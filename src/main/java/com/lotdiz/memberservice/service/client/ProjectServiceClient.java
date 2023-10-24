package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.response.ProjectDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "project-service", url = "${endpoint.project-service}")
public interface ProjectServiceClient {

    @GetMapping("/likes")
    ResultDataResponse<List<ProjectDetailsForShowResponseDto>> getProjectDetails(@RequestParam List<Long> projectIds);
}
