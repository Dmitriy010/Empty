package com.example.paramclient.service;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.entity.ClientEntity;
import com.example.paramclient.entity.ClientFlEntity;
import com.example.paramclient.entity.ClientUlEntity;
import com.example.paramclient.entity.ClientUnknownEntity;
import com.example.paramclient.entity.ParamClientLinkEntity;
import com.example.paramclient.model.ClientRole;
import com.example.paramclient.model.ClientType;
import com.example.paramclient.repository.ClientFlRepository;
import com.example.paramclient.repository.ClientUlRepository;
import com.example.paramclient.repository.ClientUnknownRepository;
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
    private final ClientUlRepository clientUlRepository;
    private final ClientFlRepository clientFlRepository;
    private final ClientUnknownRepository clientUnknownRepository;

    @Transactional(readOnly = true)
    public ClientInfoDto getPrincipal(UUID paramId) {
        return getClientByRole(paramId, ClientRole.PRINCIPAL, false);
    }

    @Transactional(readOnly = true)
    public ClientInfoDto getBeneficiary(UUID paramId) {
        return getClientByRole(paramId, ClientRole.BENEFICIARY, true);
    }

    private ClientInfoDto getClientByRole(UUID paramId, ClientRole role, boolean allowUnknown) {
        ParamClientLinkEntity link = linkRepository
                .findByParametrization_IdAndClientRole(paramId, role)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Client link not found"
                ));

        ClientEntity client = link.getClient();
        if (client == null || client.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }

        ClientType clientType = client.getClientType();
        if (clientType == ClientType.UL) {
            ClientUlEntity ulEntity = clientUlRepository.findById(client.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Client UL not found"
                    ));
            return ClientInfoDto.fromUl(ulEntity, role);
        }

        if (clientType == ClientType.FL) {
            ClientFlEntity flEntity = clientFlRepository.findById(client.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Client FL not found"
                    ));
            return ClientInfoDto.fromFl(flEntity, role);
        }

        if (clientType == ClientType.UNKNOWN && allowUnknown) {
            ClientUnknownEntity unknownEntity = clientUnknownRepository.findById(client.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Client UNKNOWN not found"
                    ));
            return ClientInfoDto.fromUnknown(unknownEntity, role);
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Unsupported client type for role"
        );
    }
}
