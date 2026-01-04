package br.com.valeoservice.model;

import java.sql.Date;

public class PedidoAprovacao extends BaseModel {

	private static final long serialVersionUID = 6392921395348152882L;

    private Long idPedidoAprovacao;
	
	private Long idPedido;
	
	private Long idHierarquiaItem;
	
	private Long idUsuario;
	
	private Boolean aprovado;

	private Boolean eMailEnviado;

	private Date dtAprovacao;

	public Long getIdPedidoAprovacao() {
		return idPedidoAprovacao;
	}

	public void setIdPedidoAprovacao(Long idPedidoAprovacao) {
		this.idPedidoAprovacao = idPedidoAprovacao;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdHierarquiaItem() {
		return idHierarquiaItem;
	}

	public void setIdHierarquiaItem(Long idHierarquiaItem) {
		this.idHierarquiaItem = idHierarquiaItem;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Boolean geteMailEnviado() {
		return eMailEnviado;
	}

	public void seteMailEnviado(Boolean eMailEnviado) {
		this.eMailEnviado = eMailEnviado;
	}

	public Date getDtAprovacao() {
		return dtAprovacao;
	}

	public void setDtAprovacao(Date dtAprovacao) {
		this.dtAprovacao = dtAprovacao;
	}

}
