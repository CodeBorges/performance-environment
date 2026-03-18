package com.performance.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "account_id")
    public Long accountId;

    @Column(name = "amount")
    public Long amount;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "status")
    public String status;
}
