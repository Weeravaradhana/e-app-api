package com.e_commerce.e_app.repository.impl;


import com.e_commerce.e_app.dto.User;
import com.e_commerce.e_app.dto.UserBuilder;
import com.e_commerce.e_app.dto.user.*;
import com.e_commerce.e_app.entity.AuthorityEntity;
import com.e_commerce.e_app.entity.UserEntity;
import com.e_commerce.e_app.entity.UserEntityBuilder;
import com.e_commerce.e_app.exception.DuplicateEntryException;
import com.e_commerce.e_app.repository.UserRepository;
import com.e_commerce.e_app.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public void save(User user) {
        Optional<UserEntity> selectedUser = jpaUserRepository.findByEmail(user.getEmail().value());
        if (selectedUser.isPresent()) {
            throw new DuplicateEntryException("User with id " + user.getDbId() + " already exists");
        }
       jpaUserRepository.save(toUserEntity(user));

    }

    @Override
    public Optional<User> getUser(UserPublicId userPublicId) {
        return jpaUserRepository.findOneByPublicId(userPublicId.value())
                .map(this::toUser);
    }

    @Override
    public Optional<User> getUserByEmail(UserEmail userEmail) {
        return jpaUserRepository.findByEmail(userEmail.value())
                .map(this::toUser);
    }

    @Override
    public void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress) {
     jpaUserRepository.updateAddress(userPublicId.value(),
             userAddress.userAddress().street(),
             userAddress.userAddress().city(),
             userAddress.userAddress().country(),
             userAddress.userAddress().zipCode()
     );
    }

    private UserEntity toUserEntity(User  user) {
        UserEntityBuilder userEntityBuilder = UserEntityBuilder.userEntity();

        if (user.getImageUrl() != null) {
            userEntityBuilder.imageUrl(user.getImageUrl().value());
        }

        if (user.getPublicId() != null) {
            userEntityBuilder.publicId(user.getPublicId().value());
        }

        if (user.getAddress() != null) {
            userEntityBuilder.addressCity(user.getAddress().city());
            userEntityBuilder.addressCountry(user.getAddress().country());
            userEntityBuilder.addressZipCode(user.getAddress().zipCode());
            userEntityBuilder.addressStreet(user.getAddress().street());
        }
       return UserEntityBuilder.userEntity()
                .id(user.getDbId())
                .firstName(user.getFirstname().value())
                .lastName(user.getLastname().value())
                .lastSeen(user.getLastSeenDate())
                .email(user.getEmail().value())
                .authorities(AuthorityEntity.getAuthoritiesAsEntity(user.getAuthorities()))
                .build();
    }

    private User toUser(UserEntity userEntity) {
        UserBuilder userBuilder = UserBuilder.user();

        if(userEntity.getImageUrl() != null){
            userBuilder.imageUrl(new UserImageUrl(userEntity.getImageUrl()));
        }


        if(userEntity.getAddressStreet() != null) {
            userBuilder.address(
                    UserAddressBuilder.userAddress()
                            .city(userEntity.getAddressCity())
                            .country(userEntity.getAddressCountry())
                            .zipCode(userEntity.getAddressZipCode())
                            .street(userEntity.getAddressStreet())
                            .build());
        }
        return userBuilder
                .email(new UserEmail(userEntity.getEmail()))
                .lastname(new UserLastname(userEntity.getLastName()))
                .firstname(new UserFirstname(userEntity.getFirstName()))
                .authorities(AuthorityEntity.getAuthorities(userEntity.getAuthorities()))
                .publicId(new UserPublicId(userEntity.getPublicId()))
                .lastModifiedDate(userEntity.getLastModifiedDate())
                .createdDate(userEntity.getCreatedDate())
                .dbId(userEntity.getId())
                .build();
    }

}
