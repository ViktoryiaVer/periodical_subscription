package com.periodicalsubscription.security;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.LoginEx;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @LogInvocationService
    @LoginEx
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new LoginException(messageSource.getMessage("msg.error.login", null,
                    LocaleContextHolder.getLocale()));
        });
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new AuthenticatedUser(user.getUsername(), user.getPassword(), grantedAuthorities, user.getId(), user.getFirstName());
    }
}
