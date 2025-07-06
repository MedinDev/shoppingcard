package com.medindev.shoppingcard.repository;

import com.medindev.shoppingcard.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
