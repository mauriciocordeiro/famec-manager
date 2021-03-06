package br.org.mac.famec.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.mac.famec.model.Aluno;
import br.org.mac.famec.model.Responsavel;

@Path("/responsavel")
public class ResponsavelRest {
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String save(Responsavel responsavel) {
		return new String("\"retorno\":\"sucesso\"");
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public static String test() {
		return new String("{\"retorno\":\"sucesso\"}");
	}

}
