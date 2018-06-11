package simple.microservices.tokenauthservice.service;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.*;
import simple.microservices.tokenauthservice.model.User;
import simple.microservices.tokenauthservice.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserCrudRepository repository;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public UserServiceImpl(UserCrudRepository userCrudRepository) {
        this.repository = userCrudRepository;
    }

    public Status saveUser(User user) {
        logger.info("Trying to save user " + user);

        if (ObjectUtils.allNotNull(repository.save(user))) {
            logger.info("user saved");
            return Status.OK;
        }
        logger.info("user NOT saved");
        return Status.ERROR;
    }


    @Override
    public Status removeUser(User user) {
        logger.info("Trying to delete user " + user);

        repository.delete(user);
        return Status.OK;
    }

    @Override
    public Status removeUser(int id) {
        logger.info("Trying to delete user by id = " + id);

        repository.deleteById(id);
        return Status.OK;
    }

    @Override
    public User getUserById(int id) {
        logger.info("Trying to find user by id = " + id);
        User user = repository.getById(id);

        if (ObjectUtils.allNotNull(user))
            logger.info("User_info found: " + user);
        else
            logger.info("User_info NOT found");

        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> getUserList() {
        logger.info("Trying to get users list");

        List<User> users = repository.findAll();

        if (users.isEmpty())
            logger.info("List is empty");
        else
            logger.info("Got list");

        return users;
    }
}
