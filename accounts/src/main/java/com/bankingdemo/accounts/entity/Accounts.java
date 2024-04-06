package com.bankingdemo.accounts.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity{
	
	@Id
	private Long accountNumber;
	
	private String accountType;
	
	private String branchAddress;

	private Boolean communicationSw;
	
	@Column(insertable = false, updatable = false, name = "customer_id")
	private Long customerId;
	
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}
