package simple.microservices.tokenauthservice.model;


import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@XmlRootElement
@Getter @Setter @NoArgsConstructor @ToString
@EqualsAndHashCode
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public boolean addRole(Role role){
        return roles.add(role);
    }

    public boolean removeRole(Role role){
        return roles.remove(role);
    }

    public String getRolesString(){

        StringBuilder buf = new StringBuilder();
        for (Role role: roles) {
            buf.append(role.getName());
            buf.append(", ");
        }
        String res = buf.toString();
        if(res.isEmpty())
            return "";
        return res.substring(0, res.length()-2);
    }

    public Map<String, String> toMap(){

        Map<String, String> map = new HashMap<>();

        map.put("username", this.username);
        map.put("password", this.password);
        map.put("roles", getRolesString());

        return map;
    }
}
