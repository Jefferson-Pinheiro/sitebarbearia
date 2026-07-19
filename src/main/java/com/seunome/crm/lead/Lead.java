package com.seunome.crm.lead;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.util.Optional;

// PanacheEntity ja da o campo "id" e os metodos estaticos de consulta (Lead.findById, Lead.listAll etc.)
@Entity
public class Lead extends PanacheEntity {

    public String name;

    // Numero em formato internacional, ex: 5511999999999
    public String phoneNumber;

    public String source;

    @Enumerated(EnumType.STRING)
    public LeadStatus status;

    public LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = LeadStatus.NOVO;
        }
    }

    public static Optional<Lead> findByPhoneNumber(String phoneNumber) {
        return find("phoneNumber", phoneNumber).firstResultOptional();
    }
}
