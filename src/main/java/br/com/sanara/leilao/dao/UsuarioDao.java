package br.com.sanara.leilao.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sanara.leilao.model.Usuario;

@Repository
public class UsuarioDao {

//	@PersistenceContext anotação usada para criar o contexto de persistencia no spring
	private EntityManager em;

	//utilizar o construtor para poder utilizar a persistência na classe de teste
	//preferir injeção de dependências via construtor, para facilitar na escrita dos testes automatizados;
	@Autowired
	public UsuarioDao(EntityManager em) {
		this.em = em;
	}

	public Usuario buscarPorUsername(String username) {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :username", Usuario.class)
				.setParameter("username", username).getSingleResult();
	}

	public void deletar(Usuario usuario) {
		em.remove(usuario);
	}

}
