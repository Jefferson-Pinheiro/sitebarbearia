package com.seunome.crm.lead;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class LeadService {

    public List<Lead> findAll() {
        return Lead.listAll();
    }

    public Lead findById(Long id) {
        Lead lead = Lead.findById(id);
        if (lead == null) {
            throw new IllegalArgumentException("Lead nao encontrado: " + id);
        }
        return lead;
    }

    @Transactional
    public Lead create(Lead lead) {
        lead.persist();
        return lead;
    }

    @Transactional
    public Lead updateStatus(Long id, LeadStatus status) {
        Lead lead = findById(id);
        lead.status = status;
        return lead;
    }

    // Usado pelo fluxo de recebimento de mensagens: cria o lead automaticamente
    // se ainda nao existir um cadastro para esse numero.
    @Transactional
    public Lead findOrCreateByPhoneNumber(String phoneNumber) {
        return Lead.findByPhoneNumber(phoneNumber).orElseGet(() -> {
            Lead novo = new Lead();
            novo.phoneNumber = phoneNumber;
            novo.source = "whatsapp";
            novo.persist();
            return novo;
        });
    }
}
