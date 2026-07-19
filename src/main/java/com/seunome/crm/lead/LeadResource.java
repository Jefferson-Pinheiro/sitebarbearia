package com.seunome.crm.lead;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/leads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LeadResource {

    @Inject
    LeadService leadService;

    @GET
    public List<Lead> findAll() {
        return leadService.findAll();
    }

    @GET
    @Path("/{id}")
    public Lead findById(@PathParam("id") Long id) {
        return leadService.findById(id);
    }

    @POST
    public Lead create(Lead lead) {
        return leadService.create(lead);
    }

    @PATCH
    @Path("/{id}/status")
    public Lead updateStatus(@PathParam("id") Long id, @QueryParam("status") LeadStatus status) {
        return leadService.updateStatus(id, status);
    }
}
