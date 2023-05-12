package com.example.backend.repository;

import com.example.backend.entity.User;
import com.example.backend.entity.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User userByEmail = userRepo.findByEmail(email);
            if (userByEmail == null) {
                throw new UsernameNotFoundException(email);
            }

        return new UserDetails(userByEmail);
    }

}
