package com.medindev.shoppingcard.service.image;

import com.medindev.shoppingcard.dto.ImageDto;
import com.medindev.shoppingcard.exception.ResourceNotFoundException;
import com.medindev.shoppingcard.model.Image;
import com.medindev.shoppingcard.model.Product;
import com.medindev.shoppingcard.repository.ImageRepository;
import com.medindev.shoppingcard.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException ("Image not found with id: " + id));
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Image not found with id: " + id);
                });

    }

    @Override
    public List<ImageDto> saveImages (List<MultipartFile > file, Long productId) {
        Product product = productService.getProductById(productId);


        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile f : file)
            try {
                Image image = new Image();
                image.setFileName(f.getOriginalFilename());
                image.setFileType(f.getContentType());
                image.setImage(new SerialBlob(f.getBytes()));
                image.setProduct(product);

                String builDownloadUrl = "/api/v1/images/download/";
                String downloadUrl = builDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);

                saveImage.setDownloadUrl(builDownloadUrl + saveImage.getId());
                imageRepository.save(saveImage);

                ImageDto imagedto = new ImageDto();
                imagedto.setImageId(saveImage.getId());
                imagedto.setImageName(saveImage.getFileName());
                imagedto.setDownloadUrl(saveImage.getDownloadUrl());
                savedImageDto.add(imagedto);
                } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        return savedImageDto;
    }
}
