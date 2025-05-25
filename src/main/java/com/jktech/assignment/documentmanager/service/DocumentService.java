package com.jktech.assignment.documentmanager.service;

import com.jktech.assignment.documentmanager.model.Document;
import com.jktech.assignment.documentmanager.repository.DocumentRepository;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private final DocumentRepository documentRepository;
    private final Tika tika = new Tika();

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void uploadDocument(MultipartFile file) {
        try {
            // Extract text content and metadata using Apache Tika
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1); // Unlimited size
            Metadata metadata = new Metadata();

            try (InputStream stream = file.getInputStream()) {
                parser.parse(stream, handler, metadata);
            }

            String textContent = handler.toString();
            String fileType = tika.detect(file.getInputStream());
            String author = metadata.get("Author") != null ? metadata.get("Author") : "Unknown";
            String title = metadata.get("title") != null ? metadata.get("title") : file.getOriginalFilename();

            // Create and save Document entity
            Document doc = new Document();
            doc.setTitle(title);
            doc.setAuthor(author);
            doc.setFileType(fileType);
            doc.setUploadDate(LocalDate.now());
            doc.setContent(textContent);
            doc.setSummary(generateSummary(textContent)); // optional summary/snippet

            documentRepository.save(doc);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload document", e);
        }
    }

    private String generateSummary(String content) {
        if (content == null || content.isBlank()) return "";
        return content.length() > 300 ? content.substring(0, 300) + "..." : content;
    }

    public List<Document> searchDocuments(String query) {
        return documentRepository.searchByContentKeyword(query);
    }

    public Page<Document> filterDocuments(String author, String type, Pageable pageable) {
        if (author != null && type != null) {
            return documentRepository.findByAuthorIgnoreCaseAndFileTypeIgnoreCase(author, type, pageable);
        } else if (author != null) {
            return documentRepository.findByAuthorIgnoreCase(author, pageable);
        } else if (type != null) {
            return documentRepository.findByFileTypeIgnoreCase(type, pageable);
        } else {
            return documentRepository.findAll(pageable);
        }
    }
}