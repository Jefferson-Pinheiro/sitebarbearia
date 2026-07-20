package com.seunome.crm.whatsapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;
import java.util.logging.Logger;

// Endpoint publico que a Meta chama para (1) verificar o webhook na configuracao inicial
// e (2) entregar as mensagens que os leads mandam de volta pelo WhatsApp.
@Path("/api/whatsapp/webhook")
public class WhatsAppWebhookResource {

    private static final Logger LOG = Logger.getLogger(WhatsAppWebhookResource.class.getName());

    @ConfigProperty(name = "whatsapp.webhook.verify-token")
    String verifyToken;

    @Inject
    WebhookProcessingService webhookProcessingService;

    // Passo de verificacao exigido pela Meta ao cadastrar a URL do webhook no painel
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response verify(
            @QueryParam("hub.mode") String mode,
            @QueryParam("hub.verify_token") String token,
            @QueryParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            return Response.ok(challenge).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    // Recebe as notificacoes de mensagens/status enviadas pela Meta.
    // Responde 200 sempre e rapido -- a Meta reenvia (e pode ate desativar o webhook)
    // se demorar ou responder erro, entao qualquer falha de parsing fica so no log.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response receive(Map<String, Object> payload) {
        try {
            webhookProcessingService.processIncoming(payload);
        } catch (Exception e) {
            LOG.warning("Falha ao processar webhook: " + e.getMessage());
        }
        return Response.ok().build();
    }
}
