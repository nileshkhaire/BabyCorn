package com.fervort.babycorn.xml.validator;

/**
 * This class is used to build ValidationResult from inside validation method.
 *
 * <p>Example Usage: How to build ValidationResult inside validation method.
 * 
 * <pre>
 * 	public ValidationResult validateEmployeeID(Field field, Object object)
 *	{
 *		Double bonusPercentageFromXML = (Double)object;
 *		int empID = bonusPercentageFromXML.intValue(); 
 *		ValidationResult result = new ValidationResult(true);
 *		if(empID&lt;100)
 *		{
 *			result = new ValidationResult(false);
 *			result.setIfInvalidMessage(empID+" is not a valid");
 *			int ifInvalidValue=  00;
 *			result.setIfInvalidValue(ifInvalidValue);
 *		}
 *		return result;
 *	}
 * </pre>
 * 
 * <p>Check documentation of individual methods to know how to use.
 * 
 * @author Nilesh Khaire
 *
 */
public class ValidationResult {
	
	private boolean isValid= false;
	private String fieldName;
	private Object ifInvalidValue;
	private Object ifValidValue;
	private String ifInvalidMessage;
	private String ifValidMessage;
	public enum SEVERITY { CONTINUE , STOP}
	private SEVERITY severity;

	/**
	 * Set true if field has valid value else false.
	 * @param isValid True if result is valid else False.
	 */
	public ValidationResult(boolean isValid)
	{
		this.isValid= isValid;
	}
	
	/**
	 * Get name of the field
	 * @return name of the field
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Set name of the field
	 * @param fieldName name of the field
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**
	 * Check if field has valid value or not
	 * @return true if field has valid value.
	 */
	public boolean isValid()
	{
		return this.isValid;
	}
	
	/**
	 * Get value which is set if field has valid value.
	 * @return set Value
	 */
	public Object getIfValidValue() {
		return ifValidValue;
	}

	/**
	 * Get value which is set if field has invalid value.
	 * @return set Value
	 */
	public Object getIfInvalidValue() {
		return ifInvalidValue;
	}

	/**
	 * Set value if field has invalid value. 
	 * @param ifInvalidValue value to set
	 */
	public void setIfInvalidValue(Object ifInvalidValue) {
		this.ifInvalidValue = ifInvalidValue;
	}

	/**
	 * Set value if field has valid value.
	 * @param ifValidValue value to set.
	 */
	public void setIfValidValue(Object ifValidValue) {
		this.ifValidValue = ifValidValue;
	}
	
	/**
	 * Get message which is set if field has invalid value.
	 * @return message
	 */
	public String getIfInvalidMessage() {
		return ifInvalidMessage;
	}

	/**
	 * Set message if field has invalid value.
	 * @param ifInvalidMessage message to set
	 */
	public void setIfInvalidMessage(String ifInvalidMessage) {
		this.ifInvalidMessage = ifInvalidMessage;
	}

	/**
	 * Get message which is set if field has valid value.
	 * @return message
	 */
	public String getIfValidMessage() {
		return ifValidMessage;
	}

	/**
	 * Set message if field has valid value.
	 * @param ifValidMessage message to set.
	 */
	public void setIfValidMessage(String ifValidMessage) {
		this.ifValidMessage = ifValidMessage;
	}
	
	/**
	 * Get severity of the validation.
	 * @return severity value.
	 */
	public SEVERITY getSeverity() {
		return severity;
	}

	/**
	 * Set severity of the ValidationResult
	 * @param severity severity to set
	 */
	public void setSeverity(SEVERITY severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "ValidationResult [fieldName=" + fieldName +", isValid=" + isValid + ", ifInvalidValue=" + ifInvalidValue + ", ifValidValue="
				+ ifValidValue + ", ifInvalidMessage=" + ifInvalidMessage + ", ifValidMessage=" + ifValidMessage
				+ ", severity=" + severity + "]";
	}
	
}
