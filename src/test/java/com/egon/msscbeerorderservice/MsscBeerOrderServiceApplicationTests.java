package com.egon.msscbeerorderservice;

import com.egon.msscbeerorderservice.services.GetBeerByIdService;
import com.egon.msscbeerorderservice.services.GetBeerByUpcService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MsscBeerOrderServiceApplicationTests {

	@MockBean
	private GetBeerByUpcService getBeerByUpcService;

	@MockBean
	private GetBeerByIdService getBeerByIdService;

	@Test
	void contextLoads() {
	}

}
