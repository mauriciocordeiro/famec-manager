package br.org.mac.famec.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import br.org.mac.famec.control.UsuarioServices;
import br.org.mac.famec.model.Usuario;
import br.org.mac.famec.util.Util;
import sol.util.Result;

@Path("/usuario")
public class UsuarioRest {
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String save(String args) {
		try {
			Thread.sleep(2000);
			JSONObject json = new JSONObject(args);
			Result result = UsuarioServices.save(
					new Usuario(
							json.getInt("cdUsuario"), 
							json.getString("nmUsuario"), 
							json.getString("nmLogin"), 
							json.getString("nmSenha"), 
							json.getString("nmEmail"), 
							json.getInt("stUsuario"), 
							json.getString("nmFuncao")));
			
			return new JSONObject((Usuario)result.getObjects().get("USUARIO")).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
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
