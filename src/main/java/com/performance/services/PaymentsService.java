package com.performance.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.performance.entity.Payments;
import com.performance.producer.KafkaProducer;
import com.performance.repository.PaymentsRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentsService {

    @Inject
    private PaymentsRepository paymentsRepository;

    @Inject
    private KafkaProducer kafkaProducer;

    @Inject
    private ObjectMapper objectMapper;

    @Transactional
    public Payments createPayment() throws JsonProcessingException {
        Payments payments = new Payments();
        payments.amount = 100L;
        payments.createdAt = LocalDateTime.now();
        payments.status = "CREATED";

        paymentsRepository.persist(payments);
        String payment = objectMapper.writeValueAsString(payments);
        kafkaProducer.send(payment);

        return payments;
    }
}
