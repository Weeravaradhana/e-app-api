package com.e_commerce.e_app.service;
import com.e_commerce.e_app.dto.User;
import com.e_commerce.e_app.dto.user.UserAddressToUpdate;
import com.e_commerce.e_app.dto.user.UserPublicId;
import org.springframework.security.oauth2.jwt.Jwt;


public interface UserService {
    User save(Jwt jwt, boolean forceResync );
    void updateAddress(UserAddressToUpdate userAddress);
    User getByPublicId(UserPublicId userPublicId);
}
