package com.project.autorepair.controllers;

import com.project.autorepair.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(path = "api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private Logger logger = LoggerFactory.getLogger(ImageController.class);

    //endpoint for fetching all images
    @GetMapping
    public ResponseEntity getImages() throws DataFormatException, IOException {
        logger.info("get request for all images");
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImages());
    }

    //endpoint for fetching images by id
    @GetMapping(path = "{id}")
    public ResponseEntity getImage(@PathVariable("id") Integer id) throws DataFormatException, IOException {
        logger.info("get request for image with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImage(id));
    }

    //endpoint for fetching images by job
    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobImages(@PathVariable("id") Integer id) throws DataFormatException, IOException {
        logger.info("get request for images with job id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getJobImages(id));
    }

    //endpoint for adding image to job
    @PostMapping(path = "/job/{id}")
    public ResponseEntity uploadImage(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile file) throws IOException, DataFormatException {
        logger.info("post request for " + file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.OK).body(imageService.upload(id, file));
    }

    //endpoint for deleting image
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteImage(@PathVariable("id") Integer id){
        logger.info("delete request for image with id: " + id);
        imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).body("image deleted");
    }
}
