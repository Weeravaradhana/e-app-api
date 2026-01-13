package com.e_commerce.e_app.entity;

import com.e_commerce.e_app.dto.Authority;
import com.e_commerce.e_app.dto.AuthorityBuilder;
import com.e_commerce.e_app.dto.user.UserAuthorityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jilt.Builder;

import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "authority")
@Builder
public class AuthorityEntity {
    @Id
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    public AuthorityEntity() {
    }

    public AuthorityEntity(String name) {
        this.name = name;
    }

    public @NotNull @Size(max = 50) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(max = 50) String name) {
        this.name = name;
    }

    public static Set<AuthorityEntity> getAuthoritiesAsEntity(Set<Authority> authorities) {
      return authorities.stream()
               .map(auth->AuthorityEntityBuilder.authorityEntity()
                       .name(auth.name().name()).build()).collect(Collectors.toSet());

    }

    public static Set<Authority> getAuthorities(Set<AuthorityEntity> authorities) {
        return authorities.stream()
                .map(auth-> AuthorityBuilder.authority().name(new UserAuthorityName(auth.name))
                        .build())
                .collect(Collectors.toSet());

    }

}
