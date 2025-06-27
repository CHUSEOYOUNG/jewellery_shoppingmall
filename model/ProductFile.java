package com.sist.web.model;

import java.io.Serializable;

public class ProductFile implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2651033576021458861L;
    private long productFileId;
    private String productId;
    private String productFileType;
    private String productFileName;
    private String productFilePath;
    private String productRegDate;

    
    public ProductFile()
    {
        productFileId = 0;
        productId = "";
        productFileType = "";
        productFileName = "";
        productFilePath = "";
        productRegDate = "";

    }


	public long getProductFileId() {
		return productFileId;
	}


	public void setProductFileId(long productFileId) {
		this.productFileId = productFileId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductFileType() {
		return productFileType;
	}


	public void setProductFileType(String productFileType) {
		this.productFileType = productFileType;
	}


	public String getProductFileName() {
		return productFileName;
	}


	public void setProductFileName(String productFileName) {
		this.productFileName = productFileName;
	}


	public String getProductFilePath() {
		return productFilePath;
	}


	public void setProductFilePath(String productFilePath) {
		this.productFilePath = productFilePath;
	}


	public String getProductRegDate() {
		return productRegDate;
	}


	public void setProductRegDate(String productRegDate) {
		this.productRegDate = productRegDate;
	}
    
    
}
