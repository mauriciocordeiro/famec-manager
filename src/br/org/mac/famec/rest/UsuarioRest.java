package br.org.mac.famec.rest;

import java.sql.Types;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import br.org.mac.famec.control.UsuarioServices;
import br.org.mac.famec.model.Usuario;
import br.org.mac.famec.util.Util;
import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.util.Result;

@Path("/usuario")
public class UsuarioRest {
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String save(String args) {
		try {
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
			
			return new JSONObject(result).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@GET
	@Path("/pesquisar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String search(@QueryParam("nome") String nome) {
		try {
			
			// busca
			ArrayList<ItemComparator> crt = new ArrayList<>();
			crt.add(new ItemComparator("nm_usuario", nome, ItemComparator.LIKE_ANY, Types.VARCHAR));
			ResultSetMap rsm = UsuarioServices.find(crt);
				
			// converte resultado em uma lista de Usuario
			int i = 0;
			Usuario[] list = new Usuario[rsm.size()];
			while(rsm.next()) {
				list[i] = new Usuario(
						rsm.getInt("cd_usuario"), 
						rsm.getString("nm_usuario"), 
						rsm.getString("nm_login"), 
						rsm.getString("nm_senha"), 
						rsm.getString("nm_email", ""), 
						rsm.getInt("st_usuario"), 
						rsm.getString("nm_funcao", ""));
				i++;
			}
			
			return new JSONArray(list).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String delete(@QueryParam("codigo") String codigo) {
		try {
			Result result = UsuarioServices.remove(Integer.parseInt(codigo));
						
			return new JSONObject(result).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@PUT
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String login(String args) {
		try {
			JSONObject json = new JSONObject(args);
			//XXX:
			System.out.println(json);
			Result result = UsuarioServices.autenticar(json.getString("nmLogin"), json.getString("nmSenha"));
			
			return new JSONObject(result).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}

}
