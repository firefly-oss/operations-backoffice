package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentDTO;
import com.vaadin.starter.business.ui.views.documentmanagement.CustomerDocumentation.Document;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Document and DocumentDTO objects.
 */
@Component
public class DocumentMapper {

    /**
     * Convert a Document entity to a DocumentDTO.
     *
     * @param document the Document entity to convert
     * @return the corresponding DocumentDTO
     */
    public DocumentDTO toDto(Document document) {
        if (document == null) {
            return null;
        }
        
        return new DocumentDTO(
            document.getDocumentId(),
            document.getCustomerId(),
            document.getCustomerName(),
            document.getDocumentType(),
            document.getStatus(),
            document.getFilename(),
            document.getUploadDate(),
            document.getFileSize(),
            document.getUploadedBy(),
            document.getDescription()
        );
    }

    /**
     * Convert a DocumentDTO to a Document entity.
     *
     * @param dto the DocumentDTO to convert
     * @return the corresponding Document entity
     */
    public Document toEntity(DocumentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Document document = new Document();
        document.setDocumentId(dto.getDocumentId());
        document.setCustomerId(dto.getCustomerId());
        document.setCustomerName(dto.getCustomerName());
        document.setDocumentType(dto.getDocumentType());
        document.setStatus(dto.getStatus());
        document.setFilename(dto.getFilename());
        document.setUploadDate(dto.getUploadDate());
        document.setFileSize(dto.getFileSize());
        document.setUploadedBy(dto.getUploadedBy());
        document.setDescription(dto.getDescription());
        
        return document;
    }

    /**
     * Convert a collection of Document entities to a list of DocumentDTOs.
     *
     * @param documents the collection of Document entities to convert
     * @return a list of corresponding DocumentDTOs
     */
    public List<DocumentDTO> toDtoList(Collection<Document> documents) {
        if (documents == null) {
            return List.of();
        }
        
        return documents.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of DocumentDTOs to a list of Document entities.
     *
     * @param dtos the collection of DocumentDTOs to convert
     * @return a list of corresponding Document entities
     */
    public List<Document> toEntityList(Collection<DocumentDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}