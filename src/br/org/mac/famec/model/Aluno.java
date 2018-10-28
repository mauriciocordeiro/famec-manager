package br.org.mac.famec.model;

import java.util.GregorianCalendar;

public class Aluno {

	private int cdAluno;
	private int cdFamilia;
	private String nmAluno;
	private GregorianCalendar dtNascimento;
	private int tpSexo;
	private String nmNaturalidade;
	private String nmEscola;
	private int nrNivelEscolar;
	private int tpModalidadeEscolar;
	private int tpHorarioEscolar;
	private int tpTurnoFamec;
	private int stAluno;
	private GregorianCalendar hrSaida;
	private int lgAcompanhanteSaida;
	private String nmAcompanhanteSaida;
	private int lgAlmocoInstituicao;

	public Aluno(){ }

	public Aluno(int cdAluno,
			int cdFamilia,
			String nmAluno,
			GregorianCalendar dtNascimento,
			int tpSexo,
			String nmNaturalidade,
			String nmEscola,
			int nrNivelEscolar,
			int tpModalidadeEscolar,
			int tpHorarioEscolar,
			int tpTurnoFamec,
			int stAluno,
			GregorianCalendar hrSaida,
			int lgAcompanhanteSaida,
			String nmAcompanhanteSaida,
			int lgAlmocoInstituicao){
		setCdAluno(cdAluno);
		setCdFamilia(cdFamilia);
		setNmAluno(nmAluno);
		setDtNascimento(dtNascimento);
		setTpSexo(tpSexo);
		setNmNaturalidade(nmNaturalidade);
		setNmEscola(nmEscola);
		setNrNivelEscolar(nrNivelEscolar);
		setTpModalidadeEscolar(tpModalidadeEscolar);
		setTpHorarioEscolar(tpHorarioEscolar);
		setTpTurnoFamec(tpTurnoFamec);
		setStAluno(stAluno);
		setHrSaida(hrSaida);
		setLgAcompanhanteSaida(lgAcompanhanteSaida);
		setNmAcompanhanteSaida(nmAcompanhanteSaida);
		setLgAlmocoInstituicao(lgAlmocoInstituicao);
	}
	public void setCdAluno(int cdAluno){
		this.cdAluno=cdAluno;
	}
	public int getCdAluno(){
		return this.cdAluno;
	}
	public void setCdFamilia(int cdFamilia){
		this.cdFamilia=cdFamilia;
	}
	public int getCdFamilia(){
		return this.cdFamilia;
	}
	public void setNmAluno(String nmAluno){
		this.nmAluno=nmAluno;
	}
	public String getNmAluno(){
		return this.nmAluno;
	}
	public void setDtNascimento(GregorianCalendar dtNascimento){
		this.dtNascimento=dtNascimento;
	}
	public GregorianCalendar getDtNascimento(){
		return this.dtNascimento;
	}
	public void setTpSexo(int tpSexo){
		this.tpSexo=tpSexo;
	}
	public int getTpSexo(){
		return this.tpSexo;
	}
	public void setNmNaturalidade(String nmNaturalidade){
		this.nmNaturalidade=nmNaturalidade;
	}
	public String getNmNaturalidade(){
		return this.nmNaturalidade;
	}
	public void setNmEscola(String nmEscola){
		this.nmEscola=nmEscola;
	}
	public String getNmEscola(){
		return this.nmEscola;
	}
	public void setNrNivelEscolar(int nrNivelEscolar){
		this.nrNivelEscolar=nrNivelEscolar;
	}
	public int getNrNivelEscolar(){
		return this.nrNivelEscolar;
	}
	public void setTpModalidadeEscolar(int tpModalidadeEscolar){
		this.tpModalidadeEscolar=tpModalidadeEscolar;
	}
	public int getTpModalidadeEscolar(){
		return this.tpModalidadeEscolar;
	}
	public void setTpHorarioEscolar(int tpHorarioEscolar){
		this.tpHorarioEscolar=tpHorarioEscolar;
	}
	public int getTpHorarioEscolar(){
		return this.tpHorarioEscolar;
	}
	public void setTpTurnoFamec(int tpTurnoFamec){
		this.tpTurnoFamec=tpTurnoFamec;
	}
	public int getTpTurnoFamec(){
		return this.tpTurnoFamec;
	}
	public void setStAluno(int stAluno){
		this.stAluno=stAluno;
	}
	public int getStAluno(){
		return this.stAluno;
	}
	public void setHrSaida(GregorianCalendar hrSaida){
		this.hrSaida=hrSaida;
	}
	public GregorianCalendar getHrSaida(){
		return this.hrSaida;
	}
	public void setLgAcompanhanteSaida(int lgAcompanhanteSaida){
		this.lgAcompanhanteSaida=lgAcompanhanteSaida;
	}
	public int getLgAcompanhanteSaida(){
		return this.lgAcompanhanteSaida;
	}
	public void setNmAcompanhanteSaida(String nmAcompanhanteSaida){
		this.nmAcompanhanteSaida=nmAcompanhanteSaida;
	}
	public String getNmAcompanhanteSaida(){
		return this.nmAcompanhanteSaida;
	}
	public void setLgAlmocoInstituicao(int lgAlmocoInstituicao){
		this.lgAlmocoInstituicao=lgAlmocoInstituicao;
	}
	public int getLgAlmocoInstituicao(){
		return this.lgAlmocoInstituicao;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdAluno: " +  getCdAluno();
		valueToString += ", cdFamilia: " +  getCdFamilia();
		valueToString += ", nmAluno: " +  getNmAluno();
		valueToString += ", dtNascimento: " +  sol.util.Util.formatDateTime(getDtNascimento(), "dd/MM/yyyy HH:mm:ss:SSS", "");
		valueToString += ", tpSexo: " +  getTpSexo();
		valueToString += ", nmNaturalidade: " +  getNmNaturalidade();
		valueToString += ", nmEscola: " +  getNmEscola();
		valueToString += ", nrNivelEscolar: " +  getNrNivelEscolar();
		valueToString += ", tpModalidadeEscolar: " +  getTpModalidadeEscolar();
		valueToString += ", tpHorarioEscolar: " +  getTpHorarioEscolar();
		valueToString += ", tpTurnoFamec: " +  getTpTurnoFamec();
		valueToString += ", stAluno: " +  getStAluno();
		valueToString += ", hrSaida: " +  sol.util.Util.formatDateTime(getHrSaida(), "dd/MM/yyyy HH:mm:ss:SSS", "");
		valueToString += ", lgAcompanhanteSaida: " +  getLgAcompanhanteSaida();
		valueToString += ", nmAcompanhanteSaida: " +  getNmAcompanhanteSaida();
		valueToString += ", lgAlmocoInstituicao: " +  getLgAlmocoInstituicao();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new Aluno(getCdAluno(),
			getCdFamilia(),
			getNmAluno(),
			getDtNascimento()==null ? null : (GregorianCalendar)getDtNascimento().clone(),
			getTpSexo(),
			getNmNaturalidade(),
			getNmEscola(),
			getNrNivelEscolar(),
			getTpModalidadeEscolar(),
			getTpHorarioEscolar(),
			getTpTurnoFamec(),
			getStAluno(),
			getHrSaida()==null ? null : (GregorianCalendar)getHrSaida().clone(),
			getLgAcompanhanteSaida(),
			getNmAcompanhanteSaida(),
			getLgAlmocoInstituicao());
	}

}