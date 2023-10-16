package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.ResultDataResponse;
import com.lotdiz.memberservice.dto.request.MembershipInfoForAssignRequestDto;
import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinReqeustDto;
import com.lotdiz.memberservice.service.MembershipService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipRestController {
    private final MembershipService membershipService;

    @PostMapping("/members/membership")
    public ResultDataResponse<Object> joinMembership(
        @Valid @RequestBody MembershipInfoForJoinReqeustDto membershipJoinDto) {
        membershipService.create(membershipJoinDto);
        return null;
    }

    @PostMapping("/members/membership/assign")
    public String assignCreatedAt(@RequestBody MembershipInfoForAssignRequestDto membershipAssignDto) {

        membershipService.joinComplete(membershipAssignDto);
        return "결제 최종 완료";
    }

}
