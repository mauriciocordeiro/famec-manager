package br.org.mac.famec.control;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.util.Result;

import br.org.mac.famec.model.Usuario;
import br.org.mac.famec.model.UsuarioDAO;
import br.org.mac.famec.util.Conexao;

public class UsuarioServices {

	public static Result save(Usuario usuario){
		return save(usuario, null);
	}

	public static Result save(Usuario usuario, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.connect();
				connect.setAutoCommit(false);
			}

			if(usuario==null)
				return new Result(-1, "Erro ao salvar. Usuario � nulo");

			int retorno;
			if(usuario.getCdUsuario()==0){
				if(connect.prepareStatement("SELECT cd_usuario FROM usuario WHERE nm_login = '"+usuario.getNmLogin()+"'").executeQuery().next()) {
					if(isConnectionNull)
						connect.rollback();
					return new Result(-2, "Login j� utilizado.");
				}
				
				retorno = UsuarioDAO.insert(usuario, connect);
				usuario.setCdUsuario(retorno);
			}
			else {
				retorno = UsuarioDAO.update(usuario, connect);
			}

			if(retorno<=0)
				Conexao.rollback(connect);
			else if (isConnectionNull)
				connect.commit();

			return new Result(retorno, (retorno<=0)?"Erro ao salvar...":"Salvo com sucesso...", "USUARIO", usuario);
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			if (isConnectionNull)
				Conexao.rollback(connect);
			return new Result(-1, e.getMessage());
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}
	public static Result remove(int cdUsuario){
		return remove(cdUsuario, false, null);
	}
	public static Result remove(int cdUsuario, boolean cascade){
		return remove(cdUsuario, cascade, null);
	}
	public static Result remove(int cdUsuario, boolean cascade, Connection connect){
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
			retorno = UsuarioDAO.delete(cdUsuario, connect);
			if(retorno<=0){
				Conexao.rollback(connect);
				return new Result(-2, "Este registro est� vinculado a outros e n�o pode ser exclu�do!");
			}
			else if (isConnectionNull)
				connect.commit();
			return new Result(1, "Registro exclu�do com sucesso!");
		}
		catch(Exception e){
			e.printStackTrace(System.out);
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
			pstmt = connect.prepareStatement("SELECT * FROM usuario");
				return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! UsuarioServices.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioServices.getAll: " + e);
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
		return Search.find("SELECT * FROM usuario", criterios, connect!=null ? connect : Conexao.connect(), connect==null);
	}
	
	
	public static Result autenticar(String nmLogin, String nmSenha){
		return autenticar(nmLogin, nmSenha, null);
	}

	public static Result autenticar(String nmLogin, String nmSenha, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull) {
				connect = Conexao.connect();
			}

			int retorno = 0;
			Usuario usuario = null;
			
			PreparedStatement ps = connect.prepareStatement("SELECT cd_usuario FROM usuario WHERE nm_login=? AND nm_senha=?");
			ps.setString(1, nmLogin);
			ps.setString(2, nmSenha);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				retorno = rs.getInt("cd_usuario");
				usuario = UsuarioDAO.get(retorno, connect);
			}

			return new Result(retorno, (retorno<=0)?"Erro ao autenticar...":"Autenticado com sucesso...", "USUARIO", usuario);
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			return new Result(-1, e.getMessage());
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

}
