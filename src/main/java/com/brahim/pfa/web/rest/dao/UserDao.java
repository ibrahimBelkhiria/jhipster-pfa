package com.brahim.pfa.web.rest.dao;

import com.brahim.pfa.domain.User;
import com.brahim.pfa.repository.UserRepository;
import com.brahim.pfa.security.SecurityUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDao {




    private final UserRepository userRepository;


    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;


    }


    public Long getUserIdByCurrentLoginUser() {

        String login = SecurityUtils.getCurrentUserLogin().get();
        User user  =   this.userRepository.findOneByLogin(login).get();
        return user.getId();
    }

}
