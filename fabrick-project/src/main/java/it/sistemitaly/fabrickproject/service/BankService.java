package it.sistemitaly.fabrickproject.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.sistemitaly.fabrickproject.model.BankAccountListResponse;
import it.sistemitaly.fabrickproject.model.BankAccountTransactionRequest;
import it.sistemitaly.fabrickproject.model.BankAccountTransactionResponse;

@Service
public interface BankService {
	public ResponseEntity<String> getBankAccountBalance( String id);
	
	public ResponseEntity<BankAccountTransactionResponse> doBankAccountTransaction(BankAccountTransactionRequest accountTransaction, String id);
	
	public ResponseEntity<BankAccountListResponse> getAccountTransactions(String id,  Map<String,String> requestParams);


}
