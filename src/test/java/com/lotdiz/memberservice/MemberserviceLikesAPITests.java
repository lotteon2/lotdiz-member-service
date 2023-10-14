package com.lotdiz.memberservice;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberserviceLikesAPITests {

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

  @Test
  @DisplayName("프로젝트를 단일 찜 추가할 수 있다")
  void test1() {
    Long projectId = 1L;
    ExtractableResponse<Response> response = 단일찜_추가(projectId);
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  private static ExtractableResponse<Response> 단일찜_추가(Long projectId) {
    return RestAssured.given()
        .log()
        .all()
        .pathParam("projectId", projectId)
        .when()
        .post("/api/projects/{projectId}/likes")
        .then()
        .log()
        .all()
        .extract();
  }

  @Test
  @DisplayName("찜을 단일 삭제할 수 있다")
  void test2() {
    Long projectId = 1L;
    단일찜_추가(projectId);

    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .pathParam("projectId", projectId)
            .when()
            .delete("/api/projects/{projectId}/likes")
            .then()
            .log()
            .all()
            .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("한번에 여러 개의 찜을 삭제할 수 있다")
  void test3() {
    단일찜_추가(1L);
    단일찜_추가(2L);
    단일찜_추가(3L);
    단일찜_추가(4L);

    String jsonArray = "[1, 2, 3]";

    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(jsonArray)
        .when()
        .delete("/api/likes")
        .then()
        .log()
        .all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }
}
