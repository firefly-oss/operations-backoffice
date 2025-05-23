package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentTrackingEntryDTO;
import com.vaadin.starter.business.ui.views.documentmanagement.DocumentTracking.DocumentTrackingEntry;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between DocumentTrackingEntry and DocumentTrackingEntryDTO objects.
 */
@Component
public class DocumentTrackingEntryMapper {

    /**
     * Convert a DocumentTrackingEntry entity to a DocumentTrackingEntryDTO.
     *
     * @param entry the DocumentTrackingEntry entity to convert
     * @return the corresponding DocumentTrackingEntryDTO
     */
    public DocumentTrackingEntryDTO toDto(DocumentTrackingEntry entry) {
        if (entry == null) {
            return null;
        }
        
        return new DocumentTrackingEntryDTO(
            entry.getDocumentId(),
            entry.getCustomerId(),
            entry.getDocumentType(),
            entry.getStatus(),
            entry.getSubmissionDate(),
            entry.getLastUpdated(),
            entry.getCurrentStage(),
            entry.getAssignedTo(),
            entry.getEstimatedCompletion(),
            entry.getNotes()
        );
    }

    /**
     * Convert a DocumentTrackingEntryDTO to a DocumentTrackingEntry entity.
     *
     * @param dto the DocumentTrackingEntryDTO to convert
     * @return the corresponding DocumentTrackingEntry entity
     */
    public DocumentTrackingEntry toEntity(DocumentTrackingEntryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        DocumentTrackingEntry entry = new DocumentTrackingEntry();
        entry.setDocumentId(dto.getDocumentId());
        entry.setCustomerId(dto.getCustomerId());
        entry.setDocumentType(dto.getDocumentType());
        entry.setStatus(dto.getStatus());
        entry.setSubmissionDate(dto.getSubmissionDate());
        entry.setLastUpdated(dto.getLastUpdated());
        entry.setCurrentStage(dto.getCurrentStage());
        entry.setAssignedTo(dto.getAssignedTo());
        entry.setEstimatedCompletion(dto.getEstimatedCompletion());
        entry.setNotes(dto.getNotes());
        
        return entry;
    }

    /**
     * Convert a collection of DocumentTrackingEntry entities to a list of DocumentTrackingEntryDTOs.
     *
     * @param entries the collection of DocumentTrackingEntry entities to convert
     * @return a list of corresponding DocumentTrackingEntryDTOs
     */
    public List<DocumentTrackingEntryDTO> toDtoList(Collection<DocumentTrackingEntry> entries) {
        if (entries == null) {
            return List.of();
        }
        
        return entries.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of DocumentTrackingEntryDTOs to a list of DocumentTrackingEntry entities.
     *
     * @param dtos the collection of DocumentTrackingEntryDTOs to convert
     * @return a list of corresponding DocumentTrackingEntry entities
     */
    public List<DocumentTrackingEntry> toEntityList(Collection<DocumentTrackingEntryDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}