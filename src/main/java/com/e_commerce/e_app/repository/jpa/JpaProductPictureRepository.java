package com.e_commerce.e_app.repository.jpa;

import com.e_commerce.e_app.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductPictureRepository extends JpaRepository<PictureEntity,Long> {
}
