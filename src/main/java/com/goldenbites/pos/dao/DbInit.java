package com.goldenbites.pos.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.goldenbites.pos.model.User;

public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    public DbInit(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userRepository.deleteAll();

        User admin = new User("admin", passwordEncoder.encode("password"), "ADMIN");
        User user = new User("user", passwordEncoder.encode("password"), "USER");

        List<User> users = Arrays.asList(admin, user);
        userRepository.saveAll(users);
    }
}
