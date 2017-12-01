package it.polito.verifoo.client;

import java.io.FileWriter;
import java.io.IOException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Main {

	public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("Specify Url and XML Filename as parameter!");
            return;
        }
		try {
			Response res=new Client(args[0]).request(args[1]);
			System.out.println(res.getStatusInfo());
			if(res.getStatusInfo().equals(Status.OK)){
				FileWriter fw=new FileWriter("result.xml");
				fw.write(res.readEntity(String.class));
				fw.close();
				System.out.println("Output written to result.xml");
			}else{
				System.err.println(res.readEntity(String.class));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
