package br.com.finchsolucoes.bpm.converter.destination;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import br.com.finchsolucoes.bpm.converter.destination.dto.DestinationDTO;

public class DestinationJsonConverter implements DestinationConverter<JSONObject> {

	public JSONObject createDestination(DestinationDTO dto){
		if (dto == null){
			return null;
		}
		JSONObject ret = new JSONObject();
		
		ret.put("destinationID", dto.getId());
		
		if (dto.getIdsRotasEntrada() != null){
			JSONArray arr = new JSONArray();
			for (String rota : dto.getIdsRotasEntrada()) {
				arr.add(rota);
			}
			ret.put("routesIn", arr);
		}
		
		if (dto.getIdsRotasSaida() != null){
			JSONArray arr = new JSONArray();
			for (String rota : dto.getIdsRotasSaida()) {
				arr.add(rota);
			}
			ret.put("routesOut", arr);
		}
		
		return ret;
	}
	
	
}
