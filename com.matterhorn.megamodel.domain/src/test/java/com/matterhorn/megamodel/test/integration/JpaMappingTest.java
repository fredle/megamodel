package com.matterhorn.megamodel.test.integration;

import static java.lang.String.format;
import static javax.persistence.Persistence.createEntityManagerFactory;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

@Ignore
public class JpaMappingTest {

	private static final String PU_NAME = "MimMegaModelPu";

	private static EntityManagerFactory factory;
	private EntityManager manager;


	@BeforeClass
	public static void intialise() throws Exception
	{
		// Mock JNDI
//		InitialContextFactory icf = new InitialContextFactory();
//		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, icf.getClass().getName());
		
		factory = createEntityManagerFactory(PU_NAME);
	}


	@AfterClass
	public static void destroy()
	{
		if (factory != null) {
			factory.close();
		}
	}


	@Before
	public void setUp()
	{
		manager = factory.createEntityManager();
	}


	@After
	public void tearDown()
	{
		if (manager != null) {
			manager.close();
		}
	}


	@Test
	public void testJpaMappings()
	{
		Set<EntityType<?>> entityTypes = factory.getMetamodel().getEntities();
		for (EntityType<?> entityType : entityTypes) {
			Class<?> entity = entityType.getJavaType();

			System.out.print("Testing " + entity.getCanonicalName() + "...");
			Query query = manager.createQuery(format("SELECT e FROM %s e", entity.getCanonicalName()));
			List<?> instructions = query.setMaxResults(1).getResultList();

			assertNotNull(instructions);
			System.out.println("Ok.");
		}
	}
	
	/**
	 * Creates a mock {@link Context} which returns our test datasource
	 * 
	 * @author alex
	 */
	public static final class MockInitialContextFactory implements InitialContextFactory {
		private Context context;


		@Override
		public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException
		{
			if (context == null) {
				// Construct DataSource
				PGSimpleDataSource ds = new PGSimpleDataSource();
				ds.setServerName("10.39.216.10");
				ds.setPortNumber(5432);
				ds.setDatabaseName("megamodel");
				ds.setUser("postgres");
				ds.setPassword("");

				// Mock Context
				context = mock(Context.class);
				when(context.lookup(any(Name.class))).thenReturn(ds);
			}

			return context;
		}
	}
}
