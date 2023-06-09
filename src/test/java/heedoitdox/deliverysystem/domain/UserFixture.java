package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.application.LoginRequest;
import heedoitdox.deliverysystem.application.UserRequest;

import heedoitdox.deliverysystem.domain.User;
import java.math.BigDecimal;

public class UserFixture {

    public static UserRequest 요청회원정보 = new UserRequest("binzzang810", "abcABC1234!!!", "윤영빈");
    public static UserRequest 요청회원정보_잘못된_비밀번호 = new UserRequest("binzzang810", "!!!!!!!!!!!!", "윤영빈");
    public static User 회원정보 = User.create(1L, "binzzang810", "abcABC1234!!!", "윤영빈");
    public static User 회원정보2 = User.create(2L, "binzzang8102", "abcABC1234!!!", "윤영빈");
    public static LoginRequest 요청로그인정보 = new LoginRequest("binzzang810", "abcABC1234!!!");
}
