package simple.microservices.tokenauthservice.repository;

import simple.microservices.tokenauthservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserCrudRepository extends CrudRepository<User, Integer> {

    User getById(int id);
    List<User> findAll();
    boolean existsById(int id);
    void deleteById(int id);
    User findByUsername(String username);
}
