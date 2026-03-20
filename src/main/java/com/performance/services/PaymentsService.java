package com.performance.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.performance.entity.Payments;
import com.performance.producer.KafkaProducer;
import com.performance.repository.PaymentsRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class PaymentsService {

    @Inject
    private PaymentsRepository paymentsRepository;

    @Inject
    private KafkaProducer kafkaProducer;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private MeterRegistry meterRegistry;

    @Transactional
    public Payments createPayment() throws JsonProcessingException {
        Timer.Sample totalSample = Timer.start(meterRegistry);

        Payments payments = new Payments();
        payments.amount = 100L;
        payments.createdAt = LocalDateTime.now();
        payments.status = "CREATED";

        Timer.Sample dbSample = Timer.start(meterRegistry);
        paymentsRepository.persist(payments);
        dbSample.stop(meterRegistry.timer("payment.db.time","type", "database"));

        String payment = objectMapper.writeValueAsString(payments);

        Timer.Sample kafkaSample = Timer.start(meterRegistry);
        kafkaProducer.send(payment);
        kafkaSample.stop(meterRegistry.timer("payment.kafka.time","type", "kafka"));

        totalSample.stop(meterRegistry.timer("payment.total.time", "type", "total"));
        return payments;
    }
}
