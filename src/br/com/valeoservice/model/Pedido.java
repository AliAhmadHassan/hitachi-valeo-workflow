package br.com.valeoservice.model;

import java.sql.Date;

public class Pedido extends BaseModel {

	private static final long serialVersionUID = -5108814708994314194L;

    private Long idPedido;
	
	private Long idHierarquiaCabecalho;
	
	private String material;
	
	private String descricaoMaterial;
	
	private Integer qtdVendas;
	
	private Double valorVendas;
	
	private Double precoMedio;

	private Double precoAtual;

	private String volume;
	
	private Double desconto;
	
	private Date periodoDe;
	
	private Date periodoAte;
	
	private Float grossMargin;
	
	private Float percDCOS;
	
	private Float percBonus;
	
	private Float percFees;

	private Float percExtra;

	private Double operatingMarginValor;

	private Float operatingMarginPerc;
	
	private Boolean aprovado;

	private Boolean enviadoSAP;
	
	private Boolean hierarquiaFinalizada;
	
	private Long idSAP;
	
	private String motivo;

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdHierarquiaCabecalho() {
		return idHierarquiaCabecalho;
	}

	public void setIdHierarquiaCabecalho(Long idHierarquiaCabecalho) {
		this.idHierarquiaCabecalho = idHierarquiaCabecalho;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDescricaoMaterial() {
		return descricaoMaterial;
	}

	public void setDescricaoMaterial(String descricaoMaterial) {
		this.descricaoMaterial = descricaoMaterial;
	}

	public Integer getQtdVendas() {
		return qtdVendas;
	}

	public void setQtdVendas(Integer qtdVendas) {
		this.qtdVendas = qtdVendas;
	}

	public Double getValorVendas() {
		return valorVendas;
	}

	public void setValorVendas(Double valorVendas) {
		this.valorVendas = valorVendas;
	}

	public Double getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}

	public Double getPrecoAtual() {
		return precoAtual;
	}

	public void setPrecoAtual(Double precoAtual) {
		this.precoAtual = precoAtual;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Date getPeriodoDe() {
		return periodoDe;
	}

	public void setPeriodoDe(Date periodoDe) {
		this.periodoDe = periodoDe;
	}

	public Date getPeriodoAte() {
		return periodoAte;
	}

	public void setPeriodoAte(Date periodoAte) {
		this.periodoAte = periodoAte;
	}

	public Float getGrossMargin() {
		return grossMargin;
	}

	public void setGrossMargin(Float grossMargin) {
		this.grossMargin = grossMargin;
	}

	public Float getPercDCOS() {
		return percDCOS;
	}

	public void setPercDCOS(Float percDCOS) {
		this.percDCOS = percDCOS;
	}

	public Float getPercBonus() {
		return percBonus;
	}

	public void setPercBonus(Float percBonus) {
		this.percBonus = percBonus;
	}

	public Float getPercFees() {
		return percFees;
	}

	public void setPercFees(Float percFees) {
		this.percFees = percFees;
	}

	public Float getPercExtra() {
		return percExtra;
	}

	public void setPercExtra(Float percExtra) {
		this.percExtra = percExtra;
	}

	public Double getOperatingMarginValor() {
		return operatingMarginValor;
	}

	public void setOperatingMarginValor(Double operatingMarginValor) {
		this.operatingMarginValor = operatingMarginValor;
	}

	public Float getOperatingMarginPerc() {
		return operatingMarginPerc;
	}

	public void setOperatingMarginPerc(Float operatingMarginPerc) {
		this.operatingMarginPerc = operatingMarginPerc;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Boolean getEnviadoSAP() {
		return enviadoSAP;
	}

	public void setEnviadoSAP(Boolean enviadoSAP) {
		this.enviadoSAP = enviadoSAP;
	}

	public Boolean getHierarquiaFinalizada() {
		return hierarquiaFinalizada;
	}

	public void setHierarquiaFinalizada(Boolean hierarquiaFinalizada) {
		this.hierarquiaFinalizada = hierarquiaFinalizada;
	}

	public Long getIdSAP() {
		return idSAP;
	}

	public void setIdSAP(Long idSAP) {
		this.idSAP = idSAP;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
