package com.seunome.crm.conversation;

import com.seunome.crm.lead.Lead;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ConversationService {

    // Reaproveita a conversa mais recente do lead, ou cria uma nova
    @Transactional
    public Conversation findOrCreateForLead(Lead lead) {
        return Conversation.findLatestForLead(lead.id).orElseGet(() -> {
            Conversation nova = new Conversation();
            nova.lead = lead;
            nova.persist();
            return nova;
        });
    }

    @Transactional
    public Message registerMessage(Conversation conversation, MessageDirection direction,
                                    String content, String whatsappMessageId) {
        Message message = new Message();
        message.conversation = conversation;
        message.direction = direction;
        message.content = content;
        message.whatsappMessageId = whatsappMessageId;
        message.persist();
        return message;
    }
}
