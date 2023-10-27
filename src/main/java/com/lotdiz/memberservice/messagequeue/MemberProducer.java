package com.lotdiz.memberservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotdiz.memberservice.dto.request.CreateMemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProducer {

  private final ObjectMapper mapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public void sendCreateMember(CreateMemberRequestDto createMemberRequestDto) {
    try {
      String jsonString = mapper.writeValueAsString(createMemberRequestDto);
      kafkaTemplate.send("create-member", jsonString);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
