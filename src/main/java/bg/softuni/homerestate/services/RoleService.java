package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    void initRoles();
    List<UserRoleEntity> getRoles();
    UserRoleEntity findByRole(UserRole user);
}
