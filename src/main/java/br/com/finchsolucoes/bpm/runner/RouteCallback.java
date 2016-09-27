package br.com.finchsolucoes.bpm.runner;

public abstract class RouteCallback<T> {

	public abstract void encontrado(T dto);
	
}
