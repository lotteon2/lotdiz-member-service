package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundingClientService {
    private FundingServiceClient fundingServiceClient;

    public List<FundingDetailsForShowResponseDto> getFundigDetails(List<Long> projectIds) {
        return fundingServiceClient.getFundingDetails(projectIds).getData();
    }
}
