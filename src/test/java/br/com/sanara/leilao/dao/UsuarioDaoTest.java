package br.com.sanara.leilao.dao;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import br.com.sanara.leilao.model.Usuario;
import br.com.sanara.leilao.util.JPAUTil;

class UsuarioDaoTest {

	private UsuarioDao dao;

	@Test
	void testBuscaUsuarioPeloUsername() {
		EntityManager em = JPAUTil.getEntityManager();
		this.dao = new UsuarioDao(em);

		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		//inicia a transação
		em.getTransaction().begin();
		//faz a persistencia do novo usuário
		em.persist(usuario);
		//faz o commit dos dados
		em.getTransaction().commit();

		Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);

	}

}
