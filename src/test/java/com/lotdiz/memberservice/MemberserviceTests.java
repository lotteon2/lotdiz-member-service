package com.lotdiz.memberservice;

import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.service.MemberService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ContextConfiguration(classes = MemberServiceApplication.class)
//@DataJpaTest
@SpringBootTest
class MemberserviceTests {

    @Autowired
    private MemberService memberService;

    @Test
    void contextLoads() {

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUpTestData() throws SQLException {
//        jdbcTemplate.execute("TRUNCATE TABLE member");
        jdbcTemplate.execute("INSERT INTO member(member_role, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','test1@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', 'leewooyup1', '01000000000', 0L, 'https://picsum.photos/200', true)");
        jdbcTemplate.execute("INSERT INTO member(member_role, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','test2@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', 'leewooyup2', '01011111111', 0L, 'https://picsum.photos/200', true)");
        jdbcTemplate.execute("INSERT INTO member(member_role, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','test3@naver.com','$2a$12$1KExfMQ7nNPKSrJh4O2a/u60QCW/.W5PqF9d7KwFzDV8hWbuxHU8W', 'leewooyup3', '01022222222', 0L, 'https://picsum.photos/200', true)");

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8083;

    }

    @AfterEach
    public void tearDownTestData() throws SQLException {

    }

    @Test
    @DisplayName("memberId로 개인정보를 조회할 수 있다")
    void test1() {
//        Long memberId = 1L;
//        Member member = memberService.showMember(memberId);
//        MemberInfoForQueryResponseDto memberQueryDto = CustomMapper.MemberInfoForQueryResponseDtoMapper(member);
//        assertEquals("leewooyup1", memberQueryDto.getMemberName());
        Long loginedId = 1L;
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("memberId", loginedId)
            .when()
            .get("/api/members")
            .then()
            .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("현재 로그인한 회원이 자신의 개인 정보를 수정할 수 있다")
    void test2() {
        Long loginedId = 1L;

        MemberInfoForChangeRequestDto memberChangeDto = MemberInfoForChangeRequestDto.builder()
            .memberId(loginedId)
            .memberName("LEEWOOYUP1")
            .memberPhoneNumber("01012345678")
            .memberProfileImageUrl("https://i.esdrop.com/d/f/OAHra5CzfD/Ools2bpggR.jpg")
            .build();

        memberService.renew(memberChangeDto);

    }
}
