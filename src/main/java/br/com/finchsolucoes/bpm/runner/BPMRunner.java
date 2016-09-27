package br.com.finchsolucoes.bpm.runner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.Task;

import br.com.finchsolucoes.bpm.converter.destination.DestinationConverter;
import br.com.finchsolucoes.bpm.converter.destination.dto.DestinationDTO;
import br.com.finchsolucoes.bpm.converter.route.RouteConverter;
import br.com.finchsolucoes.bpm.converter.route.dto.RouteDTO;


public class BPMRunner<T> {

	private DestinationConverter<T> destinationConverter;
	private RouteConverter<T> routerConverter;
	
	private ElementCallback<T> callbackElement;
	private SequenceFlowCallback<T> callbackSequence;
	
	public BPMRunner(
			DestinationConverter<T> destinationConverter, 
			RouteConverter<T> routerConverter,
			ElementCallback<T> callbackElement,
			SequenceFlowCallback<T> callbackSequence) {
		this.destinationConverter = destinationConverter;
		this.routerConverter = routerConverter;
		this.callbackElement = callbackElement;
		this.callbackSequence = callbackSequence;
	}
	
	public void percorreElements(Collection<Task> nodes){
		if (nodes != null && !nodes.isEmpty()){
			for (Task flowNode : nodes) {
				DestinationDTO dto = new DestinationDTO();
				dto.setId(flowNode.getId());
				System.out.println(flowNode.getName());
				dto.setNome(flowNode.getName());
				dto.setIdsRotasSaida(extraiIdsSequenciasDeSaidas(flowNode.getOutgoing()));
				dto.setIdsRotasEntrada(extraiIdsSequenciasDeEntradas(flowNode.getIncoming()));
				callbackElement.encontrado(destinationConverter.createDestination(dto));
			}
		}
	}
	
	private List<String> extraiIdsSequenciasDeSaidas(Collection<SequenceFlow> saidas) {
		if (saidas != null && !saidas.isEmpty()){
			List<String> saidasList = new ArrayList<>();
			for (SequenceFlow sequenceFlow : saidas) {
				mapeaSequenceSaida(sequenceFlow,new RouteCallback<String>() {
					public void encontrado(String id) {
						saidasList.add(id);
					}
				});
			}
			return saidasList;
		}
		return null;
	}
	
	private List<String> extraiIdsSequenciasDeEntradas(Collection<SequenceFlow> entradas) {
		if (entradas != null && !entradas.isEmpty()){
			List<String> entradasList = new ArrayList<>();
			for (SequenceFlow sequenceFlow : entradas) {
				sourceDecider(sequenceFlow, new RouteCallback<String>() {
					public void encontrado(String dto) {
						entradasList.add(dto);
					}
				});
			}
			return entradasList;
		}
		return null;
	}
	
	public void sourceDecider(SequenceFlow sequenceFlow,RouteCallback<String> callbackRoute){
		if (sequenceFlow == null){
			return ;
		}
		FlowNode fn = sequenceFlow.getSource();
		if (fn instanceof Task){
			callbackRoute.encontrado(sequenceFlow.getId());
		}else if (fn instanceof ExclusiveGateway){
			
		}
	}
	
	private void mapeaSequenceSaida(SequenceFlow sequenceFlow,RouteCallback<String> callbackRoute) {
		targetDecider(sequenceFlow.getId(), sequenceFlow.getSource(), sequenceFlow.getTarget(),callbackRoute);
	}

	public void targetDecider(String idFlowInicial,FlowNode source,FlowNode node,RouteCallback<String> callbackRoute){
		if (node != null){
			if (node instanceof ExclusiveGateway){
				gatewayMapper(idFlowInicial,(ExclusiveGateway)node,source,callbackRoute);
			}else if (node instanceof Task){
				taskMapper(idFlowInicial,(Task) node,source,callbackRoute);
			}
		}
	}
	
	public void taskMapper(String idFlow,Task task,FlowNode source,RouteCallback<String> callbackRoute){
		RouteDTO rota = new RouteDTO();
		rota.setRouteID(UUID.randomUUID().toString());
		rota.setBegin(source.getId());
		rota.setEnd(task.getId());
		callbackRoute.encontrado(rota.getRouteID());
		callbackSequence.encontrado(routerConverter.createRoute(rota));
	}
	
	public void gatewayMapper(String idFlowInicial,ExclusiveGateway gw,FlowNode source,RouteCallback<String> callbackRoute){
		if (gw != null){
			Collection<SequenceFlow> listaDeSaida = gw.getOutgoing();
			if (!listaDeSaida.isEmpty()){
				for (SequenceFlow sequenceFlow : listaDeSaida) {
					SequenceFlow sfGwIn = gw.getIncoming().iterator().next();
					targetDecider(sequenceFlow.getId(), sfGwIn.getSource(), sequenceFlow.getTarget(),callbackRoute);
				}
			}
		}
	}
	
	/*public void percorreSequenceFlow(Collection<SequenceFlow> flows){
		if (flows != null && !flows.isEmpty()){
			for (SequenceFlow sequenceFlow : flows) {
				FlowNode fnTarget = sequenceFlow.getTarget();
				FlowNode fnSource = sequenceFlow.getSource();
				if (fnTarget != null){
				}
				
			}
		}
	}*/
	
	
}
