package com.jktech.assignment.documentmanager.service;

import com.jktech.assignment.documentmanager.model.Document;
import com.jktech.assignment.documentmanager.repository.DocumentRepository;
import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @Captor
    ArgumentCaptor<Document> documentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadDocument_shouldExtractTextAndSave() throws Exception {
        // Arrange
        String dummyContent = "This is a test document.";
        byte[] contentBytes = dummyContent.getBytes();

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(contentBytes));
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");

        when(documentRepository.save(any(Document.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        documentService.uploadDocument(mockFile);

        // Assert
        verify(documentRepository).save(documentCaptor.capture());
        Document saved = documentCaptor.getValue();

        assertEquals("test.txt", saved.getTitle()); // title from filename
        assertEquals("Unknown", saved.getAuthor()); // fallback
        assertEquals(LocalDate.now(), saved.getUploadDate());
        assertNotNull(saved.getContent());
        assertFalse(saved.getContent().isBlank());
        assertNotNull(saved.getSummary());
    }

    @Test
    void searchDocuments_shouldDelegateToRepository() {
        // Arrange
        String query = "contract";
        List<Document> mockDocs = List.of(new Document());
        when(documentRepository.searchByContentKeyword(query)).thenReturn(mockDocs);

        // Act
        List<Document> result = documentService.searchDocuments(query);

        // Assert
        assertEquals(mockDocs, result);
        verify(documentRepository).searchByContentKeyword(query);
    }

    @Test
    void filterDocuments_withAuthorAndType_shouldQueryBoth() {
        Pageable pageable = mock(Pageable.class);
        Page<Document> mockPage = mock(Page.class);

        when(documentRepository.findByAuthorIgnoreCaseAndFileTypeIgnoreCase("john", "pdf", pageable))
                .thenReturn(mockPage);

        Page<Document> result = documentService.filterDocuments("john", "pdf", pageable);

        assertEquals(mockPage, result);
        verify(documentRepository).findByAuthorIgnoreCaseAndFileTypeIgnoreCase("john", "pdf", pageable);
    }

    @Test
    void filterDocuments_withAuthorOnly_shouldQueryAuthor() {
        Pageable pageable = mock(Pageable.class);
        Page<Document> mockPage = mock(Page.class);

        when(documentRepository.findByAuthorIgnoreCase("alice", pageable)).thenReturn(mockPage);

        Page<Document> result = documentService.filterDocuments("alice", null, pageable);

        assertEquals(mockPage, result);
        verify(documentRepository).findByAuthorIgnoreCase("alice", pageable);
    }

    @Test
    void filterDocuments_withTypeOnly_shouldQueryType() {
        Pageable pageable = mock(Pageable.class);
        Page<Document> mockPage = mock(Page.class);

        when(documentRepository.findByFileTypeIgnoreCase("docx", pageable)).thenReturn(mockPage);

        Page<Document> result = documentService.filterDocuments(null, "docx", pageable);

        assertEquals(mockPage, result);
        verify(documentRepository).findByFileTypeIgnoreCase("docx", pageable);
    }

    @Test
    void filterDocuments_withNoFilters_shouldReturnAll() {
        Pageable pageable = mock(Pageable.class);
        Page<Document> mockPage = mock(Page.class);

        when(documentRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Document> result = documentService.filterDocuments(null, null, pageable);

        assertEquals(mockPage, result);
        verify(documentRepository).findAll(pageable);
    }
}