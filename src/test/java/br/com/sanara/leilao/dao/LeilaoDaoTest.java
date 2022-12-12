package br.com.sanara.leilao.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.sanara.leilao.model.Leilao;
import br.com.sanara.leilao.model.Usuario;
import br.com.sanara.leilao.util.JPAUTil;

class LeilaoDaoTest {

	private LeilaoDao dao;

	private EntityManager em;

// recurso do junit para inicializar antes de cada um dos testes inicializar o em
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUTil.getEntityManager();
		this.dao = new LeilaoDao(em);
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
	void testDeveriaCadastrarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("mochila", new BigDecimal("70.00"), LocalDate.now(), usuario);
		leilao = dao.salvar(leilao);

//		verificando se o leilão foi persisitido através da busca dele
		Leilao salvo = dao.buscarPorId(leilao.getId());
		Assert.assertNotNull(salvo);
	}

	@Test
	void testDeveriaAtualizarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("mochila", new BigDecimal("70.00"), LocalDate.now(), usuario);
		leilao = dao.salvar(leilao);

		leilao.setNome("Celular");
		leilao.setValorInicial(new BigDecimal("400.00"));

		leilao = dao.salvar(leilao);
		Leilao salvo = dao.buscarPorId(leilao.getId());

		// verificar se as informasções foram atualizadas
		Assert.assertEquals("Celular", salvo.getNome());
		Assert.assertEquals(new BigDecimal("400.00"), salvo.getValorInicial());
	}

}
