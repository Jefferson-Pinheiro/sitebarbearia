package com.seunome.crm.conversation;

import com.seunome.crm.lead.Lead;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.util.Optional;

// Uma conversa agrupa todas as mensagens trocadas com um lead pelo WhatsApp
public class Conversation extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "lead_id")
    public Lead lead;

    public LocalDateTime startedAt;

    @PrePersist
    void prePersist() {
        this.startedAt = LocalDateTime.now();
    }

    public static Optional<Conversation> findLatestForLead(Long leadId) {
        return find("lead.id = ?1 order by startedAt desc", leadId).firstResultOptional();
    }
}
