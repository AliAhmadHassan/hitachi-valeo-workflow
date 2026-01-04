package br.com.valeoservice.model;

import java.sql.Date;


public class Grupo extends BaseModel {
    private static final long serialVersionUID = 1L;
    
	private Long idGrupo;
    
	private String nome;
    
    private boolean adm;
    
	private Date dtCriacao;
	
	private Date dtModificacao;
    
	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtModificacao() {
		return dtModificacao;
	}

	public void setDtModificacao(Date dtModificacao) {
		this.dtModificacao = dtModificacao;
	}

	public boolean getAdm() {
		return adm;
	}

	public void setAdm(boolean adm) {
		this.adm = adm;
	}

}
