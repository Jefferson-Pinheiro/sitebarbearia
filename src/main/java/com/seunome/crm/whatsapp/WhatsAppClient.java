package com.seunome.crm.whatsapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

// Responsavel por ENVIAR mensagens usando a API oficial (Graph API) da Meta.
// Usa a credencial ativa cadastrada via WhatsAppCredentialResource.
// (Usa java.net.http.HttpClient em vez do MicroProfile Rest Client porque o
// numero/telefone de destino faz parte da URL, que varia por chamada.)
@ApplicationScoped
public class WhatsAppClient {

    @Inject
    WhatsAppCredentialService credentialService;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "whatsapp.graph-api.base-url")
    String graphApiBaseUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void sendTextMessage(String toPhoneNumber, String text) throws Exception {
        WhatsAppCredential credential = credentialService.getActiveCredential();
        String accessToken = credentialService.getDecryptedAccessToken(credential);

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", toPhoneNumber,
                "type", "text",
                "text", Map.of("body", text)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(graphApiBaseUrl + "/" + credential.phoneNumberId + "/messages"))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Erro ao enviar mensagem via WhatsApp: " + response.body());
        }
    }
}
