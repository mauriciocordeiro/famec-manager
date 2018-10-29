package br.org.mac.famec.control;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.util.Result;

import com.tivic.manager.conexao.Conexao;

import br.org.mac.famec.model.PerfilSocial;
import br.org.mac.famec.model.PerfilSocialDAO;

public class PerfilSocialServices {

	public static Result save(PerfilSocial perfilSocial){
		return save(perfilSocial, null);
	}

	public static Result save(PerfilSocial perfilSocial, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.conectar();
				connect.setAutoCommit(false);
			}

			if(perfilSocial==null)
				return new Result(-1, "Erro ao salvar. PerfilSocial é nulo");

			int retorno;
			if(perfilSocial.getCdPerfilSocial()==0){
				retorno = PerfilSocialDAO.insert(perfilSocial, connect);
				perfilSocial.setCdPerfilSocial(retorno);
			}
			else {
				retorno = PerfilSocialDAO.update(perfilSocial, connect);
			}

			if(retorno<=0)
				Conexao.rollback(connect);
			else if (isConnectionNull)
				connect.commit();

			return new Result(retorno, (retorno<=0)?"Erro ao salvar...":"Salvo com sucesso...", "PERFILSOCIAL", perfilSocial);
		}
		catch(Exception e){
			e.printStackTrace();
			if (isConnectionNull)
				Conexao.rollback(connect);
			return new Result(-1, e.getMessage());
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}
	public static Result remove(int cdPerfilSocial){
		return remove(cdPerfilSocial, false, null);
	}
	public static Result remove(int cdPerfilSocial, boolean cascade){
		return remove(cdPerfilSocial, cascade, null);
	}
	public static Result remove(int cdPerfilSocial, boolean cascade, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.conectar();
				connect.setAutoCommit(false);
			}
			int retorno = 0;
			if(cascade){
				/** SE HOUVER, REMOVER TABELAS ASSOCIADAS **/
				retorno = 1;
			}
			if(!cascade || retorno>0)
			retorno = PerfilSocialDAO.delete(cdPerfilSocial, connect);
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
				Conexao.desconectar(connect);
		}
	}
public static ResultSetMap getAll() {
		return getAll(null);
	}

	public static ResultSetMap getAll(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.conectar();
		PreparedStatement pstmt;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM perfil_social");
				return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialServices.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialServices.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static ResultSetMap find(ArrayList<ItemComparator> criterios) {
		return find(criterios, null);
	}

	public static ResultSetMap find(ArrayList<ItemComparator> criterios, Connection connect) {
		return Search.find("SELECT * FROM perfil_social", criterios, true, connect!=null ? connect : Conexao.conectar(), connect==null);
	}

}
