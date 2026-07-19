package com.seunome.crm.pipeline;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PipelineService {

    public List<PipelineStage> findAllOrdered() {
        return PipelineStage.listAll(io.quarkus.panache.common.Sort.by("sortOrder"));
    }

    @Transactional
    public PipelineStage create(PipelineStage stage) {
        stage.persist();
        return stage;
    }
}
