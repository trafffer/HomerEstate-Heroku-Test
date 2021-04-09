package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.models.service.UserServiceModel;
import bg.softuni.homerestate.repositories.UserRepository;
import bg.softuni.homerestate.repositories.UserRoleRepository;
import bg.softuni.homerestate.services.HomerDBUserService;
import bg.softuni.homerestate.services.RoleService;
import bg.softuni.homerestate.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;
    private final HomerDBUserService homerDBUserService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper mapper, PasswordEncoder encoder, HomerDBUserService homerDBUserService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.encoder = encoder;
        this.homerDBUserService = homerDBUserService;
    }


    @Override
    public void initUsers() {
         if (userRepository.count()==0){
             UserEntity adminEntity = new UserEntity().setUsername("babadji").setPassword(encoder.encode("topSecret"));
             adminEntity.setRoles(roleService.getRoles());
             adminEntity.setFullName("Sai Baba");
             adminEntity.setEmail("k.terziew@abv.bg");
             userRepository.save(adminEntity);
         }
    }

    @Override
    public boolean userExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void registerAndLogin(UserServiceModel userServiceModel) {
        UserEntity user = mapper.map(userServiceModel,UserEntity.class);
        user.setPassword(encoder.encode(userServiceModel.getPassword()));
        UserRoleEntity role = roleService.findByRole(UserRole.USER);
        user.addRole(role);
        userRepository.save(user);
        UserDetails principal = homerDBUserService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public UserEntity findByUsername(String toString) {
       return userRepository.findByUsername(toString)
                .orElseThrow(()->new IllegalArgumentException("There is no such user in DB"));
    }

    @Override
    public UserEntity getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return findByUsername(username);
        }
        throw new IllegalStateException("User is not logged in");
    }

    @Override
    public List<String> findAllUsernames() {
        List<String> usernames= userRepository.findAllUsernames();
        usernames.remove(getUser().getUsername());
        return usernames;
    }

    @Override
    public void changeRole(String username, String role) {
        UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(()->new IllegalArgumentException("User with that username does not exist!"));
        if (role.equals("ADMIN")){
            user.setRoles(null);
            user.setRoles(roleService.getRoles());
        }else{
            user.setRoles(null);
            user.setRoles(List.of(roleService.findByRole(UserRole.USER)));
        }
        userRepository.save(user);
    }
}
