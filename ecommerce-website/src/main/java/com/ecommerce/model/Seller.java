package com.ecommerce.model;

import com.ecommerce.domain.AccountStatus;
import com.ecommerce.domain.USER_ROLE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String sellerName;

	private String mobile;

	@Column(unique = true, nullable = false)
	private String email;
	private String password;

	@Embedded
	private BusinessDetails businessDetails = new BusinessDetails();

	@Embedded
	private BankDetails bankDetails = new BankDetails();

	@OneToOne(cascade = CascadeType.ALL)
	private Address pickupAddress = new Address();

	private String GSTIN;

	private USER_ROLE role;
	
	private boolean isEmailVerified=false;
	
	private AccountStatus accountStatus=AccountStatus.PENDING_VERIFICATION;

}
