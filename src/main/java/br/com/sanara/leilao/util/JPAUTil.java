package br.com.sanara.leilao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUTil {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tests");

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
