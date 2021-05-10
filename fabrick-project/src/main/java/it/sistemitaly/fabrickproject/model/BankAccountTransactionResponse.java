package it.sistemitaly.fabrickproject.model;

import java.util.List;

public class BankAccountTransactionResponse {
	
	private String status;
    private List<Error> errors;
    private Payload payload;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
    
    
}
