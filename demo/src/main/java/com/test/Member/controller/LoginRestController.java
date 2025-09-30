package com.test.Member.controller;

import com.test.Member.dto.LoginDto;
import com.test.Member.dto.NaverJoinDto;
import com.test.Member.entity.Member;
import com.test.Member.repository.MemberRepository;
import com.test.Member.service.MemberService;
import com.test.Member.service.NaverLoginService;
import com.test.utils.api.ApiResponse;
import com.test.utils.api.ApiResponse.CustomBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원 관련 REST API")
public class LoginRestController {

    private final MemberService memberService;
    private final NaverLoginService naverLoginService;
    private final MemberRepository memberRepository;

    /** 일반 로그인 */
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ApiResponse<CustomBody<Member>> login(
            @Parameter(description = "로그인 정보 DTO") @RequestBody @Valid LoginDto loginDto,
            HttpSession session) {

        Member member = memberService.login(loginDto.getEmail(), loginDto.getPassword());
        session.setAttribute("loginUser", member);

        CustomBody<Member> body = new CustomBody<>(true, member, null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    /** 네이버 로그인 URL 반환 */
    @Operation(summary = "네이버 로그인 URL 생성", description = "네이버 로그인 페이지 URL을 반환합니다.")
    @GetMapping("/naver/login")
    public ApiResponse<CustomBody<String>> naverLogin(HttpSession session) {
        String state = UUID.randomUUID().toString();
        session.setAttribute("NAVER_STATE", state);

        String redirectUri = "http://localhost:8080/api/user/naver/callback";
        String naverAuthUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
                + "&client_id=" + "o99zS6BGNfRQCh3PKSzz"
                + "&redirect_uri=" + redirectUri
                + "&state=" + state;

        CustomBody<String> body = new CustomBody<>(true, naverAuthUrl, null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    /** 네이버 로그인 콜백 처리 */
    @Operation(summary = "네이버 로그인 콜백", description = "네이버 로그인 후 호출되는 콜백 API")
    @GetMapping("/naver/callback")
    public ApiResponse<CustomBody<Member>> naverCallback(
            @Parameter(description = "네이버 인증 코드") @RequestParam String code,
            @Parameter(description = "CSRF 방지를 위한 state") @RequestParam String state,
            HttpSession session) {

        String savedState = (String) session.getAttribute("NAVER_STATE");
        if (!state.equals(savedState)) {
            throw new IllegalArgumentException("Invalid state");
        }

        Member naverMember = naverLoginService.loginByCode(code, state);
        if (naverMember.getEmail() == null || naverMember.getEmail().isEmpty()) {
            throw new IllegalArgumentException("네이버 계정에 이메일 정보가 없습니다.");
        }

        Member existing = memberRepository.findByEmail(naverMember.getEmail()).orElse(null);
        if (existing == null || existing.getDisabilityType() == null) {
            session.setAttribute("tempNaverUser", naverMember);
            CustomBody<Member> body = new CustomBody<>(true, naverMember, null); // 추가정보 필요
            return new ApiResponse<>(body, HttpStatus.OK);
        }

        session.setAttribute("loginUser", existing);
        CustomBody<Member> body = new CustomBody<>(true, existing, null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    /** 네이버 추가정보 제출 */
    @Operation(summary = "네이버 추가정보 제출", description = "네이버 로그인 후 추가 정보를 입력합니다.")
    @PostMapping("/naver/extra")
    public ApiResponse<CustomBody<Member>> naverExtraSubmit(
            @Parameter(description = "추가 정보 DTO") @RequestBody @Valid NaverJoinDto joinDto,
            HttpSession session) {

        Member tempMember = (Member) session.getAttribute("tempNaverUser");
        if (tempMember == null) {
            throw new IllegalArgumentException("세션 정보 없음. 다시 로그인 해주세요.");
        }

        tempMember.updateMember(joinDto);
        memberRepository.save(tempMember);

        session.removeAttribute("tempNaverUser");
        session.setAttribute("loginUser", tempMember);

        CustomBody<Member> body = new CustomBody<>(true, tempMember, null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    /** 로그아웃 */
    @Operation(summary = "로그아웃", description = "세션을 종료합니다.")
    @PostMapping("/logout")
    public ApiResponse<CustomBody<String>> logout(HttpSession session) {
        session.invalidate();
        CustomBody<String> body = new CustomBody<>(true, "로그아웃 완료", null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }
}
