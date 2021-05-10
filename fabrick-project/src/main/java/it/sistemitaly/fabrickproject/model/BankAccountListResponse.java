package it.sistemitaly.fabrickproject.model;

public class BankAccountListResponse {
	private String status;
	private Error[] errors;
	private Payload payload;
	
	
	public BankAccountListResponse() {
		super();
	}
	
	public BankAccountListResponse(String status, Error[] errors, Payload payload) {
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
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	
}
