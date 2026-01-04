package br.com.valeoservice.model.dao;

import java.util.List;

import br.com.valeoservice.model.HierarquiaItem;

public interface IHierarquiaItemDAO extends IBaseDAO<HierarquiaItem, Long> {
	List<HierarquiaItem> getConsultaItens(Long idHierarquiaCabecalho);
}
