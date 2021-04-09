package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.repositories.UserRoleRepository;
import bg.softuni.homerestate.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserRoleRepository roleRepository;

    public RoleServiceImpl(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void initRoles() {
      if (roleRepository.count()==0){
          UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
          UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);
          roleRepository.saveAll(List.of(adminRole, userRole));
      }
    }

    @Override
    public List<UserRoleEntity> getRoles() {
        if (this.roleRepository.count() == 0) {
            throw new IllegalStateException("No roles have been inserted into the database");
        } else {
            return this.roleRepository.findAll();
        }
    }

    @Override
    public UserRoleEntity findByRole(UserRole user) {
        return roleRepository.findByRole(user)
                .orElseThrow(()->new IllegalStateException("USER role not found. Please seed the roles!"));
    }

}
