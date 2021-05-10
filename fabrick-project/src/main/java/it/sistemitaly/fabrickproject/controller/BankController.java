package it.sistemitaly.fabrickproject.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.sistemitaly.fabrickproject.model.BankAccountListResponse;
import it.sistemitaly.fabrickproject.model.BankAccountTransactionRequest;
import it.sistemitaly.fabrickproject.model.BankAccountTransactionResponse;
import it.sistemitaly.fabrickproject.service.BankService;

@RestController
public class BankController {
	
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

	
	@Autowired
	private BankService bankService;


	@Value("${BaseUrl}")
	private String baseUrl;	
		
	
	@GetMapping("/accounts/{id}/balance")
	public ResponseEntity<String> getBankAccountBalance(@PathVariable String id) {
		logger.info("the getBankAccountBalance is running..");
		return bankService.getBankAccountBalance(id);
	}

	@RequestMapping(value="/accounts/{id}/payments/money-transfers",
	method = RequestMethod.POST,
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BankAccountTransactionResponse> doBankAccountTransaction(@RequestBody BankAccountTransactionRequest accountTransaction,@PathVariable String id) {
		logger.info("the doBankAccountTransaction is running..");

		return bankService.doBankAccountTransaction(accountTransaction, id);
	}	
	
	
	@GetMapping("/accounts/{id}/transactions")
	public ResponseEntity<BankAccountListResponse> getAccountTransactions(@PathVariable String id, @RequestParam Map<String,String> requestParams) {
		logger.info("the getAccountTransactions is running..");
		return bankService.getAccountTransactions(id, requestParams);
	}
	

}
