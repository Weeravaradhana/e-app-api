package com.e_commerce.e_app.repository.jpa;
import com.e_commerce.e_app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface JpaUserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findOneByPublicId(UUID publicId);

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("""
       UPDATE UserEntity user
              SET user.addressCity = :city,
                  user.addressStreet = :street,
                  user.addressCountry = :country,
                  user.addressZipCode = :zipCode
              WHERE user.publicId = :userPublicId
       """)
    void updateAddress(UUID userPublicId, String street, String city, String country, String zipCode);
}
