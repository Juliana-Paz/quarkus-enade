package br.unitins.estagio.juliana.resource;

import br.unitins.estagio.juliana.model.Curso;
import br.unitins.estagio.juliana.model.HistoryAnswer;
import br.unitins.estagio.juliana.service.CursoService;
import br.unitins.estagio.juliana.service.HistoryAnswerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryAnswerResource {

    @Inject
    HistoryAnswerService service;

    // @POST
    // public Response insert(HistoryAnswer historyAnswer) {
    //     HistoryAnswer retorno = service.insert(historyAnswer);
    //     return Response.status(201).entity(retorno).build();
    // }

}
