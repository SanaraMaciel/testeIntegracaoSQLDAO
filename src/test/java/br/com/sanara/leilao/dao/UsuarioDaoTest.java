package br.com.sanara.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import br.com.sanara.leilao.model.Usuario;
import br.com.sanara.leilao.util.JPAUTil;

class UsuarioDaoTest {

	private UsuarioDao dao;

	@Test
	void testDeveriaEncontrarUsuarioCadastradoPeloUsername() {
		EntityManager em = JPAUTil.getEntityManager();
		this.dao = new UsuarioDao(em);

		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		// inicia a transação
		em.getTransaction().begin();
		// faz a persistencia do novo usuário
		em.persist(usuario);
		// faz o commit dos dados
		em.getTransaction().commit();

		Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);

	}

	
	@Test
	void testNaoDeveriaEncontrarUsuarioCadastradoPeloUsername() {
		EntityManager em = JPAUTil.getEntityManager();
		this.dao = new UsuarioDao(em);

		Usuario usuario = new Usuario("ciclano", "ciclano@email.com", "12345678");
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();

		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));

	}

}
