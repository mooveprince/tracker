package com.wealth.stock.tracker.model;


/**
 * @author moove
 * 
 * Response object for Upload API
 */
public class StockTrackerUploadResponse {
	
	public String fileName;
	public long fileSize;
	public String fileUploadStatus;
	public StockTrackerError error;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUploadStatus() {
		return fileUploadStatus;
	}
	public void setFileUploadStatus(String fileUploadStatus) {
		this.fileUploadStatus = fileUploadStatus;
	}
	public StockTrackerError getError() {
		return error;
	}
	public void setError(StockTrackerError error) {
		this.error = error;
	}
	
	
	

}
