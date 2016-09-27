package br.com.finchsolucoes.bpmn;

import java.io.File;
import java.util.Collection;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.json.simple.JSONObject;

import br.com.finchsolucoes.bpm.converter.destination.DestinationJsonConverter;
import br.com.finchsolucoes.bpm.converter.route.RouteJsonConverter;
import br.com.finchsolucoes.bpm.runner.BPMRunner;
import br.com.finchsolucoes.bpm.runner.ElementCallback;
import br.com.finchsolucoes.bpm.runner.SequenceFlowCallback;

public class TesteBPM {

	public static void main(String[] args) {
		TesteBPM.teste1();
		
	}
	
	public static void teste1(){
		BpmnModelInstance modelInstance = Bpmn.readModelFromFile(new File("/Dados/public/BPMn2/diagram.bpmn"));
		
		Collection<Task> lista = modelInstance.getModelElementsByType(Task.class);
		Collection<SequenceFlow> colSequence = modelInstance.getModelElementsByType(SequenceFlow.class);
		
		BPMRunner<JSONObject> bpmRunner = new BPMRunner<>(new DestinationJsonConverter(), new RouteJsonConverter(),
			new ElementCallback<JSONObject>() {
					@Override
					public void encontrado(JSONObject dto) {
						System.out.println(dto.toJSONString());
					}
				},
			new SequenceFlowCallback<JSONObject>() {
					@Override
					public void encontrado(JSONObject dto) {
						System.out.println(dto.toJSONString());
					}
				}
		);
		
		bpmRunner.percorreElements(lista);
		 
	}
	
	public static void teste2(){
		BpmnModelInstance modelInstance = Bpmn.readModelFromFile(new File("/Dados/public/BPMn2/diagram.bpmn"));
		
		Collection<Task> lista = modelInstance.getModelElementsByType(Task.class);
		
		StartEvent startEvent = modelInstance.getModelElementsByType(StartEvent.class).iterator().next();
		
		
		
		for (SequenceFlow flow : startEvent.getOutgoing()) {
			if (flow.getTarget() instanceof Task){
				System.out.println(((Task)flow.getTarget()).getName());
			}
		}
		
		//SequenceFlow flow4 = modelInstance.getModelElementById("x==1");
		
		Collection<SequenceFlow> colSequence = modelInstance.getModelElementsByType(SequenceFlow.class);
		
		for (SequenceFlow sequenceFlow : colSequence) {
			System.out.println(sequenceFlow.getSource());
		}
		
		startEvent.getChildElementsByType(SequenceFlow.class);
		
	}
	
}
