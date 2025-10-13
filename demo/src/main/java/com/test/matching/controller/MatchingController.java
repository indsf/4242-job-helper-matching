// src/main/java/com/test/matching/controller/MatchingController.java
package com.test.matching.controller;

import com.test.matching.dto.MatchingRequest;
import com.test.matching.entity.Matching;
import com.test.matching.entity.MatchingStatus;
import com.test.matching.repository.MatchingRepository;
import com.test.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/matchings")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MatchingRepository matchingRepository;

    /** 매칭 생성 */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMatching(@RequestBody MatchingRequest request) {
        Matching matching = matchingService.createMatching(
                request.getPostId(),
                request.getCommentId(),
                request.getMatchingStatus()
        );

        // 생성 브로드캐스트: 양 당사자에게 PENDING 추가 신호
        try {
            Map<String, Long> ids = resolveParticipants(matching);
            Long giverId = ids.get("giverId");
            Long takerId = ids.get("takerId");

            Map<String, Object> payload = Map.of(
                    "type", "MATCH_PENDING",
                    "matchingId", matching.getMatchingId(),
                    "postId", matching.getPost().getId(),
                    "postTitle", matching.getPost().getTitle(),
                    "status", matching.getMatchingStatus().name()
            );

            if (giverId != null) {
                simpMessagingTemplate.convertAndSend("/topic/matching/user/" + giverId, payload);
            }
            if (takerId != null && !takerId.equals(giverId)) {
                simpMessagingTemplate.convertAndSend("/topic/matching/user/" + takerId, payload);
            }
        } catch (Exception ignore) {}

        Map<String, Object> response = new HashMap<>();
        response.put("matchingId", matching.getMatchingId());
        response.put("status", matching.getMatchingStatus());
        return ResponseEntity.ok(response);
    }

    /** 매칭 취소 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> cancelMatching(@PathVariable("id") Long matchingId) {
        // 당사자 브로드캐스트를 위해 취소 전에 조회
        Optional<Matching> before = matchingService.getMatching(matchingId);

        matchingService.cancelMatching(matchingId);

        // 매칭ID 기준 채널
        simpMessagingTemplate.convertAndSend(
                "/topic/matching/" + matchingId,
                Map.of("type", "MATCH_CANCELLED", "matchingId", matchingId)
        );

        // 유저별 채널로도 취소 신호 (PENDING 목록에서 제거 용도)
        before.ifPresent(m -> {
            Map<String, Object> payload = Map.of(
                    "type", "MATCH_CANCELLED",
                    "matchingId", matchingId
            );
            try {
                Map<String, Long> ids = resolveParticipants(m);
                Long giverId = ids.get("giverId");
                Long takerId = ids.get("takerId");

                if (giverId != null) {
                    simpMessagingTemplate.convertAndSend("/topic/matching/user/" + giverId, payload);
                }
                if (takerId != null && !takerId.equals(giverId)) {
                    simpMessagingTemplate.convertAndSend("/topic/matching/user/" + takerId, payload);
                }
            } catch (Exception ignore) {}
        });

        return ResponseEntity.ok(Map.of("message", "매칭이 취소되었습니다."));
    }

    /** 매칭 상태 변경 (예: PENDING → DONE 등) */
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateMatchingStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") MatchingStatus status
    ) {
        matchingService.updateMatchingStatus(id, status);

        // DONE이면 PENDING → 제거, MATCHED → 추가 신호
        if (status == MatchingStatus.DONE) {
            matchingService.getMatching(id).ifPresent(m -> {
                Map<String, Object> payload = Map.of(
                        "type", "MATCH_DONE",
                        "matchingId", m.getMatchingId(),
                        "postId", m.getPost().getId(),
                        "postTitle", m.getPost().getTitle(),
                        "status", "DONE"
                );
                try {
                    Map<String, Long> ids = resolveParticipants(m);
                    Long giverId = ids.get("giverId");
                    Long takerId = ids.get("takerId");

                    if (giverId != null) {
                        simpMessagingTemplate.convertAndSend("/topic/matching/user/" + giverId, payload);
                    }
                    if (takerId != null && !takerId.equals(giverId)) {
                        simpMessagingTemplate.convertAndSend("/topic/matching/user/" + takerId, payload);
                    }
                } catch (Exception ignore) {}
            });
        }

        return ResponseEntity.ok().build();
    }

    /** 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<Matching> getMatching(@PathVariable("id") Long matchingId) {
        Optional<Matching> matching = matchingService.getMatching(matchingId);
        return matching.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** 진행중(PENDING) 목록 — 쿼리 파라미터로 memberId 받음 */
    @GetMapping("/pending")
    public ResponseEntity<Map<String, Object>> getMyPending(@RequestParam("memberId") Long memberId) {
        List<MatchingRepository.SimpleItem> list = matchingRepository.findPendingSimpleByMember(memberId);
        return ResponseEntity.ok(Map.of("items", list));
    }

    /** 매칭완료(DONE) 목록 — 쿼리 파라미터로 memberId 받음 */
    @GetMapping("/matched")
    public ResponseEntity<Map<String, Object>> getMyMatched(@RequestParam("memberId") Long memberId) {
        List<MatchingRepository.SimpleItem> list = matchingRepository.findDoneSimpleByMember(memberId);
        return ResponseEntity.ok(Map.of("items", list));
    }

    /** 채팅방 보장: 프론트에서 /chat/:roomId 로 이동하기 전에 호출 */
    @PostMapping("/ensure-room/{matchingId}")
    public ResponseEntity<Map<String, Object>> ensureRoom(@PathVariable("matchingId") Long matchingId) { // ← ✅ 이름 명시
        Optional<Matching> opt = matchingService.getMatching(matchingId);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        // 별도 방 엔티티가 없더라도 roomId는 matchingId와 동일하게 사용
        return ResponseEntity.ok(Map.of("roomId", matchingId));
    }

    // -------------------------
    // 내부 유틸: 참여자(giver/taker) 계산
    // -------------------------
    /**
     * 현재 도메인: Post의 postType(GIVER/TAKER)로 역할을 결정.
     * - postType == GIVER  → post.author = giver, comment.member = taker
     * - postType == TAKER  → post.author = taker, comment.member = giver
     */
    private Map<String, Long> resolveParticipants(Matching m) {
        // ✅ Post는 author, Comment는 member 사용
        Long postOwnerId =
                (m.getPost() != null && m.getPost().getAuthor() != null)
                        ? m.getPost().getAuthor().getId()
                        : null;

        Long commentOwnerId =
                (m.getComment() != null && m.getComment().getMember() != null)
                        ? m.getComment().getMember().getId()
                        : null;

        String type = String.valueOf(m.getPost() != null ? m.getPost().getPostType() : null);
        boolean postIsGiver = "GIVER".equalsIgnoreCase(type);

        Long giverId = postIsGiver ? postOwnerId    : commentOwnerId;
        Long takerId = postIsGiver ? commentOwnerId : postOwnerId;

        Map<String, Long> result = new HashMap<>();
        result.put("giverId", giverId);
        result.put("takerId", takerId);
        return result;
    }
}
