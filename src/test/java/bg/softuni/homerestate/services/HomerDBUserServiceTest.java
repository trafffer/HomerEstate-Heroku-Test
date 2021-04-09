package bg.softuni.homerestate.services;

import bg.softuni.homerestate.services.HomerDBUserService;
import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.UserRoleEntity;
import bg.softuni.homerestate.models.entities.enums.UserRole;
import bg.softuni.homerestate.repositories.UserRepository;
import bg.softuni.homerestate.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class HomerDBUserServiceTest {

    private HomerDBUserService serviceToTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp(){
       serviceToTest = new HomerDBUserService(mockUserRepository);
    }

    @Test
    public void testUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            serviceToTest.loadUserByUsername("user_does_not_exist");
        });
    }


    @Test
    public void testExistingUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("nasko");
        userEntity.setPassword("123");
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRole.USER);
        UserRoleEntity adminUser = new UserRoleEntity();
        adminUser.setRole(UserRole.ADMIN);
        
        userEntity.setRoles(List.of(adminUser,role));
        Mockito.when(mockUserRepository.findByUsername("nasko"))
                .thenReturn(Optional.of(userEntity));
        UserDetails userDetails = serviceToTest.loadUserByUsername("nasko");
        Assertions.assertEquals(userEntity.getUsername(),userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        List<String> authorities = userDetails.
                getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
    }

}


