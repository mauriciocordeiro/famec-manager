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

public class EnderecoResponsavelDAO{

	public static int insert(EnderecoResponsavel objeto) {
		return insert(objeto, null);
	}

	public static int insert(EnderecoResponsavel objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			int code = Conexao.getCode("endereco_responsavel", connect);
			if (code <= 0) {
				if (isConnectionNull)
					br.org.mac.famec.util.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdEndereco(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO endereco_responsavel (cd_endereco_responsavel,"+
			                                  "cd_responsavel,"+
			                                  "nm_rua,"+
			                                  "nr_casa,"+
			                                  "nm_complemento,"+
			                                  "nm_bairro,"+
			                                  "nm_cidade,"+
			                                  "nm_estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, code);
			pstmt.setInt(2,objeto.getCdResponsavel());
			pstmt.setString(3,objeto.getNmRua());
			pstmt.setInt(4,objeto.getNrCasa());
			pstmt.setString(5,objeto.getNmComplemento());
			pstmt.setString(6,objeto.getNmBairro());
			pstmt.setString(7,objeto.getNmCidade());
			pstmt.setString(8,objeto.getNmEstado());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int update(EnderecoResponsavel objeto) {
		return update(objeto, 0, null);
	}

	public static int update(EnderecoResponsavel objeto, int cdEnderecoOld) {
		return update(objeto, cdEnderecoOld, null);
	}

	public static int update(EnderecoResponsavel objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(EnderecoResponsavel objeto, int cdEnderecoOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE endereco_responsavel SET cd_endereco_responsavel=?,"+
												      		   "cd_responsavel=?,"+
												      		   "nm_rua=?,"+
												      		   "nr_casa=?,"+
												      		   "nm_complemento=?,"+
												      		   "nm_bairro=?,"+
												      		   "nm_cidade=?,"+
												      		   "nm_estado=? WHERE cd_endereco_responsavel=?");
			pstmt.setInt(1,objeto.getCdEndereco());
			pstmt.setInt(2,objeto.getCdResponsavel());
			pstmt.setString(3,objeto.getNmRua());
			pstmt.setInt(4,objeto.getNrCasa());
			pstmt.setString(5,objeto.getNmComplemento());
			pstmt.setString(6,objeto.getNmBairro());
			pstmt.setString(7,objeto.getNmCidade());
			pstmt.setString(8,objeto.getNmEstado());
			pstmt.setInt(9, cdEnderecoOld!=0 ? cdEnderecoOld : objeto.getCdEndereco());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int delete(int cdEndereco) {
		return delete(cdEndereco, null);
	}

	public static int delete(int cdEndereco, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM endereco_responsavel WHERE cd_endereco_responsavel=?");
			pstmt.setInt(1, cdEndereco);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static EnderecoResponsavel get(int cdEndereco) {
		return get(cdEndereco, null);
	}

	public static EnderecoResponsavel get(int cdEndereco, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM endereco_responsavel WHERE cd_endereco_responsavel=?");
			pstmt.setInt(1, cdEndereco);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new EnderecoResponsavel(rs.getInt("cd_endereco_responsavel"),
						rs.getInt("cd_responsavel"),
						rs.getString("nm_rua"),
						rs.getInt("nr_casa"),
						rs.getString("nm_complemento"),
						rs.getString("nm_bairro"),
						rs.getString("nm_cidade"),
						rs.getString("nm_estado"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.get: " + e);
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
			pstmt = connect.prepareStatement("SELECT * FROM endereco_responsavel");
			return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static ArrayList<EnderecoResponsavel> getList() {
		return getList(null);
	}

	public static ArrayList<EnderecoResponsavel> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		try {
			ArrayList<EnderecoResponsavel> list = new ArrayList<EnderecoResponsavel>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				EnderecoResponsavel obj = EnderecoResponsavelDAO.get(rsm.getInt("cd_endereco_responsavel"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! EnderecoResponsavelDAO.getList: " + e);
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
		return Search.find("SELECT * FROM endereco_responsavel", criterios, true, connect!=null ? connect : Conexao.connect(), connect==null);
	}

}
