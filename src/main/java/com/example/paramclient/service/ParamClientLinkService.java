package com.example.paramclient.service;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.model.ClientRole;
import java.util.UUID;

public interface ParamClientLinkService {

    ClientInfoDto getClientInfoByParamIdAndRole(UUID paramId, ClientRole role);
}
