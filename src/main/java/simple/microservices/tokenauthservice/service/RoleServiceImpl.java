package simple.microservices.tokenauthservice.service;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.microservices.tokenauthservice.model.Role;
import simple.microservices.tokenauthservice.repository.RoleCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleCrudRepository repository;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public RoleServiceImpl(RoleCrudRepository roleCrudRepository) {
        this.repository = roleCrudRepository;
    }

    @Override
    public Status saveRole(Role role) {
        logger.info("Trying to add role " + role);

        if (ObjectUtils.allNotNull(repository.save(role))) {
            logger.info("Role added");
            return Status.OK;
        }
        logger.info("Role NOT added");
        return Status.ERROR;
    }

    @Override
    public Status removeRole(Role role) {
        logger.info("Trying to delete role " + role);

        repository.delete(role);
        return Status.OK;
    }

    @Override
    public Status removeRole(int id) {
        logger.info("Trying to delete role by id = " + id);

        repository.deleteById(id);
        return Status.OK;
    }

    @Override
    public Role getRoleById(int id) {
        logger.info("Trying to find role by id = " + id);
        Role role = repository.getById(id);

        if (ObjectUtils.allNotNull(role))
            logger.info("Role found: " + role);
        else
            logger.info("Role NOT found");

        return role;
    }

    @Override
    public List<Role> getRoles() {
        logger.info("Trying to get roles list");

        List<Role> roles = repository.findAll();

        if (roles.isEmpty())
            logger.info("List is empty");
        else
            logger.info("Got list");

        return roles;
    }

    @Override
    public Role findRoleByName(String name) {
        logger.info("Trying to find role by name = " + name);
        Role role = repository.findByName(name);

        if (ObjectUtils.allNotNull(role))
            logger.info("Role found: " + role);
        else
            logger.info("Role NOT found");

        return role;
    }
}
