package br.com.finchsolucoes.bpm.converter.rule.dto;

public class RuleConditionDTO {

	private Object pre;
	private String condition;
	private Object post;
	public Object getPre() {
		return pre;
	}
	public void setPre(Object pre) {
		this.pre = pre;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Object getPost() {
		return post;
	}
	public void setPost(Object post) {
		this.post = post;
	}

}
