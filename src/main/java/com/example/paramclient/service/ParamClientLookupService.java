package com.example.paramclient.service;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.model.ClientRole;
import com.example.paramclient.model.ClientType;
import com.example.paramclient.repository.ParamClientLinkRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ParamClientLookupService {

    private final ParamClientLinkRepository linkRepository;

    @Transactional(readOnly = true)
    public ClientInfoDto getPrincipal(UUID paramId) {
        return getClientByRole(paramId, ClientRole.PRINCIPAL, false);
    }

    @Transactional(readOnly = true)
    public ClientInfoDto getBeneficiary(UUID paramId) {
        return getClientByRole(paramId, ClientRole.BENEFICIARY, true);
    }

    private ClientInfoDto getClientByRole(UUID paramId, ClientRole role, boolean allowUnknown) {
        ParamClientLinkRepository.ClientInfoNativeProjection projection = linkRepository
                .findClientInfoNative(paramId, role.name())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Client link not found"
                ));

        ClientType clientType = ClientType.valueOf(projection.getClientType());
        if (!allowUnknown && clientType == ClientType.UNKNOWN) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported client type for role"
            );
        }

        return ClientInfoDto.builder()
                .clientId(projection.getClientId())
                .clientRole(role)
                .clientType(clientType)
                .name(projection.getName())
                .firstName(projection.getFirstName())
                .middleName(projection.getMiddleName())
                .lastName(projection.getLastName())
                .tin(projection.getTin())
                .build();
    }
}
