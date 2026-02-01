package com.example.paramclient.service;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.exception.NotFoundException;
import com.example.paramclient.model.ClientRole;
import com.example.paramclient.repository.ParamClientLinkRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParamClientLinkServiceImpl implements ParamClientLinkService {

    private final ParamClientLinkRepository linkRepository;

    public ClientInfoDto getClientInfoByParamIdAndRole(UUID paramId, ClientRole role) {
        return linkRepository.findClientInfoByParamIdAndClientRole(paramId, role)
                .orElseThrow(() -> new NotFoundException(
                        "\u041F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u0438\u0437\u0430\u0446\u0438\u044F \u0441 %s \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u0430".formatted(paramId)
                ));
    }
}
