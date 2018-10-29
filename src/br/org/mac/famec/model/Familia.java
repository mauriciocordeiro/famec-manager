package br.org.mac.famec.model;

import java.util.GregorianCalendar;

public class Familia {

	private int cdFamilia;
	private GregorianCalendar dtCadastro;
	private int cdUsuarioCadastro;

	public Familia(){ }

	public Familia(int cdFamilia,
			GregorianCalendar dtCadastro,
			int cdUsuarioCadastro){
		setCdFamilia(cdFamilia);
		setDtCadastro(dtCadastro);
		setCdUsuarioCadastro(cdUsuarioCadastro);
	}
	public void setCdFamilia(int cdFamilia){
		this.cdFamilia=cdFamilia;
	}
	public int getCdFamilia(){
		return this.cdFamilia;
	}
	public void setDtCadastro(GregorianCalendar dtCadastro){
		this.dtCadastro=dtCadastro;
	}
	public GregorianCalendar getDtCadastro(){
		return this.dtCadastro;
	}
	public void setCdUsuarioCadastro(int cdUsuarioCadastro){
		this.cdUsuarioCadastro=cdUsuarioCadastro;
	}
	public int getCdUsuarioCadastro(){
		return this.cdUsuarioCadastro;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdFamilia: " +  getCdFamilia();
		valueToString += ", dtCadastro: " +  sol.util.Util.formatDateTime(getDtCadastro(), "dd/MM/yyyy HH:mm:ss:SSS", "");
		valueToString += ", cdUsuarioCadastro: " +  getCdUsuarioCadastro();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new Familia(getCdFamilia(),
			getDtCadastro()==null ? null : (GregorianCalendar)getDtCadastro().clone(),
			getCdUsuarioCadastro());
	}

}
