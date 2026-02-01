package com.example.paramclient.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.exception.NotFoundException;
import com.example.paramclient.model.ClientRole;
import com.example.paramclient.model.ClientType;
import com.example.paramclient.repository.ParamClientLinkRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParamClientLinkServiceImplTest {

    @Mock
    private ParamClientLinkRepository linkRepository;

    @InjectMocks
    private ParamClientLinkServiceImpl service;

    @Test
    void getClientInfoByParamIdAndRole_returnsClientInfo() {
        UUID paramId = UUID.randomUUID();
        ClientRole role = ClientRole.PRINCIPAL;
        ClientInfoDto expected = ClientInfoDto.builder()
                .clientId(UUID.randomUUID())
                .clientRole(role)
                .clientType(ClientType.UL)
                .name("Test Company")
                .tin("1234567890")
                .build();

        when(linkRepository.findClientInfoByParamIdAndClientRole(paramId, role))
                .thenReturn(Optional.of(expected));

        ClientInfoDto result = service.getClientInfoByParamIdAndRole(paramId, role);

        assertSame(expected, result);
        verify(linkRepository).findClientInfoByParamIdAndClientRole(paramId, role);
    }

    @Test
    void getClientInfoByParamIdAndRole_notFound_throws() {
        UUID paramId = UUID.randomUUID();
        ClientRole role = ClientRole.BENEFICIARY;

        when(linkRepository.findClientInfoByParamIdAndClientRole(paramId, role))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> service.getClientInfoByParamIdAndRole(paramId, role)
        );
        assertTrue(ex.getMessage().contains(paramId.toString()));
    }
}
