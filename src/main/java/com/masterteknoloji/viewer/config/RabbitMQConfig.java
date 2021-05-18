package com.masterteknoloji.viewer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.masterteknoloji.viewer.service.rabbitmq.RabbitMQListner;

@Configuration
public class RabbitMQConfig {

	@Autowired
	RabbitMQListner rabbitMQListner;
	
	@Value("${javainuse.rabbitmq.shortlivequeue}")
	String shortLiveQueueName;

	@Value("${javainuse.rabbitmq.fanoutexchange}")
	String fanoutExchange;

	@Bean
	Queue shortLiveQueue() {
		return QueueBuilder.durable(shortLiveQueueName).autoDelete().withArgument("x-message-ttl", 5*1000).build();
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange(fanoutExchange);
	}

	@Bean
	Binding fanoutBindingForShortLice(Queue shortLiveQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(shortLiveQueue).to(fanoutExchange);
	}
	
	@Bean
	MessageListenerContainer messageListenerContainerShortLive(ConnectionFactory connectionFactory ) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
		simpleMessageListenerContainer.setQueues(shortLiveQueue());
		simpleMessageListenerContainer.setMessageListener(rabbitMQListner);
		return simpleMessageListenerContainer;

	}
//--------------------------------------------------------------------------------------------------------------
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}


	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
