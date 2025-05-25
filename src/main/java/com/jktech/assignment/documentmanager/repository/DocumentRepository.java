package com.jktech.assignment.documentmanager.repository;

import com.jktech.assignment.documentmanager.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByAuthorIgnoreCaseAndFileTypeIgnoreCase(String author, String fileType, Pageable pageable);

    Page<Document> findByAuthorIgnoreCase(String author, Pageable pageable);

    Page<Document> findByFileTypeIgnoreCase(String fileType, Pageable pageable);

    @Query(value = "SELECT * FROM documents WHERE LOWER(content) LIKE LOWER(CONCAT('%', :query, '%')) LIMIT 10", nativeQuery = true)
    List<Document> searchByContentKeyword(String query);
}
