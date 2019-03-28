package br.org.mac.famec.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import br.org.mac.famec.model.Aluno;
import br.org.mac.famec.model.AlunoDAO;
import br.org.mac.famec.util.Conexao;
import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.dao.Search;
import sol.util.Result;

public class AlunoServices {

	public static Result save(Aluno aluno){
		return save(aluno, null);
	}

	public static Result save(Aluno aluno, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.connect();
				connect.setAutoCommit(false);
			}

			if(aluno==null)
				return new Result(-1, "Erro ao salvar. Aluno é nulo");

			int retorno;
			if(aluno.getCdAluno()==0){
				retorno = AlunoDAO.insert(aluno, connect);
				aluno.setCdAluno(retorno);
			}
			else {
				retorno = AlunoDAO.update(aluno, connect);
			}

			if(retorno<=0)
				Conexao.rollback(connect);
			else if (isConnectionNull)
				connect.commit();

			return new Result(retorno, (retorno<=0)?"Erro ao salvar...":"Salvo com sucesso...", "ALUNO", aluno);
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
	public static Result remove(int cdAluno){
		return remove(cdAluno, false, null);
	}
	public static Result remove(int cdAluno, boolean cascade){
		return remove(cdAluno, cascade, null);
	}
	public static Result remove(int cdAluno, boolean cascade, Connection connect){
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
			retorno = AlunoDAO.delete(cdAluno, connect);
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
			pstmt = connect.prepareStatement("SELECT * FROM aluno");
				return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! AlunoServices.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoServices.getAll: " + e);
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
		return Search.find("SELECT * FROM aluno", criterios, connect!=null ? connect : Conexao.connect(), connect==null);
	}
	

	public static ResultSetMap quickFind(String nmAluno) {
		return quickFind(nmAluno, null);
	}

	public static ResultSetMap quickFind(String nmAluno, Connection connect) {
		try {
			connect = connect==null ? Conexao.connect() : connect;
			return new ResultSetMap(connect.prepareStatement(
					"SELECT cd_aluno, nm_aluno, cd_familia "
					+ " FROM aluno "
					+ " WHERE nm_aluno iLike '%"+nmAluno+"%'")
					.executeQuery());
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoServices.getAll: " + e);
			return null;
		}
		finally {
			Conexao.disconnect(connect);
		}
	}

}

