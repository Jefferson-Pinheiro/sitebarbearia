package com.seunome.crm.whatsapp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;

import java.util.Optional;

// Guarda as credenciais geradas no Meta Business Manager para uma linha do WhatsApp.
// O accessToken fica criptografado em repouso (ver WhatsAppCredentialService).
public class WhatsAppCredential extends PanacheEntity {

    // Um nome livre para identificar a qual linha/numero essa credencial pertence
    public String label;

    // ID da conta do WhatsApp Business (WABA ID) no Meta Business
    public String wabaId;

    // ID do numero de telefone dentro dessa WABA (usado nas chamadas da Graph API)
    public String phoneNumberId;

    // Access token gerado no Meta Business -- SEMPRE criptografado antes de salvar
    @Column(length = 2000)
    public String accessToken;

    public boolean active;

    public static Optional<WhatsAppCredential> findActive() {
        return find("active", true).firstResultOptional();
    }
}
