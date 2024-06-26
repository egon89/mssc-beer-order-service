package com.egon.msscbeerorderservice.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class JmsContainerTest {

  @Container
  static GenericContainer<?> artemis = new GenericContainer<>(DockerImageName.parse("apache/activemq-artemis:latest-alpine"))
      .withEnv("ANONYMOUS_LOGIN", "true")
      .withExposedPorts(61616);

  @DynamicPropertySource
  static void artemisProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.artemis.broker-url", () -> "tcp://%s:%d".formatted(artemis.getHost(), artemis.getMappedPort(61616)));
  }

  @Autowired
  private JmsTemplate jmsTemplate;

  @Test
  void sendMessage() throws JMSException {
    jmsTemplate.convertAndSend("testQueue", "Hello, JMS!");

    Message message = jmsTemplate.receive("testQueue");

    assertThat(message).isInstanceOf(TextMessage.class);
    TextMessage textMessage = (TextMessage) message;
    assertThat(textMessage.getText()).isEqualTo("\"Hello, JMS!\"");
  }

}
