package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "funding-service", url = "${endpoint.funding-service}")
public interface FundingServiceClient {

    @GetMapping("/likes/detail")
    ResultDataResponse<List<FundingDetailsForShowResponseDto>> getFundingDetails(@RequestParam List<Long> projectIds);
}
