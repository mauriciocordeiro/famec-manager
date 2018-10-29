package br.org.mac.famec.model;

import java.sql.*;
import com.tivic.manager.conexao.Conexao;
import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.dao.Util;
import java.util.ArrayList;

public class FamiliaDAO{

	public static int insert(Familia objeto) {
		return insert(objeto, null);
	}

	public static int insert(Familia objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.conectar();
			int code = Conexao.getNextCode("familia", connect);
			if (code <= 0) {
				if (isConnectionNull)
					com.tivic.manager.conexao.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdFamilia(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO familia (cd_familia,"+
			                                  "dt_cadastro,"+
			                                  "cd_usuario_cadastro) VALUES (?, ?, ?)");
			pstmt.setInt(1, code);
			if(objeto.getDtCadastro()==null)
				pstmt.setNull(2, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(2,new Timestamp(objeto.getDtCadastro().getTimeInMillis()));
			if(objeto.getCdUsuarioCadastro()==0)
				pstmt.setNull(3, Types.INTEGER);
			else
				pstmt.setInt(3,objeto.getCdUsuarioCadastro());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static int update(Familia objeto) {
		return update(objeto, 0, null);
	}

	public static int update(Familia objeto, int cdFamiliaOld) {
		return update(objeto, cdFamiliaOld, null);
	}

	public static int update(Familia objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(Familia objeto, int cdFamiliaOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.conectar();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE familia SET cd_familia=?,"+
												      		   "dt_cadastro=?,"+
												      		   "cd_usuario_cadastro=? WHERE cd_familia=?");
			pstmt.setInt(1,objeto.getCdFamilia());
			if(objeto.getDtCadastro()==null)
				pstmt.setNull(2, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(2,new Timestamp(objeto.getDtCadastro().getTimeInMillis()));
			if(objeto.getCdUsuarioCadastro()==0)
				pstmt.setNull(3, Types.INTEGER);
			else
				pstmt.setInt(3,objeto.getCdUsuarioCadastro());
			pstmt.setInt(4, cdFamiliaOld!=0 ? cdFamiliaOld : objeto.getCdFamilia());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static int delete(int cdFamilia) {
		return delete(cdFamilia, null);
	}

	public static int delete(int cdFamilia, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.conectar();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM familia WHERE cd_familia=?");
			pstmt.setInt(1, cdFamilia);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static Familia get(int cdFamilia) {
		return get(cdFamilia, null);
	}

	public static Familia get(int cdFamilia, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.conectar();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM familia WHERE cd_familia=?");
			pstmt.setInt(1, cdFamilia);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Familia(rs.getInt("cd_familia"),
						(rs.getTimestamp("dt_cadastro")==null)?null:Util.longToCalendar(rs.getTimestamp("dt_cadastro").getTime()),
						rs.getInt("cd_usuario_cadastro"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.get: " + e);
			return null;
		}
		finally {
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
			pstmt = connect.prepareStatement("SELECT * FROM familia");
			return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static ArrayList<Familia> getList() {
		return getList(null);
	}

	public static ArrayList<Familia> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.conectar();
		try {
			ArrayList<Familia> list = new ArrayList<Familia>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				Familia obj = FamiliaDAO.get(rsm.getInt("cd_familia"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! FamiliaDAO.getList: " + e);
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
		return Search.find("SELECT * FROM familia", criterios, true, connect!=null ? connect : Conexao.conectar(), connect==null);
	}

}
