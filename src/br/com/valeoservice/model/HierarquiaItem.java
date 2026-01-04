package br.com.valeoservice.model;

public class HierarquiaItem extends BaseModel {

	private static final long serialVersionUID = 8560244099209023946L;
	
    private Long idHierarquiaItem;
	
	private Long idHierarquiaCabecalho;
	
	private Long idGrupo;
	
	private Long idHierarquiaItemFilho;

	public Long getIdHierarquiaItem() {
		return idHierarquiaItem;
	}

	public void setIdHierarquiaItem(Long idHierarquiaItem) {
		this.idHierarquiaItem = idHierarquiaItem;
	}

	public Long getIdHierarquiaCabecalho() {
		return idHierarquiaCabecalho;
	}

	public void setIdHierarquiaCabecalho(Long idHierarquiaCabecalho) {
		this.idHierarquiaCabecalho = idHierarquiaCabecalho;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Long getIdHierarquiaItemFilho() {
		return idHierarquiaItemFilho;
	}

	public void setIdHierarquiaItemFilho(Long idHierarquiaItemFilho) {
		this.idHierarquiaItemFilho = idHierarquiaItemFilho;
	}
}
