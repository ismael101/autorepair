package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.ImageRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final JobRepository jobRepository;

    public List<Image> getImages() throws DataFormatException, IOException {
        List<Image> images = imageRepository.findAll();
        for (Image i: images){
            i.setData(FileUtils.decompress(i.getData(), false));
        }
        return images;
    }

    public Image getImage(Integer id) throws DataFormatException, IOException {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("image with id: " + id + " not found"));
        image.setData(FileUtils.decompress(image.getData(), false));
        return image;
    }

    public List<Image> getJobImages(Integer id) throws DataFormatException, IOException {
        jobRepository.findById(id).orElseThrow(() -> new NotFoundException("job with id: " + id + " not found"));
        List<Image> images = imageRepository.findImagesByJob(id);
        for (Image i: images){
            i.setData(FileUtils.decompress(i.getData(), false));
        }
        return images;
    }

    public Image upload(Integer id, MultipartFile file) throws IOException, DataFormatException {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("job with id: " + id + " not found"));
        Image image = Image
                .builder()
                .name(file.getOriginalFilename())
                .data(FileUtils.compress(file.getBytes(), Deflater.BEST_COMPRESSION, false))
                .job(job)
                .build();
        imageRepository.save(image);
        image.setData(FileUtils.decompress(image.getData(), false));
        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("image with id: " + id + " not found"));
        imageRepository.delete(image);
    }
}
