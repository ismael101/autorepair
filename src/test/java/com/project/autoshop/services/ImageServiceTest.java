package com.project.autoshop.services;

import com.project.autoshop.exceptions.NotFoundException;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.ImageRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @Mock
    public ImageRepository imageRepository;
    @Mock
    public JobRepository jobRepository;
    public ImageService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ImageService(imageRepository, jobRepository);
    }

    @Test
    void itShouldGetAllImages() throws DataFormatException, IOException {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/services/test.png");
        byte[] data = Files.readAllBytes(path);
        when(imageRepository.findAll()).thenReturn(List.of(Image
                .builder()
                .name("front")
                .job(new Job())
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .build()));
        List<Image> images =  underTest.getImages();
        verify(imageRepository).findAll();
        assertEquals(images.get(0).getName(), "front");
        assertArrayEquals(images.get(0).getData(), data);
    }

    @Test
    void itShouldGetImageById() throws IOException, DataFormatException {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/services/test.png");
        byte[] data = Files.readAllBytes(path);
        when(imageRepository.findById(anyInt())).thenReturn(Optional.of(Image
                .builder()
                .name("front")
                .job(new Job())
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .build()));
        Image image = underTest.getImage(1);
        verify(imageRepository).findById(1);
        assertEquals(image.getName(), "front");
        assertArrayEquals(image.getData(), data);
    }

    @Test
    void itShouldGetImageByJob() throws IOException, DataFormatException {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/services/test.png");
        byte[] data = Files.readAllBytes(path);
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        when(imageRepository.findImagesByJob(anyInt())).thenReturn(List.of(Image
                .builder()
                .name("front")
                .job(new Job())
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .build()));
        List<Image> images = underTest.getJobImages(1);
        verify(jobRepository).findById(1);
        verify(imageRepository).findImagesByJob(1);
        assertEquals(images.get(0).getName(), "front");
        assertArrayEquals(images.get(0).getData(), data);
    }

    @Test
    void itShouldCreateImage() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/services/test.png");
        MultipartFile file = new MockMultipartFile("test.png", "test.png", "image/jpeg", Files.readAllBytes(path));
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        Image image = underTest.upload(1, file);
        verify(jobRepository).findById(1);
        verify(imageRepository).save(image);
        assertEquals(image.getName(), "test.png");
        assertArrayEquals(image.getData(), FileUtils.compress(Files.readAllBytes(path), Deflater.BEST_COMPRESSION, false));
    }

    @Test
    void itShouldDeleteImage(){
        Image image = Image.builder().build();
        when(imageRepository.findById(anyInt())).thenReturn(Optional.of(image));
        underTest.deleteImage(1);
        verify(imageRepository).findById(1);
        verify(imageRepository).delete(image);
    }

    @Test
    void itShouldThrowNotFound(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            underTest.getImage(1);
        });
        assertEquals(exception.getMessage(), "image with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
           underTest.getJobImages(1);
        });

        assertEquals(exception.getMessage(), "job with id: 1 not found");

        exception = assertThrows(NotFoundException.class, () -> {
            Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/services/test.png");
            MultipartFile file = new MockMultipartFile("test.png", "test.png", "image/jpeg", Files.readAllBytes(path));
            underTest.upload(1, file);
        });

        assertEquals(exception.getMessage(), "job with id: 1 not found");

        exception =  assertThrows(NotFoundException.class, () -> {
            underTest.deleteImage(1);
        });

        assertEquals(exception.getMessage(), "image with id: 1 not found");
    }
}