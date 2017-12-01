package it.polito.verifoo.rest.test;

import static org.junit.Assert.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRestLog {
	private static String service="http://restfoo.eu-de.mybluemix.net/log";


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
				.target(service)
				.request(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML)
				.get();
			assertEquals(Status.OK.getStatusCode(),res.getStatus());
	}

}
