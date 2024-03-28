package org.ahmedukamel.mulham.service.impl;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.exception.EntityNotFoundException;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmailIgnoreCaseAndProvider(email, Provider.LOCAL)
                .orElseThrow(() -> new EntityNotFoundException(email, User.class));
    }
}
