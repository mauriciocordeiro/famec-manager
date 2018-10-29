package br.org.mac.famec.control;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.util.Result;

import com.tivic.manager.conexao.Conexao;

import br.org.mac.famec.model.EnderecoResponsavel;
import br.org.mac.famec.model.EnderecoResponsavelDAO;

public class EnderecoResponsavelServices {

	public static Result save(EnderecoResponsavel enderecoResponsavel){
		return save(enderecoResponsavel, null);
	}

	public static Result save(EnderecoResponsavel enderecoResponsavel, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.conectar();
				connect.setAutoCommit(false);
			}

			if(enderecoResponsavel==null)
				return new Result(-1, "Erro ao salvar. EnderecoResponsavel é nulo");

			int retorno;
			if(enderecoResponsavel.getCdEndereco()==0){
				retorno = EnderecoResponsavelDAO.insert(enderecoResponsavel, connect);
				enderecoResponsavel.setCdEndereco(retorno);
			}
			else {
				retorno = EnderecoResponsavelDAO.update(enderecoResponsavel, connect);
			}

			if(retorno<=0)
				Conexao.rollback(connect);
			else if (isConnectionNull)
				connect.commit();

			return new Result(retorno, (retorno<=0)?"Erro ao salvar...":"Salvo com sucesso...", "ENDERECORESPONSAVEL", enderecoResponsavel);
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
	public static Result remove(int cdEndereco){
		return remove(cdEndereco, false, null);
	}
	public static Result remove(int cdEndereco, boolean cascade){
		return remove(cdEndereco, cascade, null);
	}
	public static Result remove(int cdEndereco, boolean cascade, Connection connect){
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
			retorno = EnderecoResponsavelDAO.delete(cdEndereco, connect);
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
			pstmt = connect.prepareStatement("SELECT * FROM endereco_responsavel");
				return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelServices.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelServices.getAll: " + e);
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
		return Search.find("SELECT * FROM endereco_responsavel", criterios, true, connect!=null ? connect : Conexao.conectar(), connect==null);
	}

}
