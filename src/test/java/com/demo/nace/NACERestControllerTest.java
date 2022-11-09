package com.demo.nace;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NACERestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void status422WhenUploadingSemicolonSeparatedCSV() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "NACE_REV2_20221029_092113.csv",
                "text/csv",
                new ClassPathResource("NACE_REV2_20221029_092113.csv").getInputStream());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/nace-detail/upload")
                        .file(mockMultipartFile))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void status200WhenUploadingCommaSeparatedCSV() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "NACE_REV2_20221029_092722.csv",
                "text/csv",
                new ClassPathResource("NACE_REV2_20221029_092722.csv").getInputStream());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/nace-detail/upload")
                        .file(mockMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    void status422WhenUploadingEmptyCSV() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "SampleCSVFile_empty.csv",
                "text/csv",
                new ClassPathResource("SampleCSVFile_empty.csv").getInputStream());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/nace-detail/upload")
                        .file(mockMultipartFile))
                .andExpect(status().is4xxClientError());
    }

    public static void main(String[] args) {
    }
}
