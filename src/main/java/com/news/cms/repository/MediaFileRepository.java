package com.news.cms.repository;
import com.news.cms.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
    List<MediaFile> findByMimeType(String mimeType);
    List<MediaFile> findByByteSizeGreaterThan(Long size);
}