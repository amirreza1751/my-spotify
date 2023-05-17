package com.neperia.mySpotify.controller;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import com.neperia.mySpotify.exception.StorageFileNotFoundException;
import com.neperia.mySpotify.service.FileSystemStorageService;
import com.neperia.mySpotify.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileSystemStorageService storageService;

    @Autowired
    public FileUploadController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String listUploadedFiles(Model model) throws IOException {

        return storageService.loadAll().map(Path::getFileName).toList().toString();

    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        Resource savedFile = storageService.loadAsResource(file.getOriginalFilename());
        System.out.println(savedFile.getFilename());
        try {
            System.out.println(savedFile.getURI().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(savedFile.getDescription());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}