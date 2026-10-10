package com.logisticsapp.controller;

import com.logisticsapp.exception.BadRequestException;
import com.logisticsapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// REST controller - upload a screenshot / pdf / word file and view it
@RestController
@RequestMapping("/api/files")
public class FileController {

    // the file types we accept
    private static final List<String> ALLOWED_TYPES = List.of("png", "jpg", "jpeg", "pdf", "doc", "docx", "txt");

    // folder name taken from application.properties (app.upload-dir)
    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    // POST /api/files - uploads a file (form-data, key: file) and returns the saved file name
    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new BadRequestException("Please select a file to upload");
        }
        String original = file.getOriginalFilename() == null ? "file"
                : StringUtils.getFilename(StringUtils.cleanPath(file.getOriginalFilename()));
        String extension = original.contains(".") ? original.substring(original.lastIndexOf('.') + 1).toLowerCase() : "";
        if (!ALLOWED_TYPES.contains(extension)) {
            throw new BadRequestException("Only png, jpg, jpeg, pdf, doc, docx and txt files are allowed");
        }

        Path folder = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(folder);
        String savedName = System.currentTimeMillis() + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
        file.transferTo(folder.resolve(savedName));
        return savedName;
    }

    // GET /api/files/{fileName} - shows the file in the response (images and pdf show, word files download)
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> view(@PathVariable String fileName) throws IOException {
        Path folder = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path file = folder.resolve(fileName).normalize();
        if (!file.startsWith(folder) || !Files.isRegularFile(file)) {
            throw new ResourceNotFoundException("File not found: " + fileName);
        }
        String type = Files.probeContentType(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(type == null ? "application/octet-stream" : type))
                .body(new FileSystemResource(file));
    }
}