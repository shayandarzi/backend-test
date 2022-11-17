package com.example.backendassignment.controller;

import com.example.backendassignment.entity.Data;
import com.example.backendassignment.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/data")
    public ResponseEntity<Void> uploadFile(@RequestParam("data") MultipartFile dataCSVFile) {
        dataService.parseCsvFile(dataCSVFile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("").buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/data")
    public ResponseEntity<List<Data>> getAllData() {
        return ResponseEntity.ok(dataService.getAllData());
    }

    @GetMapping("/data/{code}")
    public ResponseEntity<Data> getData(@PathVariable("code") String code) {
        return ResponseEntity.ok(dataService.getData(code));
    }

    @DeleteMapping("/data/{code}")
    public ResponseEntity<Void> deleteData(@PathVariable("code") String code) {
        dataService.deleteData(code);
        return ResponseEntity.accepted().build();
    }
}
