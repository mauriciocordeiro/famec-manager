package br.org.mac.famec.model;

public class EnderecoResponsavel {

	private int cdEndereco;
	private int cdResponsavel;
	private String nmRua;
	private int nrCasa;
	private String nmComplemento;
	private String nmBairro;
	private String nmCidade;
	private String nmEstado;

	public EnderecoResponsavel(){ }

	public EnderecoResponsavel(int cdEndereco,
			int cdResponsavel,
			String nmRua,
			int nrCasa,
			String nmComplemento,
			String nmBairro,
			String nmCidade,
			String nmEstado){
		setCdEndereco(cdEndereco);
		setCdResponsavel(cdResponsavel);
		setNmRua(nmRua);
		setNrCasa(nrCasa);
		setNmComplemento(nmComplemento);
		setNmBairro(nmBairro);
		setNmCidade(nmCidade);
		setNmEstado(nmEstado);
	}
	public void setCdEndereco(int cdEndereco){
		this.cdEndereco=cdEndereco;
	}
	public int getCdEndereco(){
		return this.cdEndereco;
	}
	public void setCdResponsavel(int cdResponsavel){
		this.cdResponsavel=cdResponsavel;
	}
	public int getCdResponsavel(){
		return this.cdResponsavel;
	}
	public void setNmRua(String nmRua){
		this.nmRua=nmRua;
	}
	public String getNmRua(){
		return this.nmRua;
	}
	public void setNrCasa(int nrCasa){
		this.nrCasa=nrCasa;
	}
	public int getNrCasa(){
		return this.nrCasa;
	}
	public void setNmComplemento(String nmComplemento){
		this.nmComplemento=nmComplemento;
	}
	public String getNmComplemento(){
		return this.nmComplemento;
	}
	public void setNmBairro(String nmBairro){
		this.nmBairro=nmBairro;
	}
	public String getNmBairro(){
		return this.nmBairro;
	}
	public void setNmCidade(String nmCidade){
		this.nmCidade=nmCidade;
	}
	public String getNmCidade(){
		return this.nmCidade;
	}
	public void setNmEstado(String nmEstado){
		this.nmEstado=nmEstado;
	}
	public String getNmEstado(){
		return this.nmEstado;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdEndereco: " +  getCdEndereco();
		valueToString += ", cdResponsavel: " +  getCdResponsavel();
		valueToString += ", nmRua: " +  getNmRua();
		valueToString += ", nrCasa: " +  getNrCasa();
		valueToString += ", nmComplemento: " +  getNmComplemento();
		valueToString += ", nmBairro: " +  getNmBairro();
		valueToString += ", nmCidade: " +  getNmCidade();
		valueToString += ", nmEstado: " +  getNmEstado();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new EnderecoResponsavel(getCdEndereco(),
			getCdResponsavel(),
			getNmRua(),
			getNrCasa(),
			getNmComplemento(),
			getNmBairro(),
			getNmCidade(),
			getNmEstado());
	}

}
