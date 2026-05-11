package com.news.cms.service;

import com.news.cms.entity.MediaFile;
import com.news.cms.repository.MediaFileRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    public List<MediaFile> getAllFiles() {
        return mediaFileRepository.findAll();
    }

    // Dashboard için toplam dosya sayısı
    public long getFileCount() {
        return mediaFileRepository.count();
    }

    public MediaFile saveMedia(MediaFile mediaFile) {
        return mediaFileRepository.save(mediaFile);
    }

    public void deleteFile(Long id) {
        mediaFileRepository.deleteById(id);
    }
}