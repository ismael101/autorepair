package com.project.autoshop.controllers;

import com.project.autoshop.exceptions.ErrorExceptionHandler;
import com.project.autoshop.models.Image;
import com.project.autoshop.models.Job;
import com.project.autoshop.repositories.ImageRepository;
import com.project.autoshop.repositories.JobRepository;
import com.project.autoshop.services.ImageService;
import com.project.autoshop.utils.FileUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {
    @Mock
    public ImageRepository imageRepository;
    @Mock
    public JobRepository jobRepository;
    public ImageService imageService;
    public ImageController underTest;
    public MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        imageService = new ImageService(imageRepository, jobRepository);
        underTest = new ImageController(imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ErrorExceptionHandler())
                .build();
    }

    @Test
    void itShouldGetImages() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/controllers/test.png");
        byte[] data = Files.readAllBytes(path);
        when(imageRepository.findAll()).thenReturn(List.of(Image
                .builder()
                .name("mock name")
                .job(new Job())
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].data", Matchers.is(Base64.getEncoder().encodeToString(data))));
    }

    @Test
    void itShouldGetImage() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/controllers/test.png");
        byte[] data = Files.readAllBytes(path);
        when(imageRepository.findById(anyInt())).thenReturn(Optional.of(Image
                .builder()
                .name("mock name")
                .job(new Job())
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/image/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(Base64.getEncoder().encodeToString(data))));
    }

    @Test
    void itShouldGetJobImages() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/controllers/test.png");
        byte[] data = Files.readAllBytes(path);
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job.builder().build()));
        when(imageRepository.findImagesByJob(anyInt())).thenReturn(List.of(Image
                .builder()
                .name("mock name")
                .job(new Job())
                .data(FileUtils.compress(data, Deflater.BEST_COMPRESSION, false))
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/image/job/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name", Matchers.is("mock name")))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].data", Matchers.is(Base64.getEncoder().encodeToString(data))));
    }

    @Test
    void itShouldCreateImage() throws Exception {
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(Job
                .builder()
                .build()));
        Path path = Paths.get(System.getProperty("user.dir") + "/src/test/java/com/project/autoshop/controllers/test.png");
        byte[] data = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile("image", "mock.png", MediaType.TEXT_PLAIN_VALUE, data);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/image/job/1").file(file))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("mock.png")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(Base64.getEncoder().encodeToString(data))));
    }

    @Test
    void itShouldDeleteImage() throws Exception {
        when(imageRepository.findById(anyInt())).thenReturn(Optional.of(Image
                .builder()
                .build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/image/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldThrowNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/image/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/image/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("image with id: 1 not found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/image/job/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/api/v1/image/job/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("job with id: 1 not found")));
    }

}