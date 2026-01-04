package br.com.valeoservice.model.dao;

import java.util.List;

import br.com.valeoservice.model.*;

public interface IPedidoDAO extends IBaseDAO<Pedido, Long> {
	List<Pedido> getEnviarSAP();
	List<Pedido> getParaAprovar(Long idGrupo);
}
