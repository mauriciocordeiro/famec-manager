package br.org.mac.famec.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import br.org.mac.famec.model.Aluno;
import br.org.mac.famec.model.AlunoDAO;
import br.org.mac.famec.util.Conexao;
import br.org.mac.famec.util.ReportUtils;
import br.org.mac.famec.util.Util;
import br.org.mac.midgard.util.Date;
import sol.dao.ItemComparator;
import sol.dao.ResultSetMap;
import sol.dao.Search;
import sol.util.Result;

public class AlunoServices {
	
	public static final String[] turnoInstituicao = {"", "Matutino", "Vespertino", "Noturno", "Diurno"};
	public static final String[] sexo= {"", "Masculino", "Feminino"};
	

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
		boolean isConnectionNull = connect==null;
		if (isConnectionNull)
			connect = Conexao.connect();
		try {
			ResultSetMap rsm = Search.find(
					" SELECT A.*,"
					+ " B.nr_prontuario, B.dt_cadastro,"
					+ " C.nm_responsavel, C.tp_parentesco, C.nr_telefone_1, C.nr_telefone_2,"
					+ " E.* "
					+ " FROM 			aluno 				 A"
					+ " JOIN 			familia 			 B ON (A.cd_familia = B.cd_familia)"
					+ " LEFT OUTER JOIN responsavel 		 C ON (B.cd_familia = C.cd_familia)"
					+ " LEFT OUTER JOIN usuario 			 D ON (B.cd_usuario_cadastro = D.cd_usuario)"
					+ " LEFT OUTER JOIN endereco_responsavel E ON (C.cd_responsavel = E.cd_responsavel)", 
					" ORDER BY B.nr_prontuario ", criterios, connect, false);
			
			while(rsm.next()) {
				if(rsm.getObject("hr_saida")!=null) {
					GregorianCalendar d = rsm.getGregorianCalendar("hr_saida");
					int hh = d.get(Calendar.HOUR_OF_DAY);
					int mm = d.get(Calendar.MINUTE);
					
					rsm.setValueToField("hr_saida", (hh<10 ? "0"+hh : hh)+":"+(mm<10 ? "0"+mm : mm));
				}
				
				rsm.setValueToField("ds_dt_nascimento", Date.formatDateTime(rsm.getGregorianCalendar("dt_nascimento"), "dd/MM/yyyy"));
				
				GregorianCalendar dt = rsm.getGregorianCalendar("dt_nascimento");
				rsm.setValueToField("nr_idade", Period.between(LocalDate.of(dt.get(Calendar.YEAR), dt.get(Calendar.MONTH)+1, dt.get(Calendar.DAY_OF_MONTH)), LocalDate.now()).getYears());
				
				rsm.setValueToField("nm_bairro", rsm.getString("nm_bairro"));
				rsm.setValueToField("ds_endereco", rsm.getString("nm_rua") + ", " + rsm.getString("nr_casa") + ", " + rsm.getString("nm_complemento"));
				
				rsm.setValueToField("nm_turno_famec", turnoInstituicao[rsm.getInt("tp_turno_famec")].toUpperCase());
			}
			rsm.beforeFirst();
			return rsm;
		} catch(Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Erro! AlunoServices.find: " + e);
			return null;
		}
		finally {
			if (isConnectionNull)
				Conexao.disconnect(connect);
		}
	}
	

	public static ResultSetMap quickFind(String nmAluno) {
		return quickFind(nmAluno, null);
	}

	public static ResultSetMap quickFind(String nmAluno, Connection connect) {
		connect = connect==null ? Conexao.connect() : connect;
		try {
			return new ResultSetMap(connect.prepareStatement(
					"SELECT cd_aluno, nm_aluno, cd_familia "
					+ " FROM aluno "
					+ " WHERE nm_aluno iLike '%"+nmAluno+"%'"
					+ " ORDER BY nm_aluno")
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
	
	public static Result printLista() {		
		try {
			
			ArrayList<ItemComparator> crt = new ArrayList<>();
			crt.add(new ItemComparator("B.st_aluno", Integer.toString(1), ItemComparator.EQUAL, Types.INTEGER));	
			ResultSetMap rsm = Search.find(
					  " SELECT A.*, B.*, C.*, D.*, E.*, F.*, G.nm_usuario "
					+ " FROM familia 				A"
					+ " JOIN aluno 					B ON (A.cd_familia = B.cd_familia)"
					+ " JOIN responsavel 			C ON (A.cd_familia = C.cd_familia)"
					+ " JOIN habitacao 			  	D ON (A.cd_familia = D.cd_familia)"
					+ " JOIN perfil_social 		  	E ON (A.cd_familia = E.cd_familia)"
					+ " JOIN endereco_responsavel 	F ON (C.cd_responsavel = F.cd_responsavel)"
					+ " LEFT OUTER JOIN	usuario		G ON (A.cd_usuario_cadastro = G.cd_usuario)", 
					  " ORDER BY B.nm_aluno ", 
					  crt, Conexao.connect(), true);
			
			HashMap<String, Object> params = new HashMap<>();
			params.put("LOGO", FamiliaServices.class.getResourceAsStream("/reports/img/famec_1.png"));
			params.put("DS_HOJE", Util.format(new GregorianCalendar(), "dd 'de' MMMM 'de' yyyy"));
			
			
			while(rsm.next()) {
				rsm.setValueToField("NM_TP_SEXO", AlunoServices.sexo[rsm.getInt("TP_SEXO")]);
				rsm.setValueToField("NM_LG_ALMOCO_INSTITUICAO", rsm.getInt("LG_ALMOCO_INSTITUICAO") == 1 ? "Sim" : "Não");
				rsm.setValueToField("NM_TP_TURNO_FAMEC", AlunoServices.turnoInstituicao[rsm.getInt("TP_TURNO_FAMEC")]);
				rsm.setValueToField("DS_DT_NASCIMENTO", sol.util.Util.convCalendarString(rsm.getGregorianCalendar("DT_NASCIMENTO")));
				
				GregorianCalendar dtN = rsm.getGregorianCalendar("DT_NASCIMENTO");
				LocalDate start = LocalDate.of(dtN.get(Calendar.YEAR), dtN.get(Calendar.MONTH)+1, dtN.get(Calendar.DAY_OF_MONTH));
				LocalDate end = LocalDate.now(); 
				long years = ChronoUnit.YEARS.between(start, end);
				rsm.setValueToField("NR_IDADE", Long.toString(years)+" anos");

				String nrTelefone = "";
				if(rsm.getString("NR_TELEFONE_1", null) != null) {
					nrTelefone += rsm.getString("NR_TELEFONE_1");
				}
				if(rsm.getString("NR_TELEFONE_2", null) != null) {
					nrTelefone += " "+rsm.getString("NR_TELEFONE_2");
				}
				if(rsm.getString("NR_TELEFONE_TRABALHO", null) != null) {
					nrTelefone += " "+rsm.getString("NR_TELEFONE_TRABALHO");
				}
				rsm.setValueToField("NR_TELEFONE", nrTelefone);
				
				if(rsm.getInt("LG_ACOMPANHANTE_SAIDA") > 0) {
					String dsAcompanhante = "acompanhado";
					
					if(rsm.getString("NM_ACOMPANHANTE_SAIDA", null) != null && !rsm.getString("NM_ACOMPANHANTE_SAIDA").trim().equals("")) {
						dsAcompanhante += " por "+rsm.getString("NM_ACOMPANHANTE_SAIDA");
					}
					
					rsm.setValueToField("DS_ACOMPANHANTE", dsAcompanhante);
				} else {
					rsm.setValueToField("DS_ACOMPANHANTE", "sozinho");
				}
				
				if(rsm.getGregorianCalendar("HR_SAIDA") != null) {
					GregorianCalendar hrSaida = rsm.getGregorianCalendar("HR_SAIDA");
					rsm.setValueToField("DS_HR_SAIDA", Util.format(hrSaida, "HH:mm"));
				} else {
					rsm.setValueToField("DS_HR_SAIDA", "qualquer");
				}
				
				rsm.setValueToField("NR_PRONTUARIO", Util.leadingZero(Integer.parseInt(rsm.getString("NR_PRONTUARIO")), 5));
				
				String dsEndereco = "";
				if(rsm.getString("NM_RUA") != null) {
					dsEndereco += rsm.getString("NM_RUA");
				}
				if(rsm.getInt("NR_CASA") > 0) {
					dsEndereco += ", "+rsm.getInt("NR_CASA");
				}
				if(rsm.getString("NM_BAIRRO") != null) {
					dsEndereco += ", "+rsm.getString("NM_BAIRRO");
				}
				rsm.setValueToField("DS_ENDERECO", dsEndereco);
			}
			rsm.beforeFirst();
			
			Result result = ReportUtils.generate("lista_aluno", params, rsm);
			
			return result;
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
			System.out.println("Erro! AlunoServices.printLista: " + e);
			return null;
		}
	}

}

