package br.org.mac.famec.model;

public class Usuario {

	private int cdUsuario;
	private String nmUsuario;
	private String nmLogin;
	private String nmSenha;
	private String nmEmail;
	private int stUsuario;
	private String nmFuncao;

	public Usuario(){ }

	public Usuario(int cdUsuario,
			String nmUsuario,
			String nmLogin,
			String nmSenha,
			String nmEmail,
			int stUsuario,
			String nmFuncao){
		setCdUsuario(cdUsuario);
		setNmUsuario(nmUsuario);
		setNmLogin(nmLogin);
		setNmSenha(nmSenha);
		setNmEmail(nmEmail);
		setStUsuario(stUsuario);
		setNmFuncao(nmFuncao);
	}
	public void setCdUsuario(int cdUsuario){
		this.cdUsuario=cdUsuario;
	}
	public int getCdUsuario(){
		return this.cdUsuario;
	}
	public void setNmUsuario(String nmUsuario){
		this.nmUsuario=nmUsuario;
	}
	public String getNmUsuario(){
		return this.nmUsuario;
	}
	public void setNmLogin(String nmLogin){
		this.nmLogin=nmLogin;
	}
	public String getNmLogin(){
		return this.nmLogin;
	}
	public void setNmSenha(String nmSenha){
		this.nmSenha=nmSenha;
	}
	public String getNmSenha(){
		return this.nmSenha;
	}
	public void setNmEmail(String nmEmail){
		this.nmEmail=nmEmail;
	}
	public String getNmEmail(){
		return this.nmEmail;
	}
	public void setStUsuario(int stUsuario){
		this.stUsuario=stUsuario;
	}
	public int getStUsuario(){
		return this.stUsuario;
	}
	public void setNmFuncao(String nmFuncao){
		this.nmFuncao=nmFuncao;
	}
	public String getNmFuncao(){
		return this.nmFuncao;
	}
	public String toString() {
		String valueToString = "";
		valueToString += "cdUsuario: " +  getCdUsuario();
		valueToString += ", nmUsuario: " +  getNmUsuario();
		valueToString += ", nmLogin: " +  getNmLogin();
		valueToString += ", nmSenha: " +  getNmSenha();
		valueToString += ", nmEmail: " +  getNmEmail();
		valueToString += ", stUsuario: " +  getStUsuario();
		valueToString += ", nmFuncao: " +  getNmFuncao();
		return "{" + valueToString + "}";
	}

	public Object clone() {
		return new Usuario(getCdUsuario(),
			getNmUsuario(),
			getNmLogin(),
			getNmSenha(),
			getNmEmail(),
			getStUsuario(),
			getNmFuncao());
	}

}
