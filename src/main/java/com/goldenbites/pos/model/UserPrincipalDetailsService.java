package com.goldenbites.pos.model;

import com.goldenbites.pos.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if(!user.getUserRole().equals("ADMIN")) {
        	return null;
        }else {
        	UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
    }
}
