package com.lotdiz.memberservice.utils;

public interface CustomErrorMessage {
    String ALREADY_REGISTERED = "이미 가입된 회원입니다.";
    String NOT_FOUND_MEMBER = "해당 회원을 찾을 수 없습니다.";
    String INSUFFIENT_POINTS = "포인트가 부족합니다.";
    String NOT_FOUND_LIKES = "해당 찜 목록을 찾을 수 없습니다.";
    String NOT_FOUND_DELIVERY_ADDRESS = "해당 배송지를 찾을 수 없습니다";
    String NOT_FOUND_MEMBERSHIP = "가입된 멤버십을 찾을 수 없습니다.";
    String NO_MEMBERSHIP = "가입된 멤버십이 없습니다.";
}
