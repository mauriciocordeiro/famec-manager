package br.org.mac.famec.model;

public class PerfilSocial {

	private int cdPerfilSocial;
	private int cdFamilia;
	private int lgNis;
	private String nrNis;
	private int lgBeneficio;
	private String nmBeneficio;
	private Double vlBeneficio;

	public PerfilSocial(){ }

	public PerfilSocial(int cdPerfilSocial,
			int cdFamilia,
			int lgNis,
			String nrNis,
			int lgBeneficio,
			String nmBeneficio,
			Double vlBeneficio){
		setCdPerfilSocial(cdPerfilSocial);
		setCdFamilia(cdFamilia);
		setLgNis(lgNis);
		setNrNis(nrNis);
		setLgBeneficio(lgBeneficio);
		setNmBeneficio(nmBeneficio);
		setVlBeneficio(vlBeneficio);
	}
	public void setCdPerfilSocial(int cdPerfilSocial){
		this.cdPerfilSocial=cdPerfilSocial;
	}
	public int getCdPerfilSocial(){
		return this.cdPerfilSocial;
	}
	public void setCdFamilia(int cdFamilia){
		this.cdFamilia=cdFamilia;
	}
	public int getCdFamilia(){
		return this.cdFamilia;
	}
	public void setLgNis(int lgNis){
		this.lgNis=lgNis;
	}
	public int getLgNis(){
		return this.lgNis;
	}
	public void setNrNis(String nrNis){
		this.nrNis=nrNis;
	}
	public String getNrNis(){
		return this.nrNis;
	}
	public void setLgBeneficio(int lgBeneficio){
		this.lgBeneficio=lgBeneficio;
	}
	public int getLgBeneficio(){
		return this.lgBeneficio;
	}
	public void setNmBeneficio(String nmBeneficio){
		this.nmBeneficio=nmBeneficio;
	}
	public String getNmBeneficio(){
		return this.nmBeneficio;
	}
	public void setVlBeneficio(Double vlBeneficio){
		this.vlBeneficio=vlBeneficio;
	}
	public Double getVlBeneficio(){
		return this.vlBeneficio;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdPerfilSocial: " +  getCdPerfilSocial();
		valueToString += ", cdFamilia: " +  getCdFamilia();
		valueToString += ", lgNis: " +  getLgNis();
		valueToString += ", nrNis: " +  getNrNis();
		valueToString += ", lgBeneficio: " +  getLgBeneficio();
		valueToString += ", nmBeneficio: " +  getNmBeneficio();
		valueToString += ", vlBeneficio: " +  getVlBeneficio();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new PerfilSocial(getCdPerfilSocial(),
			getCdFamilia(),
			getLgNis(),
			getNrNis(),
			getLgBeneficio(),
			getNmBeneficio(),
			getVlBeneficio());
	}

}
