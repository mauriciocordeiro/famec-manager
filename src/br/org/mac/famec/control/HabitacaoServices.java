package br.org.mac.famec.control;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.util.Result;

import com.tivic.manager.conexao.Conexao;

import br.org.mac.famec.model.Habitacao;
import br.org.mac.famec.model.HabitacaoDAO;

public class HabitacaoServices {

	public static Result save(Habitacao habitacao){
		return save(habitacao, null);
	}

	public static Result save(Habitacao habitacao, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.conectar();
				connect.setAutoCommit(false);
			}

			if(habitacao==null)
				return new Result(-1, "Erro ao salvar. Habitacao � nulo");

			int retorno;
			if(habitacao.getCdHabitacao()==0){
				retorno = HabitacaoDAO.insert(habitacao, connect);
				habitacao.setCdHabitacao(retorno);
			}
			else {
				retorno = HabitacaoDAO.update(habitacao, connect);
			}

			if(retorno<=0)
				Conexao.rollback(connect);
			else if (isConnectionNull)
				connect.commit();

			return new Result(retorno, (retorno<=0)?"Erro ao salvar...":"Salvo com sucesso...", "HABITACAO", habitacao);
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
	public static Result remove(int cdHabitacao){
		return remove(cdHabitacao, false, null);
	}
	public static Result remove(int cdHabitacao, boolean cascade){
		return remove(cdHabitacao, cascade, null);
	}
	public static Result remove(int cdHabitacao, boolean cascade, Connection connect){
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
			retorno = HabitacaoDAO.delete(cdHabitacao, connect);
			if(retorno<=0){
				Conexao.rollback(connect);
				return new Result(-2, "Este registro est� vinculado a outros e n�o pode ser exclu�do!");
			}
			else if (isConnectionNull)
				connect.commit();
			return new Result(1, "Registro exclu�do com sucesso!");
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
			pstmt = connect.prepareStatement("SELECT * FROM habitacao");
				return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoServices.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoServices.getAll: " + e);
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
		return Search.find("SELECT * FROM habitacao", criterios, true, connect!=null ? connect : Conexao.conectar(), connect==null);
	}

}
