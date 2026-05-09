package com.zbank.creditcard.contoller;

import com.zbank.creditcard.dto.request.FirstLoginRequestDto;
import com.zbank.creditcard.dto.response.FirstLoginResponseDto;
import com.zbank.creditcard.service.FirstLoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credit-cards")
public class FirstLoginController {

    private final FirstLoginService firstLoginService;

    public FirstLoginController(FirstLoginService firstLoginService) {
        this.firstLoginService = firstLoginService;
    }

    @PostMapping("/first-login")
    public ResponseEntity<FirstLoginResponseDto> firstLogin(@Valid @RequestBody FirstLoginRequestDto request) {
        return ResponseEntity.ok(firstLoginService.generatePin(request));
    }
}
