package com.performance.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.performance.entity.Payments;
import com.performance.services.PaymentsService;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Inject
    private PaymentsService paymentsService;

    @GetMapping
    public String hello() {
        return "Get payment";
    }

    @PostMapping
    public Payments processPayment() throws JsonProcessingException {
        return paymentsService.createPayment();
    }
}
