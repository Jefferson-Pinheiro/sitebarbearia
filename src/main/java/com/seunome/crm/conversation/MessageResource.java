package com.seunome.crm.conversation;

import com.seunome.crm.lead.Lead;
import com.seunome.crm.lead.LeadService;
import com.seunome.crm.whatsapp.WhatsAppClient;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.List;

// Historico de mensagens de um lead especifico + envio de nova mensagem pela tela do CRM.
@Path("/api/leads/{leadId}/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    LeadService leadService;

    @Inject
    ConversationService conversationService;

    @Inject
    WhatsAppClient whatsAppClient;

    public record MessageView(Long id, MessageDirection direction, String content, LocalDateTime sentAt) {}

    public record SendMessageRequest(String text) {}

    private MessageView toView(Message message) {
        return new MessageView(message.id, message.direction, message.content, message.sentAt);
    }

    @GET
    public List<MessageView> list(@PathParam("leadId") Long leadId) {
        Lead lead = leadService.findById(leadId);
        Conversation conversation = conversationService.findOrCreateForLead(lead);
        return Message.findByConversationOrdered(conversation.id).stream()
                .map(this::toView)
                .toList();
    }

    @POST
    @Transactional
    public MessageView send(@PathParam("leadId") Long leadId, SendMessageRequest request) throws Exception {
        Lead lead = leadService.findById(leadId);
        Conversation conversation = conversationService.findOrCreateForLead(lead);

        // Envia de verdade pela API oficial do WhatsApp -- se falhar (ex: fora da janela
        // de 24h sem usar um template aprovado), a excecao sobe e a mensagem NAO e salva.
        whatsAppClient.sendTextMessage(lead.phoneNumber, request.text());

        Message message = conversationService.registerMessage(
                conversation, MessageDirection.OUTBOUND, request.text(), null);
        return toView(message);
    }
}
