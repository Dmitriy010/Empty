package com.example.paramclient.entity;

import com.example.paramclient.model.ClientType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "clientType", discriminatorType = DiscriminatorType.STRING)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public abstract class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    Long mdmCode;

    String tin;

    public abstract ClientType getClientType();
}
