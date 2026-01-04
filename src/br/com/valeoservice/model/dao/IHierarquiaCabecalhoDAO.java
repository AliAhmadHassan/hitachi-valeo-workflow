package br.com.valeoservice.model.dao;

import br.com.valeoservice.model.*;

public interface IHierarquiaCabecalhoDAO extends IBaseDAO<HierarquiaCabecalho, Long> {
	HierarquiaCabecalho getByCodSAP(String codSAP);
}
