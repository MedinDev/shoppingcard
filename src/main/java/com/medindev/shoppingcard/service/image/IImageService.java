package com.medindev.shoppingcard.service.image;

import com.medindev.shoppingcard.dto.ImageDto;
import com.medindev.shoppingcard.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void updateImage(MultipartFile file, Long imageId);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
}
