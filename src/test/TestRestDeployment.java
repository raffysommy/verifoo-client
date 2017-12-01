/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Raffaele
 *
 */
public class TestRestDeployment {
	private static String service="http://restfoo.eu-de.mybluemix.net/rest";


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestInvalidXMLRequest() {
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.post(Entity.entity("thisisnoreallyanxmlfile",MediaType.APPLICATION_XML));
		assertEquals(Status.BAD_REQUEST.getStatusCode(),res.getStatus());
		assertTrue(res.readEntity(String.class).contains("[org.xml.sax.SAXParseException: Content is not allowed in prolog.]"));
	}
	@Test
	public void TestValidVoidXMLRequest() {
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.post(Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",MediaType.APPLICATION_XML));
		assertEquals(Status.BAD_REQUEST.getStatusCode(),res.getStatus());
		assertTrue(res.readEntity(String.class).contains("[org.xml.sax.SAXParseException: Premature end of file.]"));
	}
	@Test
	public void TestInvalidGetRequest() {
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.get();
		assertEquals(Status.METHOD_NOT_ALLOWED.getStatusCode(),res.getStatus());
	}
	@Test
	public void TestInvalidPutRequest() {
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.put(Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",MediaType.APPLICATION_XML));
		assertEquals(Status.METHOD_NOT_ALLOWED.getStatusCode(),res.getStatus());
	}
	@Test
	public void TestInvalidDeleteRequest() {
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.delete();
		assertEquals(Status.METHOD_NOT_ALLOWED.getStatusCode(),res.getStatus());
	}
	@Test
	public void TestValidRequest() throws IOException {
		String xmlread=java.nio.file.Files.lines(Paths.get("./testfile/nfv3nodes3hostsSAT-MAIL.xml")).collect(Collectors.joining("\n"));
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.post(Entity.entity(xmlread, MediaType.APPLICATION_XML));
		assertEquals(Status.OK.getStatusCode(),res.getStatus());
		assertTrue(res.readEntity(String.class).contains("isSat=\"true\""));
	}
}
