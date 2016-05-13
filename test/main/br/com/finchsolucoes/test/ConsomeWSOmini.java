package br.com.finchsolucoes.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import br.com.finchsolucoes.ominipage.services.OminiServiceDTO;

public class ConsomeWSOmini {

	private static String in = "/Dados/public/Download.jpg";
	
	private static String out = "/Dados/public/out.zip";
	
	
	public static void main(String[] args) throws Exception {
		byte[] arqBytes = null;
		Path path = Paths.get(in);
		try {
			arqBytes = Files.readAllBytes(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		String http = "http://192.168.11.87:8383/ocr/executaOCRSemDelete";
		
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);

		WebResource webResource = client.resource(http);
		
		OminiServiceDTO dto = new OminiServiceDTO();
		dto.setArquivoByte(arqBytes);
		dto.setExtensao(".jpg");
		Gson gson = new Gson();
		String json = gson.toJson(dto);

		ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,
				json);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = clientResponse.getEntity(InputStream.class);
	    byte[] buffer = new byte[is.available()];
		is.read(buffer);
	    
		File targetFile = new File(out);
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    
		//webResource.accept(MediaType.APPLICATION_JSON_TYPE).header("content-type", MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE).post(dto);
		
	}

	private static SecureRandom random = new SecureRandom();

	public static File createRandomFile(byte[] conteudo) throws IOException {
		FileInputStream fileInputStream = null;
		File file = File.createTempFile("finchDataType" + new BigInteger(130, random).toString(15), ".ser");
		// convert array of bytes into file
		FileOutputStream fileOuputStream = new FileOutputStream(file);
		fileOuputStream.write(conteudo);
		fileOuputStream.close();
		return file;
	}

}
