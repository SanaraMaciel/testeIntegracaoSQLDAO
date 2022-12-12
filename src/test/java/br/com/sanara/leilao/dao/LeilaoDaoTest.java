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
import br.com.sanara.leilao.util.LeilaoBuilder;
import br.com.sanara.leilao.util.UsuarioBuilder;

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

	@Test
	void testDeveriaCadastrarUmLeilao() {

		// criando um usuário com o padrão builder
		Usuario usuario = new UsuarioBuilder().comNome("fulano").comEmail("fulano@email.com").comSenha("12345678")
				.criar();
		this.em.persist(usuario);

		Leilao leilao = new LeilaoBuilder().comNome("Mochila").comValorInicial("500").comData(LocalDate.now())
				.comUsuario(usuario).criar();

		leilao = dao.salvar(leilao);

//		verificando se o leilão foi persisitido através da busca dele
		Leilao salvo = dao.buscarPorId(leilao.getId());
		Assert.assertNotNull(salvo);
	}

	@Test
	void testDeveriaAtualizarUmLeilao() {

		// criando um usuário com o padrão builder
		Usuario usuario = new UsuarioBuilder().comNome("fulano").comEmail("fulano@email.com").comSenha("12345678")
				.criar();
		this.em.persist(usuario);

		Leilao leilao = new LeilaoBuilder().comNome("Mochila").comValorInicial("500").comData(LocalDate.now())
				.comUsuario(usuario).criar();

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
