package com.project.autoshop.controllers;

import com.project.autoshop.services.ImageService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity getImages() throws DataFormatException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImages());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getImage(@PathVariable("id") Integer id) throws DataFormatException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImage(id));
    }

    @GetMapping(path = "/job/{id}")
    public ResponseEntity getJobImages(@PathVariable("id") Integer id) throws DataFormatException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getJobImages(id));
    }

    @PostMapping(path = "/job/{id}")
    public ResponseEntity uploadImage(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.upload(id, file));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteImage(@PathVariable("id") Integer id){
        imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).body("image deleted");
    }
}
