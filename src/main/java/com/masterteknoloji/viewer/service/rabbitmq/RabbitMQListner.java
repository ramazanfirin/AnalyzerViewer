package com.masterteknoloji.viewer.service.rabbitmq;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.domain.Line;
import com.masterteknoloji.viewer.service.CameraService;
import com.masterteknoloji.viewer.service.DataProcessManager;

@Component
public class RabbitMQListner implements MessageListener {

	@Autowired
	DataProcessManager dataProcessManager;
	
	public void onMessage(Message message) {
		String messageValue = new String(message.getBody());
		System.out.println("Short live Consuming Message - " + messageValue);
		dataProcessManager.lineCrossedByMQ(new Long(messageValue));
	}

	
}