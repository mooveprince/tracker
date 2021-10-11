package com.wealth.stock.tracker.constant;

public enum ErrorEnum {
	
	JOB_FAILURE((short)800, "Exception occurred when executing the job"),
	EMPTY_SYMBOL((short)801, "Stock Symbol cannot be empty")
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
