package com.example.paramclient.repository;

import com.example.paramclient.entity.ClientUnknownEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUnknownRepository extends JpaRepository<ClientUnknownEntity, UUID> {}
