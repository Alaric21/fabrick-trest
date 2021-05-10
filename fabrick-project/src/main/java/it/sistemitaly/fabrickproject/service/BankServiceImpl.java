package it.sistemitaly.fabrickproject.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.sistemitaly.fabrickproject.model.BankAccountListResponse;
import it.sistemitaly.fabrickproject.model.BankAccountTransactionRequest;
import it.sistemitaly.fabrickproject.model.BankAccountTransactionResponse;
import it.sistemitaly.fabrickproject.model.BankAccoutBalanceResponse;
import  it.sistemitaly.fabrickproject.model.Error;

@Service
public class BankServiceImpl implements BankService {
	
	
	@Value("${BaseUrl}")
	private String baseUrl;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Utility utility;

	@Override
	public ResponseEntity<String> getBankAccountBalance(String id) {
		ResponseEntity<BankAccoutBalanceResponse> responseEntity = null;

		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/balance")   
			    .build(id);		
		
		try {						
		    responseEntity = restTemplate.exchange(targetUrl.toString(), HttpMethod.GET, new HttpEntity<String>(utility.getHttpHeaders()), BankAccoutBalanceResponse.class);
		} catch (HttpStatusCodeException e) {
			return utility.generateResponseEntity(e.getStatusCode(), e.getMessage());
		}
		catch (RestClientException e) {
			return utility.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Balance: " + responseEntity.getBody().getPayload().getBalance());
	}

	@Override
	public ResponseEntity<BankAccountTransactionResponse> doBankAccountTransaction(BankAccountTransactionRequest accountTransaction, String id) {

		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers")   
			    .build(id);	
		
		HttpEntity<String> request = new HttpEntity<String>(accountTransaction.toString(), utility.getHttpHeaders());
			    
		BankAccountTransactionResponse response = restTemplate.postForObject(targetUrl.toString(), request, BankAccountTransactionResponse.class);
		//boolean transferDone = bankService.doBankAccountTransaction(accountTransaction);
		List<Error> errors = new ArrayList<>();
		Error error = new Error();
		error.setCode("API000");
		error.setDescription("Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780");
		errors.add(error);
		
		response.setErrors(errors);
	   
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<BankAccountListResponse> getAccountTransactions(String id, Map<String, String> requestParams) {
		ResponseEntity<BankAccountListResponse> responseEntity = null;
		URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl)
			    .path("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
			    .queryParam("fromAccountingDate", requestParams.get("fromAccountingDate"))
			    .queryParam("toAccountingDate", requestParams.get("toAccountingDate"))
			    .build(id);				
		try {						
			responseEntity = restTemplate.exchange(targetUrl.toString(), HttpMethod.GET, new HttpEntity<String>(utility.getHttpHeaders()), BankAccountListResponse.class);
		} catch (HttpStatusCodeException e) {
			return utility.generateResponseEntity(e.getStatusCode(), e.getMessage());
		}
		catch (RestClientException e) {
			return utility.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
	}

	

}
