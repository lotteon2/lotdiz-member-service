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
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .pathParam("projectId", projectId)
            .when()
            .post("/api/projects/{projectId}/likes")
            .then()
            .log()
            .all()
            .extract();
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }
}
