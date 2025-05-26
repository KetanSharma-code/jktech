package com.jktech.assignment.documentmanager.controller;

import com.jktech.assignment.documentmanager.model.Document;
import com.jktech.assignment.documentmanager.repository.DocumentRepository;
import com.jktech.assignment.documentmanager.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
@SecurityRequirement(name = "BearerAuth")
public class QnAController {

    private final DocumentService documentService;

    public QnAController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Operation(
            summary = "Q&A keyword search",
            description = "Performs keyword-based search on document content and returns top 10 matches.",
            parameters = {
                    @Parameter(name = "query", description = "Keyword or question to search", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Documents matching the query"),
                    @ApiResponse(responseCode = "400", description = "Missing or invalid query parameter")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchContent(@RequestParam("query") String query) {
        List<Document> results = documentService.searchDocuments(query);
        return ResponseEntity.ok(results);
    }
}
