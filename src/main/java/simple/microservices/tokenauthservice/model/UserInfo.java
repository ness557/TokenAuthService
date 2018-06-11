package simple.microservices.tokenauthservice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_info")
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private int phone;
}
