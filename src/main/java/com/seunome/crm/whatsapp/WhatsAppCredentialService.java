package com.seunome.crm.whatsapp;

import com.seunome.crm.crypto.TokenEncryptor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// Camada responsavel por cadastrar e consultar as credenciais do Meta Business.
// E aqui que o ID e o token que voce gera no app do WhatsApp Business sao registrados.
@ApplicationScoped
public class WhatsAppCredentialService {

    @Inject
    TokenEncryptor tokenEncryptor;

    @Transactional
    public WhatsAppCredential register(String label, String wabaId, String phoneNumberId, String rawAccessToken) {
        WhatsAppCredential credential = new WhatsAppCredential();
        credential.label = label;
        credential.wabaId = wabaId;
        credential.phoneNumberId = phoneNumberId;
        credential.accessToken = tokenEncryptor.encrypt(rawAccessToken);
        credential.active = true;
        credential.persist();
        return credential;
    }

    // Retorna o token ja descriptografado, pronto para ser usado numa chamada a Graph API.
    // Usar somente dentro do backend -- nunca expor esse valor em resposta de API.
    public String getDecryptedAccessToken(WhatsAppCredential credential) {
        return tokenEncryptor.decrypt(credential.accessToken);
    }

    public WhatsAppCredential getActiveCredential() {
        return WhatsAppCredential.findActive()
                .orElseThrow(() -> new IllegalStateException(
                        "Nenhuma credencial do WhatsApp Business esta cadastrada/ativa."));
    }
}
