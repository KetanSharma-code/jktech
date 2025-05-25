package com.jktech.assignment.documentmanager.controller;

import com.jktech.assignment.documentmanager.model.Document;
import com.jktech.assignment.documentmanager.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@SecurityRequirement(name = "BearerAuth")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        documentService.uploadDocument(file);
        return ResponseEntity.ok("Document uploaded successfully");
    }

    @GetMapping("/search")
    @Operation(summary = "Search documents by keyword")
    public ResponseEntity<List<Document>> searchDocuments(@RequestParam String query) {
        return ResponseEntity.ok(documentService.searchDocuments(query));
    }

    @Operation(
            summary = "Filter documents by author and file type",
            description = "Supports pagination and sorting via Spring Data Pageable"
    )
    @GetMapping
    public ResponseEntity<Page<Document>> selectDocuments(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String type,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "uploadDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(documentService.filterDocuments(author, type, pageable));
    }
}
