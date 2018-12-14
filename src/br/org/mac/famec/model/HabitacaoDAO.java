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

public class HabitacaoDAO{

	public static int insert(Habitacao objeto) {
		return insert(objeto, null);
	}

	public static int insert(Habitacao objeto, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			int code = Conexao.getCode("habitacao", connect);
			if (code <= 0) {
				if (isConnectionNull)
					com.tivic.manager.conexao.Conexao.rollback(connect);
				return -1;
			}
			objeto.setCdHabitacao(code);
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO habitacao (cd_habitacao,"+
			                                  "cd_familia,"+
			                                  "tp_situacao,"+
			                                  "vl_aluguel,"+
			                                  "nr_comodos,"+
			                                  "tp_abastecimento,"+
			                                  "tp_tratamento_agua,"+
			                                  "tp_iluminacao,"+
			                                  "tp_escoamento_sanitario,"+
			                                  "tp_destino_lixo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, code);
			if(objeto.getCdFamilia()==0)
				pstmt.setNull(2, Types.INTEGER);
			else
				pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setInt(3,objeto.getTpSituacao());
			pstmt.setDouble(4,objeto.getVlAluguel());
			pstmt.setInt(5,objeto.getNrComodos());
			pstmt.setInt(6,objeto.getTpAbastecimento());
			pstmt.setInt(7,objeto.getTpTratamentoAgua());
			pstmt.setInt(8,objeto.getTpIluminacao());
			pstmt.setInt(9,objeto.getTpEscoamentoSanitario());
			pstmt.setInt(10,objeto.getTpDestinoLixo());
			pstmt.executeUpdate();
			return code;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.insert: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.insert: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int update(Habitacao objeto) {
		return update(objeto, 0, null);
	}

	public static int update(Habitacao objeto, int cdHabitacaoOld) {
		return update(objeto, cdHabitacaoOld, null);
	}

	public static int update(Habitacao objeto, Connection connect) {
		return update(objeto, 0, connect);
	}

	public static int update(Habitacao objeto, int cdHabitacaoOld, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("UPDATE habitacao SET cd_habitacao=?,"+
												      		   "cd_familia=?,"+
												      		   "tp_situacao=?,"+
												      		   "vl_aluguel=?,"+
												      		   "nr_comodos=?,"+
												      		   "tp_abastecimento=?,"+
												      		   "tp_tratamento_agua=?,"+
												      		   "tp_iluminacao=?,"+
												      		   "tp_escoamento_sanitario=?,"+
												      		   "tp_destino_lixo=? WHERE cd_habitacao=?");
			pstmt.setInt(1,objeto.getCdHabitacao());
			if(objeto.getCdFamilia()==0)
				pstmt.setNull(2, Types.INTEGER);
			else
				pstmt.setInt(2,objeto.getCdFamilia());
			pstmt.setInt(3,objeto.getTpSituacao());
			pstmt.setDouble(4,objeto.getVlAluguel());
			pstmt.setInt(5,objeto.getNrComodos());
			pstmt.setInt(6,objeto.getTpAbastecimento());
			pstmt.setInt(7,objeto.getTpTratamentoAgua());
			pstmt.setInt(8,objeto.getTpIluminacao());
			pstmt.setInt(9,objeto.getTpEscoamentoSanitario());
			pstmt.setInt(10,objeto.getTpDestinoLixo());
			pstmt.setInt(11, cdHabitacaoOld!=0 ? cdHabitacaoOld : objeto.getCdHabitacao());
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.update: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.update: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static int delete(int cdHabitacao) {
		return delete(cdHabitacao, null);
	}

	public static int delete(int cdHabitacao, Connection connect){
		boolean isConnectionNull = connect==null;
		try {
			if (isConnectionNull)
				connect = Conexao.connect();
			PreparedStatement pstmt = connect.prepareStatement("DELETE FROM habitacao WHERE cd_habitacao=?");
			pstmt.setInt(1, cdHabitacao);
			pstmt.executeUpdate();
			return 1;
		}
		catch(SQLException sqlExpt){
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.delete: " + sqlExpt);
			return (-1)*sqlExpt.getErrorCode();
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.delete: " +  e);
			return -1;
		}
		finally{
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static Habitacao get(int cdHabitacao) {
		return get(cdHabitacao, null);
	}

	public static Habitacao get(int cdHabitacao, Connection connect){
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = connect.prepareStatement("SELECT * FROM habitacao WHERE cd_habitacao=?");
			pstmt.setInt(1, cdHabitacao);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return new Habitacao(rs.getInt("cd_habitacao"),
						rs.getInt("cd_familia"),
						rs.getInt("tp_situacao"),
						rs.getDouble("vl_aluguel"),
						rs.getInt("nr_comodos"),
						rs.getInt("tp_abastecimento"),
						rs.getInt("tp_tratamento_agua"),
						rs.getInt("tp_iluminacao"),
						rs.getInt("tp_escoamento_sanitario"),
						rs.getInt("tp_destino_lixo"));
			}
			else{
				return null;
			}
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.get: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.get: " + e);
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
			pstmt = connect.prepareStatement("SELECT * FROM habitacao");
			return new ResultSetMap(pstmt.executeQuery());
		}
		catch(SQLException sqlExpt) {
			sqlExpt.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.getAll: " + sqlExpt);
			return null;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.getAll: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}

	public static ArrayList<Habitacao> getList() {
		return getList(null);
	}

	public static ArrayList<Habitacao> getList(Connection connect) {
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		try {
			ArrayList<Habitacao> list = new ArrayList<Habitacao>();
			ResultSetMap rsm = getAll(connect);
			while(rsm.next()){
				Habitacao obj = HabitacaoDAO.get(rsm.getInt("cd_habitacao"), connect);
				list.add(obj);
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! HabitacaoDAO.getList: " + e);
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
		return Search.find("SELECT * FROM habitacao", criterios, true, connect!=null ? connect : Conexao.connect(), connect==null);
	}

}
