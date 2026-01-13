package com.e_commerce.e_app.dto;

import com.e_commerce.e_app.dto.user.UserAuthorityName;
import org.jilt.Builder;

@Builder
public record Authority(UserAuthorityName name) {

    public UserAuthorityName getName() {
        return name;
    }
}
