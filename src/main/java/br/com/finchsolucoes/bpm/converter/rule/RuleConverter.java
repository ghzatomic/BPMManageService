package br.com.finchsolucoes.bpm.converter.rule;

import br.com.finchsolucoes.bpm.converter.rule.dto.RuleDTO;

public interface RuleConverter<T> {

	public T createRule(RuleDTO dto);
	
}
