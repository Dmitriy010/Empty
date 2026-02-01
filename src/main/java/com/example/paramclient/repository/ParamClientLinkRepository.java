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
    Optional<ClientInfoDto> findClientInfoHql(
            @Param("paramId") UUID paramId,
            @Param("clientRole") ClientRole clientRole
    );

    @Query(value = """
            select
                c.id as client_id,
                link.client_role as client_role,
                case
                    when ul.id is not null then 'UL'
                    when fl.id is not null then 'FL'
                    else 'UNKNOWN'
                end as client_type,
                coalesce(ul.name, cu.name) as name,
                fl.first_name as first_name,
                fl.middle_name as middle_name,
                fl.last_name as last_name,
                c.tin as tin
            from param_client_link link
            join client c on c.id = link.client_id
            left join client_ul ul on ul.id = c.id
            left join client_fl fl on fl.id = c.id
            left join client_unknown cu on cu.id = c.id
            where link.param_id = :paramId
              and link.client_role = :clientRole
            """, nativeQuery = true)
    Optional<ClientInfoNativeProjection> findClientInfoNative(
            @Param("paramId") UUID paramId,
            @Param("clientRole") String clientRole
    );

    interface ClientInfoNativeProjection {
        UUID getClientId();

        String getClientRole();

        String getClientType();

        String getName();

        String getFirstName();

        String getMiddleName();

        String getLastName();

        String getTin();
    }
}
