package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.response.ProjectDetailsForShowResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectClientService {
    private final ProjectServiceClient projectServiceClient;

    public List<ProjectDetailsForShowResponseDto> getProjectDetails(List<Long> projectIds) {
        return projectServiceClient.getProjectDetails(projectIds).getData();
    }
}
