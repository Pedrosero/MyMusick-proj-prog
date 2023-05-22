package model.DAO;

import java.util.List;

import model.domain.Usuario;

public interface IDAO<T> extends AutoCloseable {
	List<T> findAll() throws Exception;

	T findById(int id) throws Exception;

	T save(T entity) throws Exception;

	T delete(T entity)throws Exception;
}
