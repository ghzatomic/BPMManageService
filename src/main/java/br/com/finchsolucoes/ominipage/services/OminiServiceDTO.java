package br.com.finchsolucoes.ominipage.services;

import javax.xml.bind.DatatypeConverter;

public class OminiServiceDTO {

	private String arquivo;
	private String extensao;
	public String getArquivo() {
		return arquivo;
	}
	
	public byte[] getArquivoByte(){
		return DatatypeConverter.parseBase64Binary(getArquivo());
	}
	
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	public String getExtensao() {
		return extensao;
	}
	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	
	
	
}
