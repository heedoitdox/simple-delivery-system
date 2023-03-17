package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.application.UserRequest;

import java.math.BigDecimal;

public class UserFixture {
    public static UserRequest 회원정보요청 = new UserRequest("binzzang810", "123123123", "윤영빈");
    public static User 회원정보 = User.createUser("binzzang810", "12312312323", "윤영빈");
}
