package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "funding-service", url = "${endpoint.funding-service}")
public interface FundingServiceClient {

    @PostMapping("/likes/detail")
    ResultDataResponse<List<FundingDetailsForShowResponseDto>> getFundingDetails(@RequestParam List<Long> projectIds);
}
