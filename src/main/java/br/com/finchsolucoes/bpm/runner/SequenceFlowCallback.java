package br.com.finchsolucoes.bpm.runner;

import br.com.finchsolucoes.bpm.converter.route.dto.RouteDTO;

public abstract class SequenceFlowCallback<T> {

	public abstract void encontrado(T dto);
	
}

