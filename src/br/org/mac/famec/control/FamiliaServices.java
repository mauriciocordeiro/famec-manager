package br.org.mac.famec.control;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.util.Result;

import br.org.mac.famec.model.Aluno;
import br.org.mac.famec.model.EnderecoResponsavel;
import br.org.mac.famec.model.Familia;
import br.org.mac.famec.model.FamiliaDAO;
import br.org.mac.famec.model.Habitacao;
import br.org.mac.famec.model.PerfilSocial;
import br.org.mac.famec.model.Responsavel;
import br.org.mac.famec.util.Conexao;
import br.org.mac.famec.util.ReportUtils;

public class FamiliaServices {

	public static Result save(Familia familia){
		return save(familia, null, null, null, null, null, null);
	}

	public static Result save(Familia familia, Responsavel responsavel, EnderecoResponsavel endereco, PerfilSocial perfilSocial, Habitacao habitacao, ArrayList<Aluno> arrayAluno, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.connect();
				connect.setAutoCommit(false);
			}

			if(familia==null)
				return new Result(-1, "Erro ao salvar. Familia é nulo");

			int retorno;
			Result result = new Result(1);
			
			// familia
			if(familia.getCdFamilia()==0){
				retorno = FamiliaDAO.insert(familia, connect);
				familia.setCdFamilia(retorno);
			}
			else {
				retorno = FamiliaDAO.update(familia, connect);
			}
			
			// responsavel
			if(responsavel!=null) {
				responsavel.setCdFamilia(familia.getCdFamilia());
				
				result = ResponsavelServices.save(responsavel, connect);	
				if(result.getCode() <= 0) {
					if(isConnectionNull)
						connect.rollback();
					return result;
				}
				
				responsavel = (Responsavel)result.getObjects().get("RESPONSAVEL");
			}
			
			// endereço
			if(endereco!=null) {
				endereco.setCdResponsavel(responsavel.getCdResponsavel());
				
				result = EnderecoResponsavelServices.save(endereco, connect);
				if(result.getCode() <= 0) {
					if(isConnectionNull)
						connect.rollback();
					return result;
				}
			}
			
			// perfil social
			if(perfilSocial!=null) {
				perfilSocial.setCdFamilia(familia.getCdFamilia());
				
				result = PerfilSocialServices.save(perfilSocial, connect);
				if(result.getCode() <= 0) {
					if(isConnectionNull)
						connect.rollback();
					return result;
				}
			}
			
			// habitacao
			if(habitacao!=null) {
				habitacao.setCdFamilia(familia.getCdFamilia());
				
				result = HabitacaoServices.save(habitacao, connect);
				if(result.getCode() <= 0) {
					if(isConnectionNull)
						connect.rollback();
					return result;
				}
			}
			
			
			// alunos
			if(arrayAluno!=null) {
				for (Aluno aluno : arrayAluno) {
					aluno.setCdFamilia(familia.getCdFamilia());
					
					result = AlunoServices.save(aluno, connect);
					if(result.getCode() <= 0) {
						if(isConnectionNull)
							connect.rollback();
						return result;
					}
				}
			}	

			if(retorno<=0)
				Conexao.rollback(connect);
			else if (isConnectionNull)
				connect.commit();
			
			result = new Result(retorno, (retorno<=0)?"Erro ao salvar...":"Salvo com sucesso...", "FAMILIA", familia);
			result.addObject("RESPONSAVEL", responsavel);
			result.addObject("ENDERECORESPONSAVEL", endereco);
			result.addObject("PERFILSOCIAL", perfilSocial);
			result.addObject("HABITACAO", habitacao);
			result.addObject("ARRAYALUNO", arrayAluno);

			return result;
		}
		catch(Exception e){
			e.printStackTrace();
			if (isConnectionNull)
				Conexao.rollback(connect);
			return new Result(-1, e.getMessage());
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}
	public static Result remove(int cdFamilia){
		return remove(cdFamilia, false, null);
	}
	public static Result remove(int cdFamilia, boolean cascade){
		return remove(cdFamilia, cascade, null);
	}
	public static Result remove(int cdFamilia, boolean cascade, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.connect();
				connect.setAutoCommit(false);
			}
			int retorno = 0;
			if(cascade){
				/** SE HOUVER, REMOVER TABELAS ASSOCIADAS **/
				connect.prepareStatement("DELETE FROM aluno WHERE cd_familia = "+cdFamilia).executeUpdate();
				connect.prepareStatement("DELETE FROM endereco_responsavel WHERE cd_responsavel = (SELECT cd_responsavel FROM responsavel WHERE cd_familia = "+cdFamilia+")").executeUpdate();	
				connect.prepareStatement("DELETE FROM responsavel WHERE cd_familia = "+cdFamilia).executeUpdate();
				connect.prepareStatement("DELETE FROM habitacao WHERE cd_familia = "+cdFamilia).executeUpdate();
				connect.prepareStatement("DELETE FROM perfil_social WHERE cd_familia = "+cdFamilia).executeUpdate();
				
				retorno = 1;
			}
			if(!cascade || retorno>0)
			retorno = FamiliaDAO.delete(cdFamilia, connect);
			if(retorno<=0){
				Conexao.rollback(connect);
				return new Result(-2, "Este registro está vinculado a outros e não pode ser excluído!");
			}
			else if (isConnectionNull)
				connect.commit();
			return new Result(1, "Registro excluído com sucesso!");
		}
		catch(Exception e){
			e.printStackTrace();
			if (isConnectionNull)
				Conexao.rollback(connect);
			return new Result(-1, "Erro ao excluir registro!");
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}
	
	public static ResultSetMap getAll() {
		return getAll(null);
	}

	public static ResultSetMap getAll(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM familia");
				return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! FamiliaServices.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaServices.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}
	
	public static ResultSetMap quickFind(String term) {
		return quickFind(term, null);
	}

	@SuppressWarnings("resource")
	public static ResultSetMap quickFind(String term, Connection connect) {
		try {
			connect = connect==null ? Conexao.connect() : connect;
			return new ResultSetMap(connect.prepareStatement(
					"SELECT A.cd_aluno, A.nm_aluno, A.cd_familia "
					+ " FROM aluno A"
					+ " JOIN familia B ON (A.cd_familia = B.cd_familia)"
					+ " JOIN responsavel C ON (A.cd_familia = C.cd_familia)"
					+ " WHERE A.nm_aluno iLike '%"+term+"%'"
					+ " OR B.nr_prontuario iLike '%"+term+"%'"
					+ " OR C.nm_responsavel iLike '%"+term+"%'")
					.executeQuery());
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaServices.quickFind: " + e);
			return null;
		}
		finally {
			Conexao.disconnect(connect);
		}
	}

	public static ResultSetMap find(ArrayList<ItemComparator> criterios) {
		return find(criterios, null);
	}

	public static ResultSetMap find(ArrayList<ItemComparator> criterios, Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		try {
			
			 ResultSetMap rsm = Search.find(
					  " SELECT A.*, C.*, D.*, E.*, F.*, G.nm_usuario "
					+ " FROM familia 				A"
//					+ " JOIN aluno 					B ON (A.cd_familia = B.cd_familia)"
					+ " JOIN responsavel 			C ON (A.cd_familia = C.cd_familia)"
					+ " JOIN habitacao 			  	D ON (A.cd_familia = D.cd_familia)"
					+ " JOIN perfil_social 		  	E ON (A.cd_familia = E.cd_familia)"
					+ " JOIN endereco_responsavel 	F ON (C.cd_responsavel = F.cd_responsavel)"
					+ " LEFT OUTER JOIN	usuario		G ON (A.cd_usuario_cadastro = G.cd_usuario)", 
					criterios, connect!=null ? connect : Conexao.connect(), connect==null);
			 
			 while(rsm.next()) {
				 ArrayList<ItemComparator> crt = new ArrayList<>();
				 crt.add(new ItemComparator("A.cd_familia", Integer.toString(rsm.getInt("cd_familia")), ItemComparator.EQUAL, Types.INTEGER));
				 rsm.setValueToField("RSM_ALUNO", AlunoServices.find(crt, connect)); 
			 }
			 rsm.beforeFirst();
			 
			 return rsm;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.out.println("Erro! FamiliaServices.find: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}
	
	public static Result generateFormulario(int cdFamilia) {		
		try {
			
			ArrayList<ItemComparator> crt = new ArrayList<>();
			crt.add(new ItemComparator("A.cd_familia", Integer.toString(cdFamilia), ItemComparator.EQUAL, Types.INTEGER));	
			ResultSetMap rsm = Search.find(
					  " SELECT A.*, B.*, C.*, D.*, E.*, F.*, G.nm_usuario "
					+ " FROM familia 				A"
					+ " JOIN aluno 					B ON (A.cd_familia = B.cd_familia)"
					+ " JOIN responsavel 			C ON (A.cd_familia = C.cd_familia)"
					+ " JOIN habitacao 			  	D ON (A.cd_familia = D.cd_familia)"
					+ " JOIN perfil_social 		  	E ON (A.cd_familia = E.cd_familia)"
					+ " JOIN endereco_responsavel 	F ON (C.cd_responsavel = F.cd_responsavel)"
					+ " LEFT OUTER JOIN	usuario		G ON (A.cd_usuario_cadastro = G.cd_usuario)", 
					crt, Conexao.connect(), true);
			
			HashMap<String, Object> parameters = new HashMap<>();
			
			Result result = ReportUtils.generate("cadastro_familia", parameters, rsm);
			
			OutputStream os = new FileOutputStream("C:/test.pdf");
			os.write((byte[])result.getObjects().get("PDF_BYTES"));
			
			os.flush();
			os.close();
			
			return result;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.out.println("Erro! FamiliaServices.generateFormulario: " + e);
			return null;
		}
	}
	

	public static Result generateComprovante(int cdFamilia, int cdAluno) {		
		try {
			
			ArrayList<ItemComparator> crt = new ArrayList<>();
			crt.add(new ItemComparator("A.cd_familia", Integer.toString(cdFamilia), ItemComparator.EQUAL, Types.INTEGER));	
			ResultSetMap rsm = find(crt);
			
			System.out.println(rsm);
			
			HashMap<String, Object> params = new HashMap<>();
			
			if(rsm.next()) {
				params.put("NR_PRONTUARIO", rsm.getString("nr_prontuario"));
				params.put("NM_ALUNO", rsm.getString("NM_ALUNO"));
				params.put("DS_DT_NASCIMENTO", rsm.getString("DS_DT_NASCIMENTO"));
//				params.put("NR_IDADE", rsm.getInt("NR_IDADE"));
				params.put("TP_SEXO", rsm.getInt("TP_SEXO"));
				params.put("NR_TELEFONE", rsm.getString("NR_TELEFONE"));
				params.put("TP_TURNO_FAMEC", rsm.getInt("TP_TURNO_FAMEC"));
				params.put("NM_ESCOLA", rsm.getString("NM_ESCOLA"));
				params.put("DS_ACOMPANHANTE", "dsf");
				params.put("DS_HR_SAIDA", "00:00");
				params.put("NM_USUARIO", rsm.getString("nm_usuario"));
			}
			rsm.beforeFirst();
			
			Result result = ReportUtils.generate("comprovante_matricula", params, rsm);
			
			return result;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.out.println("Erro! FamiliaServices.generateFormulario: " + e);
			return null;
		}
	}

}
