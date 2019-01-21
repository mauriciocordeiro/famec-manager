package br.org.mac.famec.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.org.mac.famec.control.UsuarioServices;
import br.org.mac.famec.model.Usuario;
import br.org.mac.famec.util.Util;

@Path("/usuario")
public class UsuarioRest {
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String save(Usuario usuario) {
		//return new String("\"retorno\":\"sucesso\"");
		return UsuarioServices.save(usuario).toString();
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public static String test() {
		return new String("{\"retorno\":\"sucesso\"}");
	}
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public static String getAll() {
		return Util.rsmToJSON(UsuarioServices.getAll());
	}
	

}
