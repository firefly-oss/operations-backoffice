/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.vaadin.starter.business.backend.mapper;

import com.vaadin.starter.business.backend.Client;
import com.vaadin.starter.business.backend.dto.ClientDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Client and ClientDTO objects.
 */
@Component
public class ClientMapper {

    /**
     * Convert a Client entity to a ClientDTO.
     *
     * @param client the Client entity to convert
     * @return the corresponding ClientDTO
     */
    public ClientDTO toDto(Client client) {
        if (client == null) {
            return null;
        }
        
        return new ClientDTO(
            client.getId(),
            client.getName(),
            client.getEmail(),
            client.getPhone(),
            client.getAddress(),
            client.getBalance(),
            client.getRegistered(),
            client.getLogoPath()
        );
    }

    /**
     * Convert a ClientDTO to a Client entity.
     *
     * @param dto the ClientDTO to convert
     * @return the corresponding Client entity
     */
    public Client toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new Client(
            dto.getId(),
            dto.getName(),
            dto.getEmail(),
            dto.getPhone(),
            dto.getAddress(),
            dto.getBalance(),
            dto.getRegistered(),
            dto.getLogoPath()
        );
    }

    /**
     * Convert a collection of Client entities to a list of ClientDTOs.
     *
     * @param clients the collection of Client entities to convert
     * @return a list of corresponding ClientDTOs
     */
    public List<ClientDTO> toDtoList(Collection<Client> clients) {
        if (clients == null) {
            return List.of();
        }
        
        return clients.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convert a collection of ClientDTOs to a list of Client entities.
     *
     * @param dtos the collection of ClientDTOs to convert
     * @return a list of corresponding Client entities
     */
    public List<Client> toEntityList(Collection<ClientDTO> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}