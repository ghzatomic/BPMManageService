package br.com.finchsolucoes.bpm.converter.route;

import br.com.finchsolucoes.bpm.converter.route.dto.RouteDTO;

public interface RouteConverter<T> {

	public T createRoute(RouteDTO dto);
	
}
