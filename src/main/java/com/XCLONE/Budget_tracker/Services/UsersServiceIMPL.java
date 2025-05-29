package com.XCLONE.Budget_tracker.Services;

import com.XCLONE.Budget_tracker.Entity.Users;
import com.XCLONE.Budget_tracker.Repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceIMPL implements UserDetailsService {
    @Autowired
    private UsersRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new java.util.ArrayList<>());
    }


}
