package test.java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
@RunWith(ConcurrentTestRunner.class)

public class TestRestConcurrency {
    private final static int THREAD_COUNT = 5;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//just give a moment to a webservice :D
		Thread.sleep(10000);
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
    @ThreadCount(THREAD_COUNT)
	public void TestConcurrentRequest() throws IOException {
		String service="http://restfoo.eu-de.mybluemix.net/deployment";
		String xmlread=java.nio.file.Files.lines(Paths.get("./testfile/nfv3nodes3hostsSAT-MAIL.xml")).collect(Collectors.joining("\n"));
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
			.target(service)
			.request(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.post(Entity.entity(xmlread, MediaType.APPLICATION_XML));
		assertEquals(Status.OK.getStatusCode(),res.getStatus());
		assertTrue(res.readEntity(String.class).contains("isSat=\"true\""));
	}
	@Test
    @ThreadCount(THREAD_COUNT)
	public void TestConcurrentLog() {
		String service="http://restfoo.eu-de.mybluemix.net/log";
		javax.ws.rs.core.Response res=ClientBuilder.newClient()
				.target(service)
				.request(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML)
				.get();
			assertEquals(Status.OK.getStatusCode(),res.getStatus());
	}
	@Test
    @ThreadCount(THREAD_COUNT)
	public void TestConcurrentConverter() throws IOException {
		String service="http://restfoo.eu-de.mybluemix.net/converter";
		String xmlread=java.nio.file.Files.lines(Paths.get("./testfile/nfv5nodes7hostsSAT-WEBwithParsingString.xml")).collect(Collectors.joining("\n"));
		Response res = ClientBuilder.newClient()
				.target(service)
				.request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(Entity.entity(xmlread,MediaType.APPLICATION_XML));
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
		assertTrue(res.readEntity(String.class).contains("NodeRef"));
	}

}
