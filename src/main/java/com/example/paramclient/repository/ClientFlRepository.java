package com.example.paramclient.repository;

import com.example.paramclient.entity.ClientFlEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientFlRepository extends JpaRepository<ClientFlEntity, UUID> {}
