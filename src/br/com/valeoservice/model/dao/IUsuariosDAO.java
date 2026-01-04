package br.com.valeoservice.model.dao;

import br.com.valeoservice.model.Usuario;

public interface IUsuariosDAO extends IBaseDAO<Usuario, Long> {
	
	Usuario getConsultaLogin(String email, String password);
}
