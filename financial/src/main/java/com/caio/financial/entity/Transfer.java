package com.caio.financial.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

public class Transfer {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transfer_id")
    private UUID id;

    private BigDecimal valueTransfer;

    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destiny_account_id")
    private Account destinyAccount;

    @CreatedDate
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @LastModifiedDate
    private LocalDateTime dateUpdate;
}
