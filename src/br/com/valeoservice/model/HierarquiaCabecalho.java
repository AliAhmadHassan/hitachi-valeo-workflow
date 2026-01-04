package br.com.valeoservice.model;

import java.sql.Date;
import java.util.Calendar;

public class HierarquiaCabecalho extends BaseModel {

	private static final long serialVersionUID = 4212819782417671437L;

	private Long idHierarquiaCabecalho;
    
    private Long idUsuario;

	private String nome;
    
	private String codSAP;
    
    private Date dtCriacao;
    
    private Date dtModificacao;
    
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

    public String getCodSAP() {
		return codSAP;
	}

	public void setCodSAP(String codSAP) {
		this.codSAP = codSAP;
	}
	
	public Long getIdHierarquiaCabecalho() {
		return idHierarquiaCabecalho;
	}

	public void setIdHierarquiaCabecalho(Long idHierarquiaCabecalho) {
		this.idHierarquiaCabecalho = idHierarquiaCabecalho;
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


}
