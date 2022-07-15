package com.infy.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import com.infy.model.CustomerType;

@Entity
@Table(name = "Customer")
public class CustomerEntity {
	
	@Id
	private String customerId;
	private String customerName;
	private Double walletAmount;
	@Enumerated(EnumType.STRING)
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
