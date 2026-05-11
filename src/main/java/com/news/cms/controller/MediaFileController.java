package com.news.cms.controller;

import com.news.cms.entity.MediaFile;
import com.news.cms.service.MediaFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaFileController {

    private final MediaFileService mediaFileService;

    public MediaFileController(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    @GetMapping
    public List<MediaFile> getAll() {
        return mediaFileService.getAllFiles();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(mediaFileService.getFileCount());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mediaFileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}