package com.infy.model;

public class Customer {
	
	private String customerId;
	private String customerName;
	private Double walletAmount;
	private CustomerType customerType;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	
	
}
