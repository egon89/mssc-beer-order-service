package com.egon.msscbeerorderservice;

import com.egon.msscbeerorderservice.services.GetBeerByIdService;
import com.egon.msscbeerorderservice.services.GetBeerByUpcService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MsscBeerOrderServiceApplicationTests {

	public static final String ACTIVEMQ_ARTEMIS_IMAGE = "apache/activemq-artemis:latest-alpine";
	public static final String BROKER_URL_PROPERTY = "spring.artemis.broker-url";
	public static final String BROKER_URL = "tcp://%s:%d";
	public static final String ANONYMOUS_LOGIN = "ANONYMOUS_LOGIN";


	@MockBean
	private GetBeerByUpcService getBeerByUpcService;

	@MockBean
	private GetBeerByIdService getBeerByIdService;

	@Test
	void contextLoads() {
	}

}
