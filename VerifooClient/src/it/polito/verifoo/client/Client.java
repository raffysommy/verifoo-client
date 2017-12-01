package it.polito.verifoo.client;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Client {

	private String resturl;

	public Client(String resturl) {
		this.resturl=resturl;
	}
	public Response request(String filename) throws IOException{
		String xmlread=java.nio.file.Files.lines(Paths.get(filename)).collect(Collectors.joining("\n"));
		return  ClientBuilder.newClient()
				.target(this.resturl)
				.request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(Entity.entity(xmlread,MediaType.APPLICATION_XML));
	}

}
