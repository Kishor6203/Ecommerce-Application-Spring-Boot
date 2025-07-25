package com.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.domain.PaymentMethod;
import com.ecommerce.domain.PaymentOrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class PaymentOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long amount;
	
	private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

	private PaymentMethod paymentMethod;

	private String paymentLinkId;

	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "paymentOrder", cascade = CascadeType.ALL)
	private Set<Order> order = new HashSet<>();


}
