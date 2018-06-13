package simple.microservices.tokenauthservice.service;

import simple.microservices.tokenauthservice.model.Role;

import java.util.List;

public interface RoleService {

    Status saveRole(Role role);
    Status removeRole(Role role);
    Status removeRole(int id);
    Role getRoleById(int id);
    Role findRoleByName(String name);
    List<Role> getRoles();

}
