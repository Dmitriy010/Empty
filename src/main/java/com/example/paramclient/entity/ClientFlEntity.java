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

@Entity(name = "client_fl")
@DiscriminatorValue("FL")
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ClientFlEntity extends ClientEntity {

    String firstName;
    String middleName;
    String lastName;

    @Override
    public ClientType getClientType() {
        return ClientType.FL;
    }
}
