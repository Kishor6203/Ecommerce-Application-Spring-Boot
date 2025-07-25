package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
	
    private String accountNumber;
    
    private String accountHolderName;
    
    // private String bankName;                // This line appears commented out in the image
    
    private String ifscCode;
}
