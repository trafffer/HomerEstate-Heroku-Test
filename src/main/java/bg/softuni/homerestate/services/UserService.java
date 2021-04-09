package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.models.service.UserServiceModel;

import java.util.List;

public interface UserService {
    void initUsers();
    boolean userExist(String username);
    void registerAndLogin(UserServiceModel userServiceModel);
    UserEntity findByUsername(String toString);
    UserEntity getUser();
    List<String> findAllUsernames();
    void changeRole(String username, String role);
}
