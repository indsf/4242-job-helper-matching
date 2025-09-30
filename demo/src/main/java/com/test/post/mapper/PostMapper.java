package com.test.post.mapper;

import com.test.Member.entity.Member;
import com.test.post.Entity.*;
import com.test.post.dto.AssistanceResDto;
import com.test.post.dto.PostListItemDto;
import com.test.post.dto.PostReqDto;
import com.test.post.dto.ScheduleListResDto;

public final class PostMapper {

    private PostMapper(){

    }

    public static Post toEntity(PostReqDto request, Member author) {
        return Post.builder()
                .member(author)
                .title(request.title())
                .assistanceType(AssistanceType.assFromValue(request.assistanceType()))
                .schedule(Schedule.builder()
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .scheduleType(ScheduleType.schFromValue(request.scheduleType()))
                        .scheduleDetails(request.scheduleDetails())
                        .build())
                .collage(request.collage())
                .content(request.content())
                .postType(PostType.postFromValue(request.postType()))
                .disabilityType(author.getDisabilityType())
                .gender(author.getGender())
                .age(author.getAge())
                .assistanceTime(AssistanceTime.builder()
                        .AssistanceStartTime(request.assistanceStartTime())
                        .AssistanceEndTime(request.assistanceEndTime())
                        .build())
                .build();
    }


    private static AssistanceResDto toAssistanceResDto(Post post) {
        return new AssistanceResDto(
                post.getAssistanceType(),
                post.getAssistanceTime().getAssistanceStartTime(),
                post.getAssistanceTime().getAssistanceEndTime()
        );
    }

    private static ScheduleListResDto toScheduleListResDto(Post post) {
        return ScheduleListResDto.builder()
                .startDate(post.getSchedule().getStartDate())
                .endDate(post.getSchedule().getEndDate())
                .scheduleType(post.getSchedule().getScheduleType())
                .build();
    }
}
