package com.example.paramclient.dto;

import com.example.paramclient.entity.ClientFlEntity;
import com.example.paramclient.entity.ClientUlEntity;
import com.example.paramclient.entity.ClientUnknownEntity;
import com.example.paramclient.model.ClientRole;
import com.example.paramclient.model.ClientType;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoDto {

    UUID clientId;
    ClientRole clientRole;
    ClientType clientType;

    String name;
    String firstName;
    String middleName;
    String lastName;
    String tin;

    public static ClientInfoDto fromUl(ClientUlEntity entity, ClientRole role) {
        return ClientInfoDto.builder()
                .clientId(entity.getId())
                .clientRole(role)
                .clientType(ClientType.UL)
                .name(entity.getName())
                .tin(entity.getTin())
                .build();
    }

    public static ClientInfoDto fromFl(ClientFlEntity entity, ClientRole role) {
        return ClientInfoDto.builder()
                .clientId(entity.getId())
                .clientRole(role)
                .clientType(ClientType.FL)
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .tin(entity.getTin())
                .build();
    }

    public static ClientInfoDto fromUnknown(ClientUnknownEntity entity, ClientRole role) {
        return ClientInfoDto.builder()
                .clientId(entity.getId())
                .clientRole(role)
                .clientType(ClientType.UNKNOWN)
                .name(entity.getName())
                .tin(entity.getTin())
                .build();
    }
}
