package com.masterteknoloji.viewer.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.masterteknoloji.viewer.service.CameraService;
import com.masterteknoloji.viewer.service.DataProcessManager;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	CameraService cameraService;

	
	@Autowired
	DataProcessManager dataProcessManager;
	
	
	@Value("${javainuse.rabbitmq.fanoutexchange}")
	private String exchange;
	
	public void send(Long lineId) {
		amqpTemplate.convertAndSend(exchange,"", lineId);
		System.out.println("Send msg = " + lineId);
//		System.out.println("cameraService sender "+ cameraService);
//		System.out.println("dataProcessManager sender"+ dataProcessManager);
	    
	}
}