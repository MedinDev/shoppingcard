package com.medindev.shoppingcard.controller;

import com.medindev.shoppingcard.dto.ImageDto;
import com.medindev.shoppingcard.exception.ResourceNotFoundException;
import com.medindev.shoppingcard.model.Image;
import com.medindev.shoppingcard.response.ApiResponse;
import com.medindev.shoppingcard.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")  // Base URL for all image-related endpoints
public class ImageController {

    private final IImageService imageService;


    /**
     * Saves multiple images for a given product.
     *
     * @param files     The list of image files to be saved.
     * @param productId The ID of the product to which the images belong.
     * @return ResponseEntity containing the saved image DTOs or an error message.
     * @throws Exception If an error occurs during image saving.
     * @author Medindev
     * @since 1.0.0
     **/
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Images saved successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error saving images", e.getMessage()));
        }
    }

    /**
     * Downloads an image by its ID.
     *
     * @param imageId The ID of the image to be downloaded.
     * @return ResponseEntity containing the image resource or an error message.
     * @throws SQLException If an error occurs while accessing the image data.
     * @author Medindev
     * @since 1.0.0
     **/
    @PostMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage (@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);

    }

    /**
     * Updates an existing image by its ID.
     *
     * @param imageId The ID of the image to be updated.
     * @param file    The new image file to replace the existing one.
     * @return ResponseEntity containing a success message or an error message.
     * @author Medindev
     * @since 1.0.0
     **/
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image == null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Updated successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error updating image", INTERNAL_SERVER_ERROR));
    }

    /**
     * Deletes an image by its ID.
     *
     * @param imageId The ID of the image to be deleted.
     * @return ResponseEntity containing a success message or an error message.
     * @author Medindev
     * @since 1.0.0
     **/

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image == null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("deleted successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error deleting image", INTERNAL_SERVER_ERROR));
    }


}
