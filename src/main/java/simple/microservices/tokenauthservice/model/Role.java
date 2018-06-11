package simple.microservices.tokenauthservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@XmlRootElement
@Getter @Setter @NoArgsConstructor @ToString //@EqualsAndHashCode
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @HashCodeExclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @XmlTransient
    public Set<User> getUsers() {
        return users;
    }
}
