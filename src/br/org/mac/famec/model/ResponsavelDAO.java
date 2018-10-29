package br.org.mac.famec.model;

import java.sql.*;
import com.tivic.manager.conexao.Conexao;
import sol.dao.ResultSetMap;
import sol.dao.ItemComparator;
import sol.dao.Search;
import sol.dao.Util;
import java.util.ArrayList;

public class ResponsavelDAO{

	public static int insert(Responsavel objeto) {
		return insert(objeto, null);
	}

	public static int insert(Responsavel objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.conectar();
			int code = Conexao.getNextCode("responsavel", connect);
			if (code <= 0) {
				if (isConnectionNull)
					com.tivic.manager.conexao.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdResponsavel(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO responsavel (cd_responsavel,"+
			                                  "cd_familia,"+
			                                  "nm_responsavel,"+
			                                  "tp_parentesco,"+
			                                  "tp_genero,"+
			                                  "dt_nascimento,"+
			                                  "nm_naturalidade,"+
			                                  "tp_estado_civil,"+
			                                  "nr_telefone_1,"+
			                                  "nr_telefone_2,"+
			                                  "nr_rg,"+
			                                  "nm_orgao_expedidor_rg,"+
			                                  "sg_uf_rg,"+
			                                  "nr_cpf,"+
			                                  "ds_escolaridade,"+
			                                  "lg_estudante,"+
			                                  "tp_nivel_escolar,"+
			                                  "tp_turno,"+
			                                  "nm_ocupacao,"+
			                                  "vl_renda_mensal,"+
			                                  "nm_local_trabalho,"+
			                                  "nr_telefone_trabalho) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, code);
			if(objeto.getCdFamilia()==0)
				pstmt.setNull(2, Types.INTEGER);
			else
				pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setString(3,objeto.getNmResponsavel());
			pstmt.setInt(4,objeto.getTpParentesco());
			pstmt.setInt(5,objeto.getTpGenero());
			if(objeto.getDtNascimento()==null)
				pstmt.setNull(6, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(6,new Timestamp(objeto.getDtNascimento().getTimeInMillis()));
			pstmt.setString(7,objeto.getNmNaturalidade());
			pstmt.setInt(8,objeto.getTpEstadoCivil());
			pstmt.setString(9,objeto.getNrTelefone1());
			pstmt.setString(10,objeto.getNrTelefone2());
			pstmt.setString(11,objeto.getNrRg());
			pstmt.setString(12,objeto.getNmOrgaoExpedidorRg());
			pstmt.setString(13,objeto.getSgUfRg());
			pstmt.setString(14,objeto.getNrCpf());
			pstmt.setString(15,objeto.getDsEscolaridade());
			pstmt.setInt(16,objeto.getLgEstudante());
			pstmt.setInt(17,objeto.getTpNivelEscolar());
			pstmt.setInt(18,objeto.getTpTurno());
			pstmt.setString(19,objeto.getNmOcupacao());
			pstmt.setDouble(20,objeto.getVlRendaMensal());
			pstmt.setString(21,objeto.getNmLocalTrabalho());
			pstmt.setString(22,objeto.getNrTelefoneTrabalho());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static int update(Responsavel objeto) {
		return update(objeto, 0, null);
	}

	public static int update(Responsavel objeto, int cdResponsavelOld) {
		return update(objeto, cdResponsavelOld, null);
	}

	public static int update(Responsavel objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(Responsavel objeto, int cdResponsavelOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.conectar();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE responsavel SET cd_responsavel=?,"+
												      		   "cd_familia=?,"+
												      		   "nm_responsavel=?,"+
												      		   "tp_parentesco=?,"+
												      		   "tp_genero=?,"+
												      		   "dt_nascimento=?,"+
												      		   "nm_naturalidade=?,"+
												      		   "tp_estado_civil=?,"+
												      		   "nr_telefone_1=?,"+
												      		   "nr_telefone_2=?,"+
												      		   "nr_rg=?,"+
												      		   "nm_orgao_expedidor_rg=?,"+
												      		   "sg_uf_rg=?,"+
												      		   "nr_cpf=?,"+
												      		   "ds_escolaridade=?,"+
												      		   "lg_estudante=?,"+
												      		   "tp_nivel_escolar=?,"+
												      		   "tp_turno=?,"+
												      		   "nm_ocupacao=?,"+
												      		   "vl_renda_mensal=?,"+
												      		   "nm_local_trabalho=?,"+
												      		   "nr_telefone_trabalho=? WHERE cd_responsavel=?");
			pstmt.setInt(1,objeto.getCdResponsavel());
			if(objeto.getCdFamilia()==0)
				pstmt.setNull(2, Types.INTEGER);
			else
				pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setString(3,objeto.getNmResponsavel());
			pstmt.setInt(4,objeto.getTpParentesco());
			pstmt.setInt(5,objeto.getTpGenero());
			if(objeto.getDtNascimento()==null)
				pstmt.setNull(6, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(6,new Timestamp(objeto.getDtNascimento().getTimeInMillis()));
			pstmt.setString(7,objeto.getNmNaturalidade());
			pstmt.setInt(8,objeto.getTpEstadoCivil());
			pstmt.setString(9,objeto.getNrTelefone1());
			pstmt.setString(10,objeto.getNrTelefone2());
			pstmt.setString(11,objeto.getNrRg());
			pstmt.setString(12,objeto.getNmOrgaoExpedidorRg());
			pstmt.setString(13,objeto.getSgUfRg());
			pstmt.setString(14,objeto.getNrCpf());
			pstmt.setString(15,objeto.getDsEscolaridade());
			pstmt.setInt(16,objeto.getLgEstudante());
			pstmt.setInt(17,objeto.getTpNivelEscolar());
			pstmt.setInt(18,objeto.getTpTurno());
			pstmt.setString(19,objeto.getNmOcupacao());
			pstmt.setDouble(20,objeto.getVlRendaMensal());
			pstmt.setString(21,objeto.getNmLocalTrabalho());
			pstmt.setString(22,objeto.getNrTelefoneTrabalho());
			pstmt.setInt(23, cdResponsavelOld!=0 ? cdResponsavelOld : objeto.getCdResponsavel());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static int delete(int cdResponsavel) {
		return delete(cdResponsavel, null);
	}

	public static int delete(int cdResponsavel, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.conectar();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM responsavel WHERE cd_responsavel=?");
			pstmt.setInt(1, cdResponsavel);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static Responsavel get(int cdResponsavel) {
		return get(cdResponsavel, null);
	}

	public static Responsavel get(int cdResponsavel, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.conectar();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM responsavel WHERE cd_responsavel=?");
			pstmt.setInt(1, cdResponsavel);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Responsavel(rs.getInt("cd_responsavel"),
						rs.getInt("cd_familia"),
						rs.getString("nm_responsavel"),
						rs.getInt("tp_parentesco"),
						rs.getInt("tp_genero"),
						(rs.getTimestamp("dt_nascimento")==null)?null:Util.longToCalendar(rs.getTimestamp("dt_nascimento").getTime()),
						rs.getString("nm_naturalidade"),
						rs.getInt("tp_estado_civil"),
						rs.getString("nr_telefone_1"),
						rs.getString("nr_telefone_2"),
						rs.getString("nr_rg"),
						rs.getString("nm_orgao_expedidor_rg"),
						rs.getString("sg_uf_rg"),
						rs.getString("nr_cpf"),
						rs.getString("ds_escolaridade"),
						rs.getInt("lg_estudante"),
						rs.getInt("tp_nivel_escolar"),
						rs.getInt("tp_turno"),
						rs.getString("nm_ocupacao"),
						rs.getDouble("vl_renda_mensal"),
						rs.getString("nm_local_trabalho"),
						rs.getString("nr_telefone_trabalho"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.get: " + e);
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
			pstmt = connect.prepareStatement("SELECT * FROM responsavel");
			return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.desconectar(connect);
		}
	}

	public static ArrayList<Responsavel> getList() {
		return getList(null);
	}

	public static ArrayList<Responsavel> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.conectar();
		try {
			ArrayList<Responsavel> list = new ArrayList<Responsavel>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				Responsavel obj = ResponsavelDAO.get(rsm.getInt("cd_responsavel"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! ResponsavelDAO.getList: " + e);
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
		return Search.find("SELECT * FROM responsavel", criterios, true, connect!=null ? connect : Conexao.conectar(), connect==null);
	}

}
