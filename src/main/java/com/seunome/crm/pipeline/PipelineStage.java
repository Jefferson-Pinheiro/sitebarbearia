package com.seunome.crm.pipeline;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

// Representa uma etapa configuravel do funil de vendas.
// Ex: "Novo lead", "Qualificacao", "Proposta enviada", "Fechado"
public class PipelineStage extends PanacheEntity {

    public String name;

    // Define a ordem de exibicao das etapas no funil
    public Integer order;
}
