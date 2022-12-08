package br.com.sanara.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sanara.leilao.model.Usuario;
import br.com.sanara.leilao.util.JPAUTil;

class UsuarioDaoTest {

	private UsuarioDao dao;

	private EntityManager em;

	// recurso do junit para inicializar antes de cada um dos testes inicializar o  em
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUTil.getEntityManager();
		this.dao = new UsuarioDao(em);
		// inicia a transação
		em.getTransaction().begin();
	}

	// Chama o método após a execução de cada teste
	@AfterEach
	public void afterEach() {
		// faz o rollback dos dados no banco zerando para o proximo teste
		this.em.getTransaction().rollback();
	}

	private Usuario criarUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		// faz a persistencia do novo usuário
		this.em.persist(usuario);
		return usuario;
	}

	@Test
	void testDeveriaEncontrarUsuarioCadastradoPeloUsername() {

		// faz o commit dos dados
		// em.getTransaction().commit();

		Usuario usuario = criarUsuario();
		Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);
	}

	@Test
	void testNaoDeveriaEncontrarUsuarioCadastradoPeloUsername() {
		criarUsuario();
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
	}

}
