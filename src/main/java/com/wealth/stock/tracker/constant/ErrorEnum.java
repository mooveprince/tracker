package com.wealth.stock.tracker.constant;

public enum ErrorEnum {
	
	JOB_FAILURE((short)800, "Exception occurred when executing the job"),
	EMPTY_SYMBOL((short)801, "Stock Symbol cannot be empty"),
	INVALID_INPUT((short)802, "Invalid value found. Please provide valid value for quarter, date and ticker"),
	SEARCH_EXCEPTION_OCCURED((short)803, "Error occurred during search"),
	ADD_TRACKER_ERROR((short)804, "Error in persisting the tracker record"),
	ADD_EXCEPTION_OCCURED((short)805, "Error occurred during search"),
	UPLOAD_FILE_ERROR((short)806, "Error in parsing the input file. Please check the template"),
	SYMBOL_NOT_FOUND((short)803, "Stock not available in tracker")
	;
	
	private final short key;
	private final String value;
	
	ErrorEnum(short key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public short getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
	

}
