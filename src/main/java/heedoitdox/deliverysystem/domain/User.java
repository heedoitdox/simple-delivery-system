package heedoitdox.deliverysystem.domain;

import java.util.Objects;
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

    public static User create(Long id, String identifier, String password, String name) {
        User user = new User();
        user.id = id;
        user.identifier = identifier;
        user.password = password;
        user.name = name;

        return user;
    }

    public static User create(String identifier, String password, String name) {
        User user = new User();
        user.identifier = identifier;
        user.password = password;
        user.name = name;

        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
}
