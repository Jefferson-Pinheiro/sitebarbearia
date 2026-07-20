package com.seunome.crm.whatsapp;

import com.seunome.crm.conversation.Conversation;
import com.seunome.crm.conversation.ConversationService;
import com.seunome.crm.conversation.MessageDirection;
import com.seunome.crm.lead.Lead;
import com.seunome.crm.lead.LeadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// Interpreta o payload que a Meta manda no webhook e grava a mensagem recebida
// no lead/conversa correspondente. Formato do payload (resumido):
// entry[].changes[].value.messages[] = { from, id, text: { body } }
@ApplicationScoped
public class WebhookProcessingService {

    private static final Logger LOG = Logger.getLogger(WebhookProcessingService.class.getName());

    @Inject
    LeadService leadService;

    @Inject
    ConversationService conversationService;

    @Transactional
    public void processIncoming(Map<String, Object> payload) {
        try {
            List<Map<String, Object>> entries = (List<Map<String, Object>>) payload.get("entry");
            if (entries == null) return;

            for (Map<String, Object> entry : entries) {
                List<Map<String, Object>> changes = (List<Map<String, Object>>) entry.get("changes");
                if (changes == null) continue;

                for (Map<String, Object> change : changes) {
                    Map<String, Object> value = (Map<String, Object>) change.get("value");
                    if (value == null) continue;

                    List<Map<String, Object>> messages = (List<Map<String, Object>>) value.get("messages");
                    if (messages == null) continue; // pode ser so uma notificacao de status, sem mensagem nova

                    for (Map<String, Object> msg : messages) {
                        handleIncomingMessage(msg);
                    }
                }
            }
        } catch (ClassCastException e) {
            LOG.warning("Payload do webhook em formato inesperado: " + e.getMessage());
        }
    }

    private void handleIncomingMessage(Map<String, Object> msg) {
        String from = (String) msg.get("from");
        String whatsappMessageId = (String) msg.get("id");
        Map<String, Object> text = (Map<String, Object>) msg.get("text");
        String body = text != null ? (String) text.get("body") : null;

        if (from == null || body == null) {
            LOG.info("Mensagem recebida sem texto simples (imagem/audio/etc) -- ignorada por enquanto: " + msg);
            return;
        }

        Lead lead = leadService.findOrCreateByPhoneNumber(from);
        Conversation conversation = conversationService.findOrCreateForLead(lead);
        conversationService.registerMessage(conversation, MessageDirection.INBOUND, body, whatsappMessageId);
    }
}
