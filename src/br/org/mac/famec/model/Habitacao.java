package br.org.mac.famec.model;

public class Habitacao {

	private int cdHabitacao;
	private int cdFamilia;
	private int tpSituacao;
	private Double vlAluguel;
	private int nrComodos;
	private int tpAbastecimento;
	private int tpTratamentoAgua;
	private int tpIluminacao;
	private int tpEscoamentoSanitario;
	private int tpDestinoLixo;

	public Habitacao(){ }

	public Habitacao(int cdHabitacao,
			int cdFamilia,
			int tpSituacao,
			Double vlAluguel,
			int nrComodos,
			int tpAbastecimento,
			int tpTratamentoAgua,
			int tpIluminacao,
			int tpEscoamentoSanitario,
			int tpDestinoLixo){
		setCdHabitacao(cdHabitacao);
		setCdFamilia(cdFamilia);
		setTpSituacao(tpSituacao);
		setVlAluguel(vlAluguel);
		setNrComodos(nrComodos);
		setTpAbastecimento(tpAbastecimento);
		setTpTratamentoAgua(tpTratamentoAgua);
		setTpIluminacao(tpIluminacao);
		setTpEscoamentoSanitario(tpEscoamentoSanitario);
		setTpDestinoLixo(tpDestinoLixo);
	}
	public void setCdHabitacao(int cdHabitacao){
		this.cdHabitacao=cdHabitacao;
	}
	public int getCdHabitacao(){
		return this.cdHabitacao;
	}
	public void setCdFamilia(int cdFamilia){
		this.cdFamilia=cdFamilia;
	}
	public int getCdFamilia(){
		return this.cdFamilia;
	}
	public void setTpSituacao(int tpSituacao){
		this.tpSituacao=tpSituacao;
	}
	public int getTpSituacao(){
		return this.tpSituacao;
	}
	public void setVlAluguel(Double vlAluguel){
		this.vlAluguel=vlAluguel;
	}
	public Double getVlAluguel(){
		return this.vlAluguel;
	}
	public void setNrComodos(int nrComodos){
		this.nrComodos=nrComodos;
	}
	public int getNrComodos(){
		return this.nrComodos;
	}
	public void setTpAbastecimento(int tpAbastecimento){
		this.tpAbastecimento=tpAbastecimento;
	}
	public int getTpAbastecimento(){
		return this.tpAbastecimento;
	}
	public void setTpTratamentoAgua(int tpTratamentoAgua){
		this.tpTratamentoAgua=tpTratamentoAgua;
	}
	public int getTpTratamentoAgua(){
		return this.tpTratamentoAgua;
	}
	public void setTpIluminacao(int tpIluminacao){
		this.tpIluminacao=tpIluminacao;
	}
	public int getTpIluminacao(){
		return this.tpIluminacao;
	}
	public void setTpEscoamentoSanitario(int tpEscoamentoSanitario){
		this.tpEscoamentoSanitario=tpEscoamentoSanitario;
	}
	public int getTpEscoamentoSanitario(){
		return this.tpEscoamentoSanitario;
	}
	public void setTpDestinoLixo(int tpDestinoLixo){
		this.tpDestinoLixo=tpDestinoLixo;
	}
	public int getTpDestinoLixo(){
		return this.tpDestinoLixo;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdHabitacao: " +  getCdHabitacao();
		valueToString += ", cdFamilia: " +  getCdFamilia();
		valueToString += ", tpSituacao: " +  getTpSituacao();
		valueToString += ", vlAluguel: " +  getVlAluguel();
		valueToString += ", nrComodos: " +  getNrComodos();
		valueToString += ", tpAbastecimento: " +  getTpAbastecimento();
		valueToString += ", tpTratamentoAgua: " +  getTpTratamentoAgua();
		valueToString += ", tpIluminacao: " +  getTpIluminacao();
		valueToString += ", tpEscoamentoSanitario: " +  getTpEscoamentoSanitario();
		valueToString += ", tpDestinoLixo: " +  getTpDestinoLixo();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new Habitacao(getCdHabitacao(),
			getCdFamilia(),
			getTpSituacao(),
			getVlAluguel(),
			getNrComodos(),
			getTpAbastecimento(),
			getTpTratamentoAgua(),
			getTpIluminacao(),
			getTpEscoamentoSanitario(),
			getTpDestinoLixo());
	}

}
