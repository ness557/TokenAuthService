package simple.microservices.tokenauthservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import simple.microservices.tokenauthservice.model.Role;

import java.util.List;

@Transactional
public interface RoleCrudRepository extends CrudRepository<Role, Integer> {

    Role getById(Integer id);
    List<Role> findAll();
    Role findByName(String name);
    boolean existsById(int id);
    void deleteById(int id);
}
