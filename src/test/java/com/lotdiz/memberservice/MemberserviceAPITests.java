package com.lotdiz.memberservice;

import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @ContextConfiguration(classes = MemberServiceApplication.class)
// @DataJpaTest
@SpringBootTest
class MemberserviceAPITests {

  @Autowired private MemberService memberService;

  @Test
  void contextLoads() {}

  @Autowired private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void setUpTestData() throws SQLException {
    //        jdbcTemplate.execute("TRUNCATE TABLE member");
    jdbcTemplate.execute(
        "INSERT INTO member(member_role, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','test1@naver.com','$2a$12$K3fmfXl0i/ZXVv7nppksPOgXPw.cHdujbSA6TPwJD1XdOFhuezBuK', 'leewooyup1', '01000000000', 0L, 'https://picsum.photos/200', true)");
    jdbcTemplate.execute(
        "INSERT INTO member(member_role, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','test2@naver.com','$2a$12$K3fmfXl0i/ZXVv7nppksPOgXPw.cHdujbSA6TPwJD1XdOFhuezBuK', 'leewooyup2', '01011111111', 0L, 'https://picsum.photos/200', true)");
    jdbcTemplate.execute(
        "INSERT INTO member(member_role, member_email, member_password, member_name, member_phone_number, member_point, member_profile_image_url, member_privacy_agreement) VALUES('ROLE_USER','test3@naver.com','$2a$12$K3fmfXl0i/ZXVv7nppksPOgXPw.cHdujbSA6TPwJD1XdOFhuezBuK', 'leewooyup3', '01022222222', 0L, 'https://picsum.photos/200', true)");

    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8083;
  }

  @AfterEach
  public void tearDownTestData() throws SQLException {}

  @Test
  @DisplayName("현재 로그인한 회원이 개인정보를 조회할 수 있다")
  void test1() {
    Long loginedId = 1L;
    ExtractableResponse<Response> response = 회원정보조회(loginedId);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  private static ExtractableResponse<Response> 회원정보조회(Long loginedId) {
    return RestAssured.given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("memberId", loginedId)
        .when()
        .get("/api/members")
        .then()
        .log()
        .all()
        .extract();
  }

  @Test
  @DisplayName("현재 로그인한 회원이 자신의 개인 정보를 수정할 수 있다")
  void test2() {
    MemberInfoForChangeRequestDto memberChangeDto =
        MemberInfoForChangeRequestDto.builder()
            .originPassword(null)
            .newPassword(null)
            .memberName("이우엽1")
            .memberPhoneNumber("01012345678")
            .memberProfileImageUrl("fgdgdfg")
            .build();

    ExtractableResponse<Response> response =
        회원정보수정(memberChangeDto);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    Long loginedId = 1L;

    회원정보조회(loginedId);
  }

  @Test
  @DisplayName("현재 로그인한 회원이 자신의 개인 정보를 수정할 수 있다(비밀번호까지)")
  void test3() {
    MemberInfoForChangeRequestDto memberChangeDto =
        MemberInfoForChangeRequestDto.builder()
            .originPassword("woo1234@")
            .newPassword("woo5678@")
            .memberName("이우엽1")
            .memberPhoneNumber("01012345678")
            .memberProfileImageUrl("fgdgdfg")
            .build();
    ExtractableResponse<Response> response = 회원정보수정(memberChangeDto);
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("현재 로그인 한 회원이 자신의 개인 정보를 수정할 때, 비밀번호를 틀린 경우 새 비밀번호로 바꿀 수 없다.")
  void test4() {
    MemberInfoForChangeRequestDto memberChangeDto =
        MemberInfoForChangeRequestDto.builder()
            .originPassword("woo0000@")
            .newPassword("woo5678@")
            .memberName("이우엽1")
            .memberPhoneNumber("01012345678")
            .memberProfileImageUrl("fgdgdfg")
            .build();
    ExtractableResponse<Response> response = 회원정보수정(memberChangeDto);
    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  private static ExtractableResponse<Response> 회원정보수정(
      MemberInfoForChangeRequestDto memberChangeDto) {
    return RestAssured.given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(memberChangeDto)
        .when()
        .patch("/api/members")
        .then()
        .log()
        .all()
        .extract();
  }



}
