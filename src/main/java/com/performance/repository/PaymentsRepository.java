package com.performance.repository;

import com.performance.entity.Payments;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentsRepository implements PanacheRepository<Payments> {
}
