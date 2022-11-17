package com.example.backendassignment.service;

import com.example.backendassignment.entity.Data;
import com.example.backendassignment.repository.DataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DataServiceUTest {

    @Autowired
    private DataService dataService;

    @Autowired
    private DataRepository dataRepository;

    @AfterEach
    public void afterEach() {
        dataRepository.deleteAll();
    }

    @Test
    void uploadServiceTest() throws IOException {

        File testFile = new File("data.csv");

        InputStream stream = new FileInputStream(testFile);
        MultipartFile multipartFileToSend = new MockMultipartFile("file", testFile.getName(), MediaType.TEXT_HTML_VALUE, stream);
        dataService.parseCsvFile(multipartFileToSend);
        assertEquals(18, dataRepository.findAll().size());
    }

    @Test
    void getDataTest() throws IOException {
        File testFile = new File("data.csv");

        InputStream stream = new FileInputStream(testFile);
        MultipartFile multipartFileToSend = new MockMultipartFile("file", testFile.getName(), MediaType.TEXT_HTML_VALUE, stream);
        dataService.parseCsvFile(multipartFileToSend);
        Data data = dataRepository.findByCode("Type 1").get();
        assertEquals("ZIB", data.getSource());
        assertEquals("ZIB001", data.getCodeListCode());
    }

    @Test
    void deleteDataTest() throws IOException {
        File testFile = new File("data.csv");

        InputStream stream = new FileInputStream(testFile);
        MultipartFile multipartFileToSend = new MockMultipartFile("file", testFile.getName(), MediaType.TEXT_HTML_VALUE, stream);
        dataService.parseCsvFile(multipartFileToSend);
        dataService.deleteData("Type 1");
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> dataService.getData("Type 1"));
        assertEquals("Data not found.", entityNotFoundException.getMessage());
    }

}
