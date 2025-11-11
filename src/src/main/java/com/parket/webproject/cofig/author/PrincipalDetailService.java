package com.parket.webproject.cofig.author;

import com.parket.webproject.domain.User;
import com.parket.webproject.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class PrincipalDetailService implements UserDetailsService {
    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        return principalDetails;
    }
}
