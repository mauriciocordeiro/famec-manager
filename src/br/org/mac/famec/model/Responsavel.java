package br.org.mac.famec.model;

import java.util.GregorianCalendar;

public class Responsavel {

	private int cdResponsavel;
	private int cdFamilia;
	private String nmResponsavel;
	private int tpParentesco;
	private int tpGenero;
	private GregorianCalendar dtNascimento;
	private String nmNaturalidade;
	private int tpEstadoCivil;
	private String nrTelefone1;
	private String nrTelefone2;
	private String nrRg;
	private String nmOrgaoExpedidorRg;
	private String sgUfRg;
	private String nrCpf;
	private String dsEscolaridade;
	private int lgEstudante;
	private int tpNivelEscolar;
	private int tpTurno;
	private String nmOcupacao;
	private Double vlRendaMensal;
	private String nmLocalTrabalho;
	private String nrTelefoneTrabalho;

	public Responsavel(){ }

	public Responsavel(int cdResponsavel,
			int cdFamilia,
			String nmResponsavel,
			int tpParentesco,
			int tpGenero,
			GregorianCalendar dtNascimento,
			String nmNaturalidade,
			int tpEstadoCivil,
			String nrTelefone1,
			String nrTelefone2,
			String nrRg,
			String nmOrgaoExpedidorRg,
			String sgUfRg,
			String nrCpf,
			String dsEscolaridade,
			int lgEstudante,
			int tpNivelEscolar,
			int tpTurno,
			String nmOcupacao,
			Double vlRendaMensal,
			String nmLocalTrabalho,
			String nrTelefoneTrabalho){
		setCdResponsavel(cdResponsavel);
		setCdFamilia(cdFamilia);
		setNmResponsavel(nmResponsavel);
		setTpParentesco(tpParentesco);
		setTpGenero(tpGenero);
		setDtNascimento(dtNascimento);
		setNmNaturalidade(nmNaturalidade);
		setTpEstadoCivil(tpEstadoCivil);
		setNrTelefone1(nrTelefone1);
		setNrTelefone2(nrTelefone2);
		setNrRg(nrRg);
		setNmOrgaoExpedidorRg(nmOrgaoExpedidorRg);
		setSgUfRg(sgUfRg);
		setNrCpf(nrCpf);
		setDsEscolaridade(dsEscolaridade);
		setLgEstudante(lgEstudante);
		setTpNivelEscolar(tpNivelEscolar);
		setTpTurno(tpTurno);
		setNmOcupacao(nmOcupacao);
		setVlRendaMensal(vlRendaMensal);
		setNmLocalTrabalho(nmLocalTrabalho);
		setNrTelefoneTrabalho(nrTelefoneTrabalho);
	}
	public void setCdResponsavel(int cdResponsavel){
		this.cdResponsavel=cdResponsavel;
	}
	public int getCdResponsavel(){
		return this.cdResponsavel;
	}
	public void setCdFamilia(int cdFamilia){
		this.cdFamilia=cdFamilia;
	}
	public int getCdFamilia(){
		return this.cdFamilia;
	}
	public void setNmResponsavel(String nmResponsavel){
		this.nmResponsavel=nmResponsavel;
	}
	public String getNmResponsavel(){
		return this.nmResponsavel;
	}
	public void setTpParentesco(int tpParentesco){
		this.tpParentesco=tpParentesco;
	}
	public int getTpParentesco(){
		return this.tpParentesco;
	}
	public void setTpGenero(int tpGenero){
		this.tpGenero=tpGenero;
	}
	public int getTpGenero(){
		return this.tpGenero;
	}
	public void setDtNascimento(GregorianCalendar dtNascimento){
		this.dtNascimento=dtNascimento;
	}
	public GregorianCalendar getDtNascimento(){
		return this.dtNascimento;
	}
	public void setNmNaturalidade(String nmNaturalidade){
		this.nmNaturalidade=nmNaturalidade;
	}
	public String getNmNaturalidade(){
		return this.nmNaturalidade;
	}
	public void setTpEstadoCivil(int tpEstadoCivil){
		this.tpEstadoCivil=tpEstadoCivil;
	}
	public int getTpEstadoCivil(){
		return this.tpEstadoCivil;
	}
	public void setNrTelefone1(String nrTelefone1){
		this.nrTelefone1=nrTelefone1;
	}
	public String getNrTelefone1(){
		return this.nrTelefone1;
	}
	public void setNrTelefone2(String nrTelefone2){
		this.nrTelefone2=nrTelefone2;
	}
	public String getNrTelefone2(){
		return this.nrTelefone2;
	}
	public void setNrRg(String nrRg){
		this.nrRg=nrRg;
	}
	public String getNrRg(){
		return this.nrRg;
	}
	public void setNmOrgaoExpedidorRg(String nmOrgaoExpedidorRg){
		this.nmOrgaoExpedidorRg=nmOrgaoExpedidorRg;
	}
	public String getNmOrgaoExpedidorRg(){
		return this.nmOrgaoExpedidorRg;
	}
	public void setSgUfRg(String sgUfRg){
		this.sgUfRg=sgUfRg;
	}
	public String getSgUfRg(){
		return this.sgUfRg;
	}
	public void setNrCpf(String nrCpf){
		this.nrCpf=nrCpf;
	}
	public String getNrCpf(){
		return this.nrCpf;
	}
	public void setDsEscolaridade(String dsEscolaridade){
		this.dsEscolaridade=dsEscolaridade;
	}
	public String getDsEscolaridade(){
		return this.dsEscolaridade;
	}
	public void setLgEstudante(int lgEstudante){
		this.lgEstudante=lgEstudante;
	}
	public int getLgEstudante(){
		return this.lgEstudante;
	}
	public void setTpNivelEscolar(int tpNivelEscolar){
		this.tpNivelEscolar=tpNivelEscolar;
	}
	public int getTpNivelEscolar(){
		return this.tpNivelEscolar;
	}
	public void setTpTurno(int tpTurno){
		this.tpTurno=tpTurno;
	}
	public int getTpTurno(){
		return this.tpTurno;
	}
	public void setNmOcupacao(String nmOcupacao){
		this.nmOcupacao=nmOcupacao;
	}
	public String getNmOcupacao(){
		return this.nmOcupacao;
	}
	public void setVlRendaMensal(Double vlRendaMensal){
		this.vlRendaMensal=vlRendaMensal;
	}
	public Double getVlRendaMensal(){
		return this.vlRendaMensal;
	}
	public void setNmLocalTrabalho(String nmLocalTrabalho){
		this.nmLocalTrabalho=nmLocalTrabalho;
	}
	public String getNmLocalTrabalho(){
		return this.nmLocalTrabalho;
	}
	public void setNrTelefoneTrabalho(String nrTelefoneTrabalho){
		this.nrTelefoneTrabalho=nrTelefoneTrabalho;
	}
	public String getNrTelefoneTrabalho(){
		return this.nrTelefoneTrabalho;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdResponsavel: " +  getCdResponsavel();
		valueToString += ", cdFamilia: " +  getCdFamilia();
		valueToString += ", nmResponsavel: " +  getNmResponsavel();
		valueToString += ", tpParentesco: " +  getTpParentesco();
		valueToString += ", tpGenero: " +  getTpGenero();
		valueToString += ", dtNascimento: " +  sol.util.Util.formatDateTime(getDtNascimento(), "dd/MM/yyyy HH:mm:ss:SSS", "");
		valueToString += ", nmNaturalidade: " +  getNmNaturalidade();
		valueToString += ", tpEstadoCivil: " +  getTpEstadoCivil();
		valueToString += ", nrTelefone1: " +  getNrTelefone1();
		valueToString += ", nrTelefone2: " +  getNrTelefone2();
		valueToString += ", nrRg: " +  getNrRg();
		valueToString += ", nmOrgaoExpedidorRg: " +  getNmOrgaoExpedidorRg();
		valueToString += ", sgUfRg: " +  getSgUfRg();
		valueToString += ", nrCpf: " +  getNrCpf();
		valueToString += ", dsEscolaridade: " +  getDsEscolaridade();
		valueToString += ", lgEstudante: " +  getLgEstudante();
		valueToString += ", tpNivelEscolar: " +  getTpNivelEscolar();
		valueToString += ", tpTurno: " +  getTpTurno();
		valueToString += ", nmOcupacao: " +  getNmOcupacao();
		valueToString += ", vlRendaMensal: " +  getVlRendaMensal();
		valueToString += ", nmLocalTrabalho: " +  getNmLocalTrabalho();
		valueToString += ", nrTelefoneTrabalho: " +  getNrTelefoneTrabalho();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new Responsavel(getCdResponsavel(),
			getCdFamilia(),
			getNmResponsavel(),
			getTpParentesco(),
			getTpGenero(),
			getDtNascimento()==null ? null : (GregorianCalendar)getDtNascimento().clone(),
			getNmNaturalidade(),
			getTpEstadoCivil(),
			getNrTelefone1(),
			getNrTelefone2(),
			getNrRg(),
			getNmOrgaoExpedidorRg(),
			getSgUfRg(),
			getNrCpf(),
			getDsEscolaridade(),
			getLgEstudante(),
			getTpNivelEscolar(),
			getTpTurno(),
			getNmOcupacao(),
			getVlRendaMensal(),
			getNmLocalTrabalho(),
			getNrTelefoneTrabalho());
	}

}
