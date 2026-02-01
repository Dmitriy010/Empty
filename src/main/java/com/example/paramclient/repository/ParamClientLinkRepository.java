package com.example.paramclient.repository;

import com.example.paramclient.entity.ParamClientLinkEntity;
import com.example.paramclient.model.ClientRole;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamClientLinkRepository extends JpaRepository<ParamClientLinkEntity, UUID> {

    Optional<ParamClientLinkEntity> findByParametrization_IdAndClientRole(
            UUID paramId,
            ClientRole clientRole
    );
}
