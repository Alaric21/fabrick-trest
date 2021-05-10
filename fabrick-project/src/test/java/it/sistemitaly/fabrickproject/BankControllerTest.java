package it.sistemitaly.fabrickproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.sistemitaly.fabrickproject.controller.BankController;
import it.sistemitaly.fabrickproject.model.BankAccountListResponse;
import it.sistemitaly.fabrickproject.model.Payload;
import it.sistemitaly.fabrickproject.model.Transaction;
import it.sistemitaly.fabrickproject.model.Type;
import it.sistemitaly.fabrickproject.service.BankService;

@WebMvcTest(BankController.class)
class BankControllerTest {
	
	
	@Mock
    private RestTemplate restTemplate;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private BankService bankService;
		
	@Test
	void getBankAccountBalance() throws Exception {
		String balance = "Balance: 122.7";
		Mockito.when(bankService.getBankAccountBalance("14537780"))
        .thenReturn(ResponseEntity.status(HttpStatus.OK).body(balance));
		
		String url = "/accounts/14537780/balance";
		
		MvcResult mvcResult =mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		
		String actualjsonResponse = mvcResult.getResponse().getContentAsString();
		System.out.println(actualjsonResponse);
		
        Assert.assertEquals(balance, actualjsonResponse);                                                        
		
	}

	
	
	  @Test
	  void getAccountTransactions() throws Exception { 
		  
		  BankAccountListResponse bankAccountlistResponse = new BankAccountListResponse();
		  		  
          List<Transaction> transactions = new ArrayList<>();
          Transaction t1 = new Transaction();
          
          t1.setTransactionId("1329140278001");
          t1.setOperationId("19000017094683");
          t1.setAccountingDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2019-02-01T00:00:00.000+00:00"));
          t1.setValueDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2019-02-01T00:00:00.000+00:00"));
         
          Type type1  = new Type();
          type1.setEnumeration("GBS_TRANSACTION_TYPE");
          type1.setValue("GBS_ACCOUNT_TRANSACTION_TYPE_0034");
          
          t1.setType(type1);
          t1.setAmount(89.00);
          t1.setCurrency("EUR");
          t1.setDescription("GC LUCA TERRIBILE        DA 03268.22300         DATA ORDINE 01022019 COPERTURA SPESE");
          
          Transaction t2 = new Transaction();
          t2.setTransactionId("398894");
          t2.setOperationId("00000000398894");
          t2.setAccountingDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2019-01-31T00:00:00.000+00:00"));
          t2.setValueDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2019-02-01T00:00:00.000+00:00"));
          
          Type type2 = new Type();
          type2.setEnumeration("GBS_TRANSACTION_TYPE");
          type2.setValue("GBS_ACCOUNT_TRANSACTION_TYPE_0050");
          
          t2.setType(type2);
          t2.setAmount(-95.73);
          t2.setCurrency("EUR");
          t2.setDescription("PD VISA CORPORATE 12");
          
          
          transactions.add(t1);
          transactions.add(t2);
          
          
		  Payload payload =  new Payload();
		  payload.setList(transactions);
		  
		  
		  bankAccountlistResponse.setPayload(payload);
		  bankAccountlistResponse.setStatus("OK");
		  bankAccountlistResponse.setErrors(null);
		  
		  Map<String ,String> param = new HashMap<String, String>(); 
		  param.put("fromAccountingDate", "2019-01-01");
		  param.put("toAccountingDate", "2019-02-01");

		  Mockito.when(bankService.getAccountTransactions("14537780", param))
	        .thenReturn(ResponseEntity.status(HttpStatus.OK).body(bankAccountlistResponse));
			
			String url = "/accounts/14537780/transactions";
			
			MvcResult mvcResult = mockMvc.perform(get(url).param("fromAccountingDate", "2019-01-01").param("toAccountingDate", "2019-02-01")).andExpect(status().isOk()).andReturn();
		  
			String actualjsonResponse = mvcResult.getResponse().getContentAsString();
			System.out.println(actualjsonResponse);
			String bankAccountlistResponseString = mapper.writeValueAsString(bankAccountlistResponse);
	        Assert.assertEquals(bankAccountlistResponseString, actualjsonResponse); 
	  }
	     
	 
}
