package br.org.mac.famec.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import br.org.mac.famec.util.Conexao;

import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.dao.Search;

public class PerfilSocialDAO{

	public static int insert(PerfilSocial objeto) {
		return insert(objeto, null);
	}

	public static int insert(PerfilSocial objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			int code = Conexao.getCode("perfil_social", connect);
			if (code <= 0) {
				if (isConnectionNull)
					com.tivic.manager.conexao.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdPerfilSocial(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO perfil_social (cd_perfil_social,"+
			                                  "cd_familia,"+
			                                  "lg_nis,"+
			                                  "nr_nis,"+
			                                  "lg_beneficio,"+
			                                  "nm_beneficio,"+
			                                  "vl_beneficio) VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, code);
			if(objeto.getCdFamilia()==0)
				pstmt.setNull(2, Types.INTEGER);
			else
				pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setInt(3,objeto.getLgNis());
			pstmt.setString(4,objeto.getNrNis());
			pstmt.setInt(5,objeto.getLgBeneficio());
			pstmt.setString(6,objeto.getNmBeneficio());
			pstmt.setDouble(7,objeto.getVlBeneficio());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int update(PerfilSocial objeto) {
		return update(objeto, 0, null);
	}

	public static int update(PerfilSocial objeto, int cdPerfilSocialOld) {
		return update(objeto, cdPerfilSocialOld, null);
	}

	public static int update(PerfilSocial objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(PerfilSocial objeto, int cdPerfilSocialOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE perfil_social SET cd_perfil_social=?,"+
												      		   "cd_familia=?,"+
												      		   "lg_nis=?,"+
												      		   "nr_nis=?,"+
												      		   "lg_beneficio=?,"+
												      		   "nm_beneficio=?,"+
												      		   "vl_beneficio=? WHERE cd_perfil_social=?");
			pstmt.setInt(1,objeto.getCdPerfilSocial());
			if(objeto.getCdFamilia()==0)
				pstmt.setNull(2, Types.INTEGER);
			else
				pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setInt(3,objeto.getLgNis());
			pstmt.setString(4,objeto.getNrNis());
			pstmt.setInt(5,objeto.getLgBeneficio());
			pstmt.setString(6,objeto.getNmBeneficio());
			pstmt.setDouble(7,objeto.getVlBeneficio());
			pstmt.setInt(8, cdPerfilSocialOld!=0 ? cdPerfilSocialOld : objeto.getCdPerfilSocial());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int delete(int cdPerfilSocial) {
		return delete(cdPerfilSocial, null);
	}

	public static int delete(int cdPerfilSocial, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM perfil_social WHERE cd_perfil_social=?");
			pstmt.setInt(1, cdPerfilSocial);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static PerfilSocial get(int cdPerfilSocial) {
		return get(cdPerfilSocial, null);
	}

	public static PerfilSocial get(int cdPerfilSocial, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM perfil_social WHERE cd_perfil_social=?");
			pstmt.setInt(1, cdPerfilSocial);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new PerfilSocial(rs.getInt("cd_perfil_social"),
						rs.getInt("cd_familia"),
						rs.getInt("lg_nis"),
						rs.getString("nr_nis"),
						rs.getInt("lg_beneficio"),
						rs.getString("nm_beneficio"),
						rs.getDouble("vl_beneficio"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.get: " + e);
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
			pstmt = connect.prepareStatement("SELECT * FROM perfil_social");
			return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static ArrayList<PerfilSocial> getList() {
		return getList(null);
	}

	public static ArrayList<PerfilSocial> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		try {
			ArrayList<PerfilSocial> list = new ArrayList<PerfilSocial>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				PerfilSocial obj = PerfilSocialDAO.get(rsm.getInt("cd_perfil_social"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! PerfilSocialDAO.getList: " + e);
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
		return Search.find("SELECT * FROM perfil_social", criterios, true, connect!=null ? connect : Conexao.connect(), connect==null);
	}

}
