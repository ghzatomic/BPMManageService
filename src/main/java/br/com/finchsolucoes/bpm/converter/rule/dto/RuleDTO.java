package br.com.finchsolucoes.bpm.converter.rule.dto;

public class RuleDTO {

	private String ruleId;
	private RuleConditionDTO rule;
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public RuleConditionDTO getRule() {
		return rule;
	}
	public void setRule(RuleConditionDTO rule) {
		this.rule = rule;
	}
	
}
