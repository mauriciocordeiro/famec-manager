package br.org.mac.famec.rest;

import java.sql.Types;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import br.org.mac.famec.control.FamiliaServices;
import br.org.mac.famec.control.UsuarioServices;
import br.org.mac.famec.model.Aluno;
import br.org.mac.famec.model.EnderecoResponsavel;
import br.org.mac.famec.model.Familia;
import br.org.mac.famec.model.Habitacao;
import br.org.mac.famec.model.PerfilSocial;
import br.org.mac.famec.model.Responsavel;
import br.org.mac.famec.util.Util;
import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.util.Result;

@Path("/familia")
public class FamiliaRest {
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String save(String args) {
		try {
			JSONObject json = new JSONObject(args);
						
			// familia
			Familia familia = new Familia(
					json.getInt("cdFamilia"), 
					Util.angularDateToCalendar(json.getString("dtCadastro")), 
					json.getInt("cdUsuarioCadastro"),
					json.getString("nrProntuario")
				);
			
			// responsavel
			Responsavel responsavel = new Responsavel(
					json.getInt("cdResponsavel"), 
					json.getInt("cdFamilia"), 
					json.getString("nmResponsavel"), 
					json.getInt("tpParentesco"), 
					json.getInt("tpGenero"), 
					Util.angularDateToCalendar(json.getString("dtNascimento")), 
					json.getString("nmNaturalidade"), 
					json.getInt("tpEstadoCivil"), 
					json.getString("nrTelefone1"), 
					json.getString("nrTelefone2"), 
					json.getString("nrRg"), 
					json.getString("nmOrgaoExpedidorRg"), 
					json.getString("sgUfRg"), 
					json.getString("nrCpf"), 
					json.getString("dsEscolaridade"), 
					json.getInt("lgEstudante"), 
					json.getInt("tpNivelEscolar"), 
					json.getInt("tpTurno"), 
					json.getString("nmOcupacao"), 
					Double.valueOf(json.getDouble("vlRendaMensal")), 
					json.getString("nmLocalTrabalho"), 
					json.getString("nrTelefoneTrabalho")
				);
			
			// endereco
			EnderecoResponsavel endereco = new EnderecoResponsavel(
					json.getInt("cdEnderecoResponsavel"), 
					json.getInt("cdResponsavel"), 
					json.getString("nmRua"), 
					json.getInt("nrCasa"), 
					json.getString("nmComplemento"), 
					json.getString("nmBairro"), 
					json.getString("nmCidade"), 
					json.getString("nmEstado")
				);
						
			// perfil social
			PerfilSocial perfilSocial = new PerfilSocial(
					json.getInt("cdPerfilSocial"), 
					json.getInt("cdFamilia"), 
					json.getInt("lgNis"), 
					json.getString("nrNis"), 
					json.getInt("lgBeneficio"), 
					json.getString("nmBeneficio"), 
					json.getDouble("vlBeneficio")
				);
			
			// habitação
			Habitacao habitacao = new Habitacao(
					json.getInt("cdHabitacao"), 
					json.getInt("cdFamilia"), 
					json.getInt("tpSituacao"), 
					json.getDouble("vlAluguel"), 
					json.getInt("nrComodos"), 
					json.getInt("tpAbastecimento"), 
					json.getInt("tpTratamentoAgua"), 
					json.getInt("tpIluminacao"), 
					json.getInt("tpEscoamentoSanitario"), 
					json.getInt("tpDestinoLixo")
				);
			
			// alunos
			ArrayList<Aluno> lstAlunos = new ArrayList<>();
			JSONArray arrayAlunos = json.getJSONArray("arrayAlunos");
			for (int i = 0; i < arrayAlunos.length(); i++) {
				JSONObject jsonAluno = arrayAlunos.getJSONObject(i);
				
				String strHrSaida[] = null;
				try {
					strHrSaida = jsonAluno.getString("hrSaida").split(":");
				} catch(Exception e) {}
				GregorianCalendar hrSaida = null;
				if(strHrSaida!=null && strHrSaida.length == 2)
					hrSaida = new GregorianCalendar(2000, 1, 1, Integer.parseInt(strHrSaida[0]), Integer.parseInt(strHrSaida[1]));
				
				lstAlunos.add(new Aluno(
						jsonAluno.getInt("cdAluno"), 
						jsonAluno.getInt("cdFamilia"), 
						jsonAluno.getString("nmAluno"), 
						Util.angularDateToCalendar(jsonAluno.getString("dtNascimento")), 
						jsonAluno.getInt("tpSexo"), 
						jsonAluno.getString("nmNaturalidade"), 
						jsonAluno.getString("nmEscola"), 
						jsonAluno.getInt("nrNivelEscolar"), 
						jsonAluno.getInt("tpModalidadeEscolar"), 
						jsonAluno.getInt("tpHorarioEscolar"), 
						jsonAluno.getInt("tpTurnoFamec"), 
						1, //jsonAluno.getInt("stAluno"), 
						hrSaida, //(GregorianCalendar)jsonAluno.get("hrSaida"), 
						jsonAluno.getInt("lgAcompanhanteSaida"), 
						jsonAluno.getString("nmAcompanhanteSaida"), 
						jsonAluno.getInt("lgAlmocoInstituicao"))
					);
			}
			
			Result result = FamiliaServices.save(familia, responsavel, endereco, perfilSocial, habitacao, lstAlunos, null);
			
			return new JSONObject(result).toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return new JSONObject(new Result(-1, "O servidor não conseguiu processar a requisição!")).toString();
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
			crt.add(new ItemComparator("B.nm_aluno", nome, ItemComparator.LIKE_ANY, Types.VARCHAR));
			ResultSetMap rsm = FamiliaServices.find(crt);
			
			JSONArray list = new JSONArray();
			
			while(rsm.next()) {
				
			}
			
			return list.toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@GET
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static String get(@QueryParam("cod") int cdFamilia) {
		try {			
			// busca
			ArrayList<ItemComparator> crt = new ArrayList<>();
			crt.add(new ItemComparator("A.cd_familia", Integer.toString(cdFamilia), ItemComparator.EQUAL, Types.INTEGER));
			ResultSetMap rsm = FamiliaServices.find(crt);
			
			return Util.rsmToJSON(rsm).toString();
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
			
						
			return new JSONObject("{}").toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}

}
