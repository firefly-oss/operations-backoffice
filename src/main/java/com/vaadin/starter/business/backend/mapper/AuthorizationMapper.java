package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.dto.documentmanagement.AuthorizationDTO;
import com.vaadin.starter.business.ui.views.documentmanagement.SignaturesAuthorizations.Authorization;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Authorization and AuthorizationDTO objects.
 */
@Component
public class AuthorizationMapper {

    /**
     * Convert an Authorization entity to an AuthorizationDTO.
     *
     * @param authorization the Authorization entity to convert
     * @return the corresponding AuthorizationDTO
     */
    public AuthorizationDTO toDto(Authorization authorization) {
        if (authorization == null) {
            return null;
        }
        
        return new AuthorizationDTO(
            authorization.getAuthorizationId(),
            authorization.getCustomerId(),
            authorization.getCustomerName(),
            authorization.getAuthorizationType(),
            authorization.getStatus(),
            authorization.getAuthorizedTo(),
            authorization.getCreationDate(),
            authorization.getExpiryDate(),
            authorization.getApprovedBy(),
            authorization.getNotes()
        );
    }

    /**
     * Convert an AuthorizationDTO to an Authorization entity.
     *
     * @param dto the AuthorizationDTO to convert
     * @return the corresponding Authorization entity
     */
    public Authorization toEntity(AuthorizationDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Authorization authorization = new Authorization();
        authorization.setAuthorizationId(dto.getAuthorizationId());
        authorization.setCustomerId(dto.getCustomerId());
        authorization.setCustomerName(dto.getCustomerName());
        authorization.setAuthorizationType(dto.getAuthorizationType());
        authorization.setStatus(dto.getStatus());
        authorization.setAuthorizedTo(dto.getAuthorizedTo());
        authorization.setCreationDate(dto.getCreationDate());
        authorization.setExpiryDate(dto.getExpiryDate());
        authorization.setApprovedBy(dto.getApprovedBy());
        authorization.setNotes(dto.getNotes());
        
        return authorization;
    }

    /**
     * Convert a collection of Authorization entities to a list of AuthorizationDTOs.
     *
     * @param authorizations the collection of Authorization entities to convert
     * @return a list of corresponding AuthorizationDTOs
     */
    public List<AuthorizationDTO> toDtoList(Collection<Authorization> authorizations) {
        if (authorizations == null) {
            return List.of();
        }
        
        return authorizations.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of AuthorizationDTOs to a list of Authorization entities.
     *
     * @param dtos the collection of AuthorizationDTOs to convert
     * @return a list of corresponding Authorization entities
     */
    public List<Authorization> toEntityList(Collection<AuthorizationDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}