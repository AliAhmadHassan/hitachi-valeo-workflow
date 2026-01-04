package br.com.valeoservice.model;

public class HierarquiaItemItem {
	private Long id;
	
	private String descricao;
	
	private Long idGrupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public HierarquiaItemItem(Long id, String descricao, Long idGrupo) {
		this.id = id;
		this.descricao = descricao;
		this.idGrupo = idGrupo;
	}
	
	public HierarquiaItemItem() {}
}
