package com.e_commerce.e_app.service.impl;

import com.e_commerce.e_app.dto.Authority;
import com.e_commerce.e_app.dto.AuthorityBuilder;
import com.e_commerce.e_app.dto.User;
import com.e_commerce.e_app.dto.UserBuilder;
import com.e_commerce.e_app.dto.user.*;
import com.e_commerce.e_app.exception.UserNotFoundException;
import com.e_commerce.e_app.repository.UserRepository;
import com.e_commerce.e_app.security.AuthenticationUser;
import com.e_commerce.e_app.service.UserService;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public UserServiceImpl(UserRepository userRepository, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public User save(Jwt jwt, boolean forceResync) {
        List<String> rolesFromToken = AuthenticationUser.extractRolesFromToken(jwt);
        Map<String, Object> userInfo = keycloakService.getUserInfo(jwt);
        User user = fromTokenAttribute(rolesFromToken,userInfo);
        Optional<User> existingUser = userRepository.getUserByEmail(user.getEmail());

        existingUser
                .filter(u-> !forceResync)
                .ifPresentOrElse(
                        e-> updateUser(user,e),
                         ()->{
                            user.setPublicId(new UserPublicId(UUID.randomUUID()));
                            userRepository.save(user);

                         }
                        );
        return user;
    }

    @Override
    public void updateAddress(UserAddressToUpdate userAddress) {
        userRepository.updateAddress(userAddress.userPublicId(),userAddress);
    }

    @Override
    public User getByPublicId(UserPublicId userPublicId) {
        return userRepository.getUser(userPublicId).orElseThrow(()->new UserNotFoundException("User not found"));
    }

    private User fromTokenAttribute(List<String> rolesFromToken, Map<String, Object> userInfo) {
        UserBuilder userBuilder = new UserBuilder();

        if(userInfo.containsKey("email")) {
            userBuilder.email(new UserEmail(userInfo.get("email").toString()));
        }

        if(userInfo.containsKey("given_name")) {
            userBuilder.firstname(new UserFirstname(userInfo.get("given_name").toString()));
        }

        if(userInfo.containsKey("family_name")) {
            userBuilder.lastname(new UserLastname(userInfo.get("family_name").toString()));
        }

        if(userInfo.containsKey("picture")) {
            userBuilder.imageUrl(new UserImageUrl(userInfo.get("picture").toString()));
        }

        if(userInfo.containsKey("last_signed_in")) {
            userBuilder.lastSeenDate(Instant.parse(userInfo.get("last_signed_in").toString()));
        }

        Set<Authority> authorities = rolesFromToken
                .stream()
                .map(auth -> AuthorityBuilder.authority().name(new UserAuthorityName(auth)).build())
                .collect(Collectors.toSet());

        userBuilder.authorities(authorities);

        return userBuilder.build();
    }

    private void updateUser(User user,User existingUser) {
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastSeenDate(user.getLastSeenDate());
        existingUser.setImageUrl(user.getImageUrl());
        userRepository.save(existingUser);
    }


}
