package com.seunome.crm.pipeline;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/pipeline-stages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PipelineResource {

    @Inject
    PipelineService pipelineService;

    @GET
    public List<PipelineStage> findAll() {
        return pipelineService.findAllOrdered();
    }

    @POST
    public PipelineStage create(PipelineStage stage) {
        return pipelineService.create(stage);
    }
}
