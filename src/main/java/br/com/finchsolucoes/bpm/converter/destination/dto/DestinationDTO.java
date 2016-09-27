package br.com.finchsolucoes.bpm.converter.destination.dto;

import java.util.List;

public class DestinationDTO {

	private String id;
	private String nome;
	
	private List<String> idsRotasSaida;
	private List<String> idsRotasEntrada;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<String> getIdsRotasSaida() {
		return idsRotasSaida;
	}
	public void setIdsRotasSaida(List<String> idsRotasSaida) {
		this.idsRotasSaida = idsRotasSaida;
	}
	public List<String> getIdsRotasEntrada() {
		return idsRotasEntrada;
	}
	public void setIdsRotasEntrada(List<String> idsRotasEntrada) {
		this.idsRotasEntrada = idsRotasEntrada;
	}
	
}
