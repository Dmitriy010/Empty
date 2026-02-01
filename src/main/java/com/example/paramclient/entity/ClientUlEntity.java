package com.example.paramclient.entity;

import com.example.paramclient.model.ClientType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity(name = "client_ul")
@DiscriminatorValue("UL")
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ClientUlEntity extends ClientEntity {

    String name;

    @Override
    public ClientType getClientType() {
        return ClientType.UL;
    }
}
