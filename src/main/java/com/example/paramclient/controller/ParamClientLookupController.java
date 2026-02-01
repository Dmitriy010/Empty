package com.example.paramclient.controller;

import com.example.paramclient.dto.ClientInfoDto;
import com.example.paramclient.service.ParamClientLookupService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParamClientLookupController {

    private final ParamClientLookupService lookupService;

    @GetMapping("/principal/{paramId}")
    public ClientInfoDto getPrincipal(@PathVariable UUID paramId) {
        return lookupService.getPrincipal(paramId);
    }

    @GetMapping("/beneficiary/{paramId}")
    public ClientInfoDto getBeneficiary(@PathVariable UUID paramId) {
        return lookupService.getBeneficiary(paramId);
    }
}
