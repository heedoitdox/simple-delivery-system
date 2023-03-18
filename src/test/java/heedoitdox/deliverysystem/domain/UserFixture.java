package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.application.LoginRequest;
import heedoitdox.deliverysystem.application.UserRequest;

import heedoitdox.deliverysystem.domain.User;
import java.math.BigDecimal;

public class UserFixture {

    public static UserRequest 요청회원정보 = new UserRequest("binzzang810", "abcABC1234!!!", "윤영빈");
    public static User 회원정보 = User.createUser("binzzang810", "abcABC1234!!!", "윤영빈");
    public static LoginRequest 요청로그인정보 = new LoginRequest("binzzang810", "abcABC1234!!!");
}
