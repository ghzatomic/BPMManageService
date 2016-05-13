package br.com.finchsolucoes.ominipage.services;

import java.io.Serializable;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

public class OminiServiceDTO implements Serializable{

	private String arquivo;
	private String extensao;
	public String getArquivo() {
		return arquivo;
	}
	
	public byte[] getArquivoByte(){
		return Base64.decodeBase64(getArquivo());
	}
	
	public void setArquivoByte(byte[] arquivo){
		setArquivo(Base64.encodeBase64String(arquivo));
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
