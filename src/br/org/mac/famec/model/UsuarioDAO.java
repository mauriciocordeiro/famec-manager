package br.org.mac.famec.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.org.mac.famec.util.Conexao;

import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.dao.Search;

public class UsuarioDAO{

	public static int insert(Usuario objeto) {
		return insert(objeto, null);
	}

	public static int insert(Usuario objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			int code = Conexao.getCode("usuario", connect);
			if (code <= 0) {
				if (isConnectionNull)
					com.tivic.manager.conexao.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdUsuario(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO usuario (cd_usuario,"+
			                                  "nm_usuario,"+
			                                  "nm_login,"+
			                                  "nm_senha,"+
			                                  "nm_email,"+
			                                  "st_usuario,"+
			                                  "nm_funcao) VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, code);
			pstmt.setString(2,objeto.getNmUsuario());
			pstmt.setString(3,objeto.getNmLogin());
			pstmt.setString(4,objeto.getNmSenha());
			pstmt.setString(5,objeto.getNmEmail());
			pstmt.setInt(6,objeto.getStUsuario());
			pstmt.setString(7,objeto.getNmFuncao());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int update(Usuario objeto) {
		return update(objeto, 0, null);
	}

	public static int update(Usuario objeto, int cdUsuarioOld) {
		return update(objeto, cdUsuarioOld, null);
	}

	public static int update(Usuario objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(Usuario objeto, int cdUsuarioOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE usuario SET cd_usuario=?,"+
												      		   "nm_usuario=?,"+
												      		   "nm_login=?,"+
												      		   "nm_senha=?,"+
												      		   "nm_email=?,"+
												      		   "st_usuario=?,"+
												      		   "nm_funcao=? WHERE cd_usuario=?");
			pstmt.setInt(1,objeto.getCdUsuario());
			pstmt.setString(2,objeto.getNmUsuario());
			pstmt.setString(3,objeto.getNmLogin());
			pstmt.setString(4,objeto.getNmSenha());
			pstmt.setString(5,objeto.getNmEmail());
			pstmt.setInt(6,objeto.getStUsuario());
			pstmt.setString(7,objeto.getNmFuncao());
			pstmt.setInt(8, cdUsuarioOld!=0 ? cdUsuarioOld : objeto.getCdUsuario());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int delete(int cdUsuario) {
		return delete(cdUsuario, null);
	}

	public static int delete(int cdUsuario, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM usuario WHERE cd_usuario=?");
			pstmt.setInt(1, cdUsuario);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static Usuario get(int cdUsuario) {
		return get(cdUsuario, null);
	}

	public static Usuario get(int cdUsuario, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM usuario WHERE cd_usuario=?");
			pstmt.setInt(1, cdUsuario);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Usuario(rs.getInt("cd_usuario"),
						rs.getString("nm_usuario"),
						rs.getString("nm_login"),
						rs.getString("nm_senha"),
						rs.getString("nm_email"),
						rs.getInt("st_usuario"),
						rs.getString("nm_funcao"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.get: " + e);
			return null;
		}
		finally {
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
			System.err.println("Erro! UsuarioDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static ArrayList<Usuario> getList() {
		return getList(null);
	}

	public static ArrayList<Usuario> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		try {
			ArrayList<Usuario> list = new ArrayList<Usuario>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				Usuario obj = UsuarioDAO.get(rsm.getInt("cd_usuario"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! UsuarioDAO.getList: " + e);
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
		return Search.find("SELECT * FROM usuario", criterios, true, connect!=null ? connect : Conexao.connect(), connect==null);
	}

}
