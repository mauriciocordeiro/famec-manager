package br.org.mac.famec.control;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

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

	public static ResultSetMap find(ArrayList<ItemComparator> criterios) {
		return find(criterios, null);
	}

	public static ResultSetMap find(ArrayList<ItemComparator> criterios, Connection connect) {
		return Search.find("SELECT * FROM familia", criterios, connect!=null ? connect : Conexao.connect(), connect==null);
	}

}
