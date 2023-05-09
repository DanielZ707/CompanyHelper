package com.example.backend.repository;

import com.example.backend.entity.UserEntity;
import com.example.backend.entity.UserEntityDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService  implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            UserEntity userByEmail = userRepo.findByEmail(email);
            if (userByEmail == null) {
                throw new UsernameNotFoundException(email);
            }

        return new UserEntityDetails(userByEmail);
    }

}
