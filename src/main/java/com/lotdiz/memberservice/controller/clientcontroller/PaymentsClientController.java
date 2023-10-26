package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.request.MembershipInfoForAssignRequestDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MembershipService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentsClientController {
    private final MembershipService membershipService;

    @PostMapping("/members/membership/assign")
    public ResponseEntity<ResultDataResponse<Object>> assignCreatedAt(
        @Valid @RequestBody MembershipInfoForAssignRequestDto membershipAssignDto) {

        membershipService.joinMembershipComplete(membershipAssignDto);
        return ResponseEntity.ok()
            .body(
                new ResultDataResponse<>(
                    String.valueOf(HttpStatus.OK.value()),
                    HttpStatus.OK.name(),
                    "카카오페이 최종 결제 성공",
                    null));
    }
}
