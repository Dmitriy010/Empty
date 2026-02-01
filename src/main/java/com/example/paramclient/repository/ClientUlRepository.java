package com.example.paramclient.repository;

import com.example.paramclient.entity.ClientUlEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUlRepository extends JpaRepository<ClientUlEntity, UUID> {}
