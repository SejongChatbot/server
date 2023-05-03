package com.sejongmate.config;

import com.sejongmate.common.BaseException;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.sejongmate.common.BaseResponseStatus.INVALID_USER_NUM;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String num) throws BaseException {
        User user = userRepository.findByNum(num)
                .orElseThrow(() -> {
                    log.error(INVALID_USER_NUM.getMessage());
                    return new BaseException(INVALID_USER_NUM);
                });

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getNum(), user.getPassword(), grantedAuthorities);
    }

}
