package data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    private String login;

    private String pass;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }
}
