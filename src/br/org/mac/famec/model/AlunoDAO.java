package br.org.mac.famec.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import br.org.mac.famec.util.Conexao;

import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.dao.Search;
import sol.dao.Util;

public class AlunoDAO{

	public static int insert(Aluno objeto) {
		return insert(objeto, null);
	}

	public static int insert(Aluno objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			int code = Conexao.getCode("aluno", connect);
			if (code <= 0) {
				if (isConnectionNull)
					br.org.mac.famec.util.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdAluno(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO aluno (cd_aluno,"+
			                                  "cd_familia,"+
			                                  "nm_aluno,"+
			                                  "dt_nascimento,"+
			                                  "tp_sexo,"+
			                                  "nm_naturalidade,"+
			                                  "nm_escola,"+
			                                  "nr_nivel_escolar,"+
			                                  "tp_modalidade_escolar,"+
			                                  "tp_horario_escolar,"+
			                                  "tp_turno_famec,"+
			                                  "st_aluno,"+
			                                  "hr_saida,"+
			                                  "lg_acompanhante_saida,"+
			                                  "nm_acompanhante_saida,"+
			                                  "lg_almoco_instituicao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, code);
			pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setString(3,objeto.getNmAluno());
			if(objeto.getDtNascimento()==null)
				pstmt.setNull(4, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(4,new Timestamp(objeto.getDtNascimento().getTimeInMillis()));
			pstmt.setInt(5,objeto.getTpSexo());
			pstmt.setString(6,objeto.getNmNaturalidade());
			pstmt.setString(7,objeto.getNmEscola());
			pstmt.setInt(8,objeto.getNrNivelEscolar());
			pstmt.setInt(9,objeto.getTpModalidadeEscolar());
			pstmt.setInt(10,objeto.getTpHorarioEscolar());
			pstmt.setInt(11,objeto.getTpTurnoFamec());
			pstmt.setInt(12,objeto.getStAluno());
			if(objeto.getHrSaida()==null)
				pstmt.setNull(13, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(13,new Timestamp(objeto.getHrSaida().getTimeInMillis()));
			pstmt.setInt(14,objeto.getLgAcompanhanteSaida());
			pstmt.setString(15,objeto.getNmAcompanhanteSaida());
			pstmt.setInt(16,objeto.getLgAlmocoInstituicao());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int update(Aluno objeto) {
		return update(objeto, 0, null);
	}

	public static int update(Aluno objeto, int cdAlunoOld) {
		return update(objeto, cdAlunoOld, null);
	}

	public static int update(Aluno objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(Aluno objeto, int cdAlunoOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE aluno SET cd_aluno=?,"+
												      		   "cd_familia=?,"+
												      		   "nm_aluno=?,"+
												      		   "dt_nascimento=?,"+
												      		   "tp_sexo=?,"+
												      		   "nm_naturalidade=?,"+
												      		   "nm_escola=?,"+
												      		   "nr_nivel_escolar=?,"+
												      		   "tp_modalidade_escolar=?,"+
												      		   "tp_horario_escolar=?,"+
												      		   "tp_turno_famec=?,"+
												      		   "st_aluno=?,"+
												      		   "hr_saida=?,"+
												      		   "lg_acompanhante_saida=?,"+
												      		   "nm_acompanhante_saida=?,"+
												      		   "lg_almoco_instituicao=? WHERE cd_aluno=?");
			pstmt.setInt(1,objeto.getCdAluno());
			pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setString(3,objeto.getNmAluno());
			if(objeto.getDtNascimento()==null)
				pstmt.setNull(4, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(4,new Timestamp(objeto.getDtNascimento().getTimeInMillis()));
			pstmt.setInt(5,objeto.getTpSexo());
			pstmt.setString(6,objeto.getNmNaturalidade());
			pstmt.setString(7,objeto.getNmEscola());
			pstmt.setInt(8,objeto.getNrNivelEscolar());
			pstmt.setInt(9,objeto.getTpModalidadeEscolar());
			pstmt.setInt(10,objeto.getTpHorarioEscolar());
			pstmt.setInt(11,objeto.getTpTurnoFamec());
			pstmt.setInt(12,objeto.getStAluno());
			if(objeto.getHrSaida()==null)
				pstmt.setNull(13, Types.TIMESTAMP);
			else
				pstmt.setTimestamp(13,new Timestamp(objeto.getHrSaida().getTimeInMillis()));
			pstmt.setInt(14,objeto.getLgAcompanhanteSaida());
			pstmt.setString(15,objeto.getNmAcompanhanteSaida());
			pstmt.setInt(16,objeto.getLgAlmocoInstituicao());
			pstmt.setInt(17, cdAlunoOld!=0 ? cdAlunoOld : objeto.getCdAluno());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int delete(int cdAluno) {
		return delete(cdAluno, null);
	}

	public static int delete(int cdAluno, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM aluno WHERE cd_aluno=?");
			pstmt.setInt(1, cdAluno);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static Aluno get(int cdAluno) {
		return get(cdAluno, null);
	}

	public static Aluno get(int cdAluno, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM aluno WHERE cd_aluno=?");
			pstmt.setInt(1, cdAluno);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Aluno(rs.getInt("cd_aluno"),
						rs.getInt("cd_familia"),
						rs.getString("nm_aluno"),
						(rs.getTimestamp("dt_nascimento")==null)?null:Util.longToCalendar(rs.getTimestamp("dt_nascimento").getTime()),
						rs.getInt("tp_sexo"),
						rs.getString("nm_naturalidade"),
						rs.getString("nm_escola"),
						rs.getInt("nr_nivel_escolar"),
						rs.getInt("tp_modalidade_escolar"),
						rs.getInt("tp_horario_escolar"),
						rs.getInt("tp_turno_famec"),
						rs.getInt("st_aluno"),
						(rs.getTimestamp("hr_saida")==null)?null:Util.longToCalendar(rs.getTimestamp("hr_saida").getTime()),
						rs.getInt("lg_acompanhante_saida"),
						rs.getString("nm_acompanhante_saida"),
						rs.getInt("lg_almoco_instituicao"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.get: " + e);
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
			pstmt = connect.prepareStatement("SELECT * FROM aluno");
			return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static ArrayList<Aluno> getList() {
		return getList(null);
	}

	public static ArrayList<Aluno> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		try {
			ArrayList<Aluno> list = new ArrayList<Aluno>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				Aluno obj = AlunoDAO.get(rsm.getInt("cd_aluno"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoDAO.getList: " + e);
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
		return Search.find("SELECT * FROM aluno", criterios, true, connect!=null ? connect : Conexao.connect(), connect==null);
	}

}
