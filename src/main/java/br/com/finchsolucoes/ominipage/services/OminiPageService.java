package br.com.finchsolucoes.ominipage.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.finchsolucoes.ominipage.omini.Constantes;
import br.com.finchsolucoes.ominipage.omini.JobRunnerOmniPage;
import br.com.finchsolucoes.ominipage.util.FileUtils;
import br.com.finchsolucoes.ominipage.util.ZipUtils;

@RestController
@RequestMapping("/omini")
public class OminiPageService {

	@RequestMapping(value = "/executaOCRMultipart", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces={MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public HttpEntity<byte[]> executaOCRMultipart(@RequestParam("fileBytes") MultipartFile arquivo) throws IOException {
		byte[] bFileZip = processa(arquivo.getBytes());
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    header.set("Content-Disposition",
	                   "attachment; filename=" + System.currentTimeMillis());
	    header.setContentLength(bFileZip.length);
	    
	    //tempZipFile.delete();
	    

	    return new HttpEntity<byte[]>(bFileZip, header);
	    
	}
	
	@RequestMapping(value = "/executaOCR", method ={RequestMethod.POST,RequestMethod.GET}, consumes = {
			MediaType.APPLICATION_OCTET_STREAM_VALUE }, produces={MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public @ResponseBody byte[] executaOCR( @RequestBody byte[] arquivo) throws IOException {
	    return processa(arquivo);
	}

	private byte[] processa(byte[] arqBytes) throws IOException, FileNotFoundException {
		int tryLimit = 10000;
		long nomeArquivoSistema = System.currentTimeMillis();
		
		File temp = File.createTempFile(String.valueOf(nomeArquivoSistema), ".pdf");
		
		FileOutputStream fileOuputStream = new FileOutputStream(temp);
		try {
			fileOuputStream.write(arqBytes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			fileOuputStream.close();
		}

		//System.out.println("Movendo file "+Constantes.PATH_INPUT.replace("c:", Constantes.SERVER) + nomeArquivoSistema + ".pdf");
		
		temp.renameTo(new File(Constantes.PATH_INPUT.replace("c:", Constantes.SERVER) + nomeArquivoSistema + ".pdf"));
		
		JobRunnerOmniPage runJob = new JobRunnerOmniPage();
		runJob.runJob(nomeArquivoSistema);
		
		File file = new File(Constantes.PATH_INPUT.replace("c:", Constantes.SERVER) + nomeArquivoSistema + ".pdf");
		int tryPos = 0;
		while (file.exists()) {
			try {
				file = new File(Constantes.PATH_INPUT.replace("c:", Constantes.SERVER) + nomeArquivoSistema + ".pdf");
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (tryPos > tryLimit){
				break;
			}
			tryPos++;
		}

		deleteTempFiles();
		//System.out.println("Terminou ... vai zipar .. ");
		
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.startsWith(String.valueOf(nomeArquivoSistema))) {
					return true;
				} else {
					return false;
				}
			}
		};
		
		List<String> listFileAbsolutePathZip = new ArrayList<>();
		List<File> listFileZip = new ArrayList<>();
		File listFileDir = null;
		
		File pastaOut = new File(Constantes.PATH_OUTPUT);
		//System.out.println("Verificando : "+pastaOut.getAbsolutePath());
		if (pastaOut.exists()){
			for (File file2 : pastaOut.listFiles(textFilter)) {
				if (file2.isDirectory()){
					for (File file2Sub : file2.listFiles()) {
						listFileAbsolutePathZip.add(file2Sub.getAbsolutePath());
						listFileZip.add(file2Sub);
					}
					listFileDir = file2;
				}else{
					listFileAbsolutePathZip.add(file2.getAbsolutePath());
					listFileZip.add(file2);
				}
				
			}
		}
		File tempZipFile = null;
		if (listFileZip.size() != 0){
			tempZipFile=File.createTempFile(String.valueOf(nomeArquivoSistema), ".zip");
			//System.out.println(tempZipFile.getAbsolutePath());
			ZipUtils.zipFilesString(listFileZip, tempZipFile);
			for (File file2 : listFileZip) {
				//System.out.println("Deletando : "+file2.getAbsolutePath());
				if (file2.isDirectory()){
					FileUtils.deleteDirectory(file2);
				}else{
					file2.delete();
				}
			}
		}
		listFileDir.delete();
		byte[] bFileZip = new byte[(int) tempZipFile.length()];
		FileInputStream fileInputStream = new FileInputStream(tempZipFile);
	    fileInputStream.read(bFileZip);
	    fileInputStream.close();
		return bFileZip;
	}

	/**
	 * 
	 * M�todo respons�vel por remover os arquivos de configura��o usados pelo
	 * OmniPage.
	 *
	 * @since 25 de set de 2015 15:51:12
	 * @author Gustavo de Camargo <gustavocamargo@finchsolucoes.com.br>
	 *
	 */
	@SuppressWarnings("unused")
	private static void deleteTempFiles() {
		File folder = new File(Constantes.PATH_JOB.replace("c:", Constantes.SERVER));
		File folders[] = folder.listFiles();

		for (int i = 0; i < folders.length; i++) {
			File folderCorrente = folders[i];
			String name = folderCorrente.getName();

			if (name.endsWith(".jpf") || name.endsWith(".xwf") || name.endsWith(".dat")) {
				if (name.length() > 13) {
					boolean success = new File(folderCorrente.toString()).delete();

					if (success == false) {
						System.err.println("<<<N�o foi possivel deletar o arquivo:" + name.toString() + ">>>");
					}
				}
			}
		}
	}

}
