package br.com.finchsolucoes.bpm.converter.route;

import org.json.simple.JSONObject;

import br.com.finchsolucoes.bpm.converter.route.dto.RouteDTO;

public class RouteJsonConverter implements RouteConverter<JSONObject> {

	public JSONObject createRoute(RouteDTO dto){
		if (dto == null){
			return null;
		}
		JSONObject ret = new JSONObject();
		
		ret.put("routeID", dto.getRouteID());
		ret.put("rule", dto.getRule());
		ret.put("begin", dto.getBegin());
		ret.put("end", dto.getEnd());
		
		return ret;
	}
	
}
