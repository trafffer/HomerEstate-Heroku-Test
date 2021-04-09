package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HomerDBUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public HomerDBUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with name "+username+" was not found!"));
        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(UserEntity user) {
        List<GrantedAuthority>authorities = user.getRoles()
                .stream()
                .map(r->new SimpleGrantedAuthority("ROLE_"+r.getRole().name()))
                .collect(Collectors.toList());
        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
