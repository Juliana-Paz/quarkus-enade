package br.unitins.estagio.juliana.resource;

import br.unitins.estagio.juliana.model.Question;
import br.unitins.estagio.juliana.service.EnadeServive;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/enade")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnadeResource {

    @Inject
    EnadeServive service;

    @GET
    @Path("dailyquestion/user/{id}")
    public Response dailyQuestion(@PathParam("id") Long id) {
        Question retorno = service.dailyQuestion(id);
        return Response.status(201).entity(retorno).build();
    }   
  
}
