package com.example.paramclient.repository;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.entity.ParamClientLinkEntity;
import com.example.paramclient.model.ClientRole;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParamClientLinkRepository extends JpaRepository<ParamClientLinkEntity, UUID> {

    @Query("""
            select new com.example.paramclient.dto.ClientInfoDto(
                    c.id,
                    link.clientRole,
                    case
                        when ul.id is not null then com.example.paramclient.model.ClientType.UL
                        when fl.id is not null then com.example.paramclient.model.ClientType.FL
                        else com.example.paramclient.model.ClientType.UNKNOWN
                    end,
                    coalesce(ul.name, cu.name),
                    fl.firstName,
                    fl.middleName,
                    fl.lastName,
                    c.tin
            )
            from param_client_link link
            join link.client c
            left join treat(c as client_ul) ul
            left join treat(c as client_fl) fl
            left join treat(c as client_unknown) cu
            where link.parametrization.id = :paramId
              and link.clientRole = :clientRole
            """)
    Optional<ClientInfoDto> findClientInfo(
            @Param("paramId") UUID paramId,
            @Param("clientRole") ClientRole clientRole
    );
}
