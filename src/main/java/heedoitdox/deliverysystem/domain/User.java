package heedoitdox.deliverysystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    public static User createUser(String identifier, String password, String name) {
        User user = new User();
        user.identifier = identifier;
        user.password = password;
        user.name = name;

        return user;
    }
}
