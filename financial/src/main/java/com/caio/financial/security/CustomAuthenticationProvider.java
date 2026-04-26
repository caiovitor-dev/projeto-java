package com.caio.financial.security;

import com.caio.financial.entity.User;
import com.caio.financial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login= authentication.getName();
        String passwordEntered= authentication.getCredentials().toString();

        User user= userService.getUserByLogin(login);

        String encryptedPassword= user.getPassword();

        boolean matches = passwordEncoder.matches(passwordEntered, encryptedPassword);

        if(matches){
            return new CustomAuthentication(user);
        }

        throw new UsernameNotFoundException("Usuario ou senha incorretos");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
