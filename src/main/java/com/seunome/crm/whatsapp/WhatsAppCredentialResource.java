package com.seunome.crm.whatsapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

// Endpoint onde voce cola o WABA ID / phone_number_id / access_token
// gerados no painel do Meta Business, para o CRM passar a usar a API oficial.
@Path("/api/whatsapp/credentials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WhatsAppCredentialResource {

    @Inject
    WhatsAppCredentialService credentialService;

    public record RegisterCredentialRequest(String label, String wabaId, String phoneNumberId, String accessToken) {}

    @POST
    public WhatsAppCredential register(RegisterCredentialRequest request) {
        return credentialService.register(
                request.label(), request.wabaId(), request.phoneNumberId(), request.accessToken());
        // Observacao: a resposta ainda expoe o campo accessToken (criptografado).
        // No proximo passo vale criar um DTO de saida que omite esse campo por completo.
    }
}
