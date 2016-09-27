package br.com.finchsolucoes.bpm.converter.destination;

import br.com.finchsolucoes.bpm.converter.destination.dto.DestinationDTO;

public interface DestinationConverter<T> {

	public T createDestination(DestinationDTO dto);

}
