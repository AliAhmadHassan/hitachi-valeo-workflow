package br.com.valeoservice.model.dao;

import java.util.List;

import br.com.valeoservice.model.BaseModel;

public interface IBaseDAO<T extends BaseModel, Y> {
	T getById(Y ID);
	
	List<T> getAll();

	T save(T type);

	T update(T type);

	void delete(T type);

	
}
