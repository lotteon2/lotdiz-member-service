package com.lotdiz.memberservice.dto.response;

import com.lotdiz.memberservice.dto.MemberNameDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberNameResponseDto {
      private List<MemberNameDto> memberNameDtos;

}
