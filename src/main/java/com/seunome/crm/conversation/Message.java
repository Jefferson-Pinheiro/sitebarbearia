package com.seunome.crm.conversation;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class Message extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    public Conversation conversation;

    @Enumerated(EnumType.STRING)
    public MessageDirection direction;

    @Column(length = 4000)
    public String content;

    // id da mensagem retornado pela Graph API, util para rastrear status de entrega
    public String whatsappMessageId;

    public LocalDateTime sentAt;

    @PrePersist
    void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}
