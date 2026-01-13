package com.e_commerce.e_app.repository;

import com.e_commerce.e_app.dto.User;
import com.e_commerce.e_app.dto.user.UserAddressToUpdate;
import com.e_commerce.e_app.dto.user.UserEmail;
import com.e_commerce.e_app.dto.user.UserPublicId;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> getUser(UserPublicId userPublicId);
    Optional<User> getUserByEmail(UserEmail userEmail);
    void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress);

}
