package com.seunome.crm.pipeline;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

// Representa uma etapa configuravel do funil de vendas.
// Ex: "Novo lead", "Qualificacao", "Proposta enviada", "Fechado"
@Entity
public class PipelineStage extends PanacheEntity {

    public String name;

    // Define a ordem de exibicao das etapas no funil
    // (nao pode se chamar "order" -- e palavra reservada no PostgreSQL)
    public Integer sortOrder;
}
