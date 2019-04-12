package br.org.mac.famec.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import br.org.mac.famec.control.FamiliaServices;
import sol.util.Result;

@Path("/util")
public class UtilRest {
	
	@GET
	@Path("/report/cad-familia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String gerarCadastroFamilia(@QueryParam("cod") Integer cdFamilia) {
		try {
			
			Result result = FamiliaServices.generateFormulario(cdFamilia);
			
			return new JSONObject(result).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}

}
