package com.e_commerce.e_app.dto.response;

import com.e_commerce.e_app.dto.Authority;
import com.e_commerce.e_app.dto.User;
import org.jilt.Builder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record ResponseUser(UUID publicId,
                           String firstName,
                           String lastName,
                           String email,
                           String imageUrl,
                           Set<String> authorities)
{
    public static ResponseUser toResponseUser(User user) {
       ResponseUserBuilder responseUser = ResponseUserBuilder.responseUser();
       if (user.getImageUrl() != null) {
           responseUser.imageUrl(user.getImageUrl().value());
       }

       return responseUser
               .publicId(user.getPublicId().value())
               .firstName(user.getFirstname().value())
               .lastName(user.getLastname().value())
               .email(user.getEmail().value())
               .authorities(fromSet(user.getAuthorities()))
               .build();
    }

    private static Set<String> fromSet(Set<Authority> authorities) {
        return authorities.stream()
                .map(auth->auth.getName().name())
                .collect(Collectors.toSet());
    }
}
