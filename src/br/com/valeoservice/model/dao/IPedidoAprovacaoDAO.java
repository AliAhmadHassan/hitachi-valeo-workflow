package br.com.valeoservice.model.dao;

import java.util.List;

import br.com.valeoservice.model.*;

public interface IPedidoAprovacaoDAO extends IBaseDAO<PedidoAprovacao, Long>{
	PedidoAprovacao getPedidoAprovacao(Long idPedido, Long idHierarquiaItem);
}
