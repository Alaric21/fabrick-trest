package it.sistemitaly.fabrickproject.model;

public class BankAccoutBalanceResponse {
	

	private String status;
	private Error[] errors;
	private BankAccountBalanceRequest payload;
	
	public BankAccoutBalanceResponse() {
		super();
	}

	public BankAccoutBalanceResponse(String status, Error[] errors, BankAccountBalanceRequest payload) {
		super();
		this.status = status;
		this.errors = errors;
		this.payload = payload;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Error[] getErrors() {
		return errors;
	}
	public void setErrors(Error[] errors) {
		this.errors = errors;
	}
	public BankAccountBalanceRequest getPayload() {
		return payload;
	}
	public void setPayload(BankAccountBalanceRequest payload) {
		this.payload = payload;
	}
}