package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.dto.documentmanagement.TransactionDocumentDTO;
import com.vaadin.starter.business.ui.views.documentmanagement.TransactionDocuments.Document;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Document and TransactionDocumentDTO objects.
 */
@Component
public class TransactionDocumentMapper {

    /**
     * Convert a Document entity to a TransactionDocumentDTO.
     *
     * @param document the Document entity to convert
     * @return the corresponding TransactionDocumentDTO
     */
    public TransactionDocumentDTO toDto(Document document) {
        if (document == null) {
            return null;
        }
        
        return new TransactionDocumentDTO(
            document.getDocumentId(),
            document.getTransactionId(),
            document.getTransactionType(),
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
     * Convert a TransactionDocumentDTO to a Document entity.
     *
     * @param dto the TransactionDocumentDTO to convert
     * @return the corresponding Document entity
     */
    public Document toEntity(TransactionDocumentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Document document = new Document();
        document.setDocumentId(dto.getDocumentId());
        document.setTransactionId(dto.getTransactionId());
        document.setTransactionType(dto.getTransactionType());
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
     * Convert a collection of Document entities to a list of TransactionDocumentDTOs.
     *
     * @param documents the collection of Document entities to convert
     * @return a list of corresponding TransactionDocumentDTOs
     */
    public List<TransactionDocumentDTO> toDtoList(Collection<Document> documents) {
        if (documents == null) {
            return List.of();
        }
        
        return documents.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of TransactionDocumentDTOs to a list of Document entities.
     *
     * @param dtos the collection of TransactionDocumentDTOs to convert
     * @return a list of corresponding Document entities
     */
    public List<Document> toEntityList(Collection<TransactionDocumentDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}