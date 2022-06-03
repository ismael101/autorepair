package com.project.autorepair.services;

import com.project.autorepair.exceptions.NotFoundException;
import com.project.autorepair.models.Image;
import com.project.autorepair.models.Job;
import com.project.autorepair.repositories.ImageRepository;
import com.project.autorepair.repositories.JobRepository;
import com.project.autorepair.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;

//service containing business logic for images
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    //method that fetches all images
    public List<Image> getImages() throws DataFormatException, IOException {
        List<Image> images = imageRepository.findAll();
        //decompresses each image from database
        for (Image i: images){
            i.setData(FileUtils.decompress(i.getData(), false));
        }
        logger.info("all images fetched");
        return images;
    }

    //method that fetches image by id
    public Image getImage(Integer id) throws DataFormatException, IOException {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by image with id: " + id);
                    throw new NotFoundException("image with id: " + id + " not found");
                });
        //decompress image
        image.setData(FileUtils.decompress(image.getData(), false));
        logger.info(image.toString() + " fetched");
        return image;
    }

    //method that fetches image job id
    public List<Image> getJobImages(Integer id) throws DataFormatException, IOException {
        List<Image> images = imageRepository.findImagesByJob(id);
        for (Image i: images){
            i.setData(FileUtils.decompress(i.getData(), false));
        }
        logger.info(images.toString() + " fetched");
        return images;
    }

    //method for creating an image
    public Image upload(Integer id, MultipartFile file) throws IOException, DataFormatException {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + id);
                    throw new NotFoundException("job with id: " + id + " not found");
                });
        Image image = Image
                .builder()
                .name(file.getOriginalFilename())
                .data(FileUtils.compress(file.getBytes(), Deflater.BEST_COMPRESSION, false))
                .job(job)
                .build();
        imageRepository.save(image);
        image.setData(FileUtils.decompress(image.getData(),  false));
        logger.info(image.toString() + " uploaded");
        return image;
    }

    //method for deleting on image
    public void deleteImage(Integer id){
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("not found exception caused by job with id: " + id);
                    throw new NotFoundException("image with id: " + id + " not found");
                });
        logger.info(image.toString() + " deleted");
        imageRepository.delete(image);
    }
}
