package com.caio.financial.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recurring_transaction_id")
    private UUID id;

    private String description;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @CreatedDate
    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
