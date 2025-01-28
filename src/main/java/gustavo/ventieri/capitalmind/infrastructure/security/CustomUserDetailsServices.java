package gustavo.ventieri.capitalmind.infrastructure.security;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import gustavo.ventieri.capitalmind.entity.User;
import gustavo.ventieri.capitalmind.repository.UserRepository;
import org.springframework.stereotype.Component;





@Component
public class CustomUserDetailsServices implements UserDetailsService  {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findById(UUID.fromString(username)).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
    
}
