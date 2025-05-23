package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.dto.documentmanagement.SignatureDTO;
import com.vaadin.starter.business.ui.views.documentmanagement.SignaturesAuthorizations.Signature;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Signature and SignatureDTO objects.
 */
@Component
public class SignatureMapper {

    /**
     * Convert a Signature entity to a SignatureDTO.
     *
     * @param signature the Signature entity to convert
     * @return the corresponding SignatureDTO
     */
    public SignatureDTO toDto(Signature signature) {
        if (signature == null) {
            return null;
        }
        
        return new SignatureDTO(
            signature.getSignatureId(),
            signature.getCustomerId(),
            signature.getCustomerName(),
            signature.getSignatureType(),
            signature.getStatus(),
            signature.getCreationDate(),
            signature.getExpiryDate(),
            signature.getVerifiedBy(),
            signature.getNotes()
        );
    }

    /**
     * Convert a SignatureDTO to a Signature entity.
     *
     * @param dto the SignatureDTO to convert
     * @return the corresponding Signature entity
     */
    public Signature toEntity(SignatureDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Signature signature = new Signature();
        signature.setSignatureId(dto.getSignatureId());
        signature.setCustomerId(dto.getCustomerId());
        signature.setCustomerName(dto.getCustomerName());
        signature.setSignatureType(dto.getSignatureType());
        signature.setStatus(dto.getStatus());
        signature.setCreationDate(dto.getCreationDate());
        signature.setExpiryDate(dto.getExpiryDate());
        signature.setVerifiedBy(dto.getVerifiedBy());
        signature.setNotes(dto.getNotes());
        
        return signature;
    }

    /**
     * Convert a collection of Signature entities to a list of SignatureDTOs.
     *
     * @param signatures the collection of Signature entities to convert
     * @return a list of corresponding SignatureDTOs
     */
    public List<SignatureDTO> toDtoList(Collection<Signature> signatures) {
        if (signatures == null) {
            return List.of();
        }
        
        return signatures.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of SignatureDTOs to a list of Signature entities.
     *
     * @param dtos the collection of SignatureDTOs to convert
     * @return a list of corresponding Signature entities
     */
    public List<Signature> toEntityList(Collection<SignatureDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}