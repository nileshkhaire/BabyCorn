package com.fervort.babycorn.xml.validator;

public class ValidationResult {
	
	private boolean isValid= false;
	private String fieldName;
	private Object ifInvalidValue;
	private Object ifValidValue;
	private String ifInvalidMessage;
	private String ifValidMessage;
	public enum SEVERITY { CONTINUE , STOP}
	private SEVERITY severity;

	public ValidationResult(boolean isValid)
	{
		this.isValid= isValid;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public boolean isValid()
	{
		return this.isValid;
	}
	
	public Object getIfValidValue() {
		return ifValidValue;
	}

	public Object getIfInvalidValue() {
		return ifInvalidValue;
	}

	public void setIfInvalidValue(Object ifInvalidValue) {
		this.ifInvalidValue = ifInvalidValue;
	}

	public void setIfValidValue(Object ifValidValue) {
		this.ifValidValue = ifValidValue;
	}
	
	public String getIfInvalidMessage() {
		return ifInvalidMessage;
	}

	public void setIfInvalidMessage(String ifInvalidMessage) {
		this.ifInvalidMessage = ifInvalidMessage;
	}

	public String getIfValidMessage() {
		return ifValidMessage;
	}

	public void setIfValidMessage(String ifValidMessage) {
		this.ifValidMessage = ifValidMessage;
	}
	
	public SEVERITY getSeverity() {
		return severity;
	}

	public void setSeverity(SEVERITY severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "ValidationResult [isValid=" + isValid + ", ifInvalidValue=" + ifInvalidValue + ", ifValidValue="
				+ ifValidValue + ", ifInvalidMessage=" + ifInvalidMessage + ", ifValidMessage=" + ifValidMessage
				+ ", severity=" + severity + "]";
	}
	
}