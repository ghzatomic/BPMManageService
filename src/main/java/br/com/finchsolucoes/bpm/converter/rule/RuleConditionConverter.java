package br.com.finchsolucoes.bpm.converter.rule;

import br.com.finchsolucoes.bpm.converter.rule.dto.RuleConditionDTO;

public interface RuleConditionConverter<T> {

	public T createRuleCondition(RuleConditionDTO dto);
	
}
