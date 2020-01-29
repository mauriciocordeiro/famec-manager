package br.org.mac.famec.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import br.org.mac.famec.control.AlunoServices;
import br.org.mac.famec.control.FamiliaServices;
import br.org.mac.famec.model.Aluno;
import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.util.Result;

@Path("/aluno")
public class AlunoRest {
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String save(Aluno aluno) {
		return new String("\"retorno\":\"sucesso\"");
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public static String test() {
		return new String("{\"retorno\":\"sucesso\"}");
	}
	
	@GET
	@Path("/quick/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String search(@QueryParam("nome") String nome) {
		try {			
			// busca
			ResultSetMap rsm = AlunoServices.quickFind(nome);
			
			Aluno[] list = new Aluno[rsm.size()];
			while(rsm.next()) {
				list[rsm.getPointer()] = new Aluno();
				list[rsm.getPointer()].setCdAluno(rsm.getInt("cd_aluno"));
				list[rsm.getPointer()].setNmAluno(rsm.getString("nm_aluno"));
				list[rsm.getPointer()].setCdFamilia(rsm.getInt("cd_familia"));
			}
			
			return new JSONArray(list).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@POST
	@Path("/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String find() {
		try {
			ResultSetMap rsm = AlunoServices.find(new ArrayList<ItemComparator>());
			JSONObject[] list = new JSONObject[rsm.size()];
			while(rsm.next()) {
				JSONObject item = new JSONObject(rsm.getRegister());
				list[rsm.getPointer()] = item;
			}
			
			return new JSONArray(list).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@GET
	@Path("/report/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	public static Response reportComprovante() {
		try {			
			Result result = AlunoServices.printLista();
			
			return Response.ok((byte[])result.getObjects().get("PDF_BYTES")).build();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}

}
