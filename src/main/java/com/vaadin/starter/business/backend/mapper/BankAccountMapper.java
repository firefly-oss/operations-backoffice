package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.BankAccount;
import com.vaadin.starter.business.backend.dto.BankAccountDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between BankAccount and BankAccountDTO objects.
 */
@Component
public class BankAccountMapper {

    /**
     * Convert a BankAccount entity to a BankAccountDTO.
     *
     * @param bankAccount the BankAccount entity to convert
     * @return the corresponding BankAccountDTO
     */
    public BankAccountDTO toDto(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }
        
        return new BankAccountDTO(
            bankAccount.getId(),
            bankAccount.getBank(),
            bankAccount.getAccount(),
            bankAccount.getOwner(),
            bankAccount.getAvailability(),
            bankAccount.getUpdated(),
            bankAccount.getLogoPath()
        );
    }

    /**
     * Convert a BankAccountDTO to a BankAccount entity.
     *
     * @param dto the BankAccountDTO to convert
     * @return the corresponding BankAccount entity
     */
    public BankAccount toEntity(BankAccountDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new BankAccount(
            dto.getId(),
            dto.getBank(),
            dto.getAccount(),
            dto.getOwner(),
            dto.getAvailability(),
            dto.getUpdated(),
            dto.getLogoPath()
        );
    }

    /**
     * Convert a collection of BankAccount entities to a list of BankAccountDTOs.
     *
     * @param bankAccounts the collection of BankAccount entities to convert
     * @return a list of corresponding BankAccountDTOs
     */
    public List<BankAccountDTO> toDtoList(Collection<BankAccount> bankAccounts) {
        if (bankAccounts == null) {
            return List.of();
        }
        
        return bankAccounts.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of BankAccountDTOs to a list of BankAccount entities.
     *
     * @param dtos the collection of BankAccountDTOs to convert
     * @return a list of corresponding BankAccount entities
     */
    public List<BankAccount> toEntityList(Collection<BankAccountDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}