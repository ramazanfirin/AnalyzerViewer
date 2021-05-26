package com.masterteknoloji.viewer.service.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterteknoloji.viewer.domain.dto.VideoRecordQueryVM;
import com.masterteknoloji.viewer.service.DataProcessManager;

@Component
public class RabbitMQListner implements MessageListener {

	@Autowired
	DataProcessManager dataProcessManager;

	@Autowired
	ObjectMapper objectMapper;
	
	public void onMessage(Message message) {
		String messageValue = new String(message.getBody());
		System.out.println("Short live Consuming Message - " + messageValue);
		objectMapper.findAndRegisterModules();
		VideoRecordQueryVM queryVM = null;
		try {
			queryVM = objectMapper.readValue(messageValue, VideoRecordQueryVM.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		dataProcessManager.lineCrossedByMQ(queryVM);
	}

	
}