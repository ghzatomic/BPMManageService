package br.com.finchsolucoes.bpm.converter.rule;

import org.json.simple.JSONObject;

import br.com.finchsolucoes.bpm.converter.rule.dto.RuleConditionDTO;
import br.com.finchsolucoes.bpm.converter.rule.dto.RuleDTO;

public class RuleJsonConverter implements RuleConverter<JSONObject>,RuleConditionConverter<JSONObject> {

	public JSONObject createRule(RuleDTO dto){
		if (dto == null){
			return null;
		}
		JSONObject ret = new JSONObject();
		
		ret.put("ruleID", dto.getRuleId());
		ret.put("rule", createRuleCondition(dto.getRule()));
		
		return ret;
	}

	public JSONObject createRuleCondition(RuleConditionDTO dto) {
		if (dto == null){
			return null;
		}
		JSONObject ret = new JSONObject();
		if (dto.getPre() != null){
			JSONObject first = new JSONObject();
			if (dto.getPre() instanceof String){
				first.put("field", dto.getPre());
			}else if (dto.getPre() instanceof RuleDTO){
				RuleDTO ruleSub = (RuleDTO) dto.getPre();
				first.put("link", ruleSub.getRuleId());
			}
			ret.put("first", first);
		}
		ret.put("operator", dto.getCondition());
		if (dto.getPost() != null){
			JSONObject second = new JSONObject();
			if (dto.getPost() instanceof String){
				second.put("field", dto.getPost());
			}else if (dto.getPost() instanceof RuleDTO){
				RuleDTO ruleSub = (RuleDTO) dto.getPost();
				second.put("link", ruleSub.getRuleId());
			}
			ret.put("second", second);
		}
		
		return ret;
	}
	
	
	
}
