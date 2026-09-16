package com.mobilerechargeapp.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recharge {

	private Long rechargeId;

	@Pattern(regexp = "[0-9]{10}", message = "Mobile number must be exactly 10 digits")
	private String mobileNumber;

	@NotBlank(message = "Operator name cannot be blank")
	private String operator;

	@Min(value = 10, message = "Recharge amount must be at least 10")
	private double amount;

	@NotBlank(message = "Plan type cannot be blank")
	private String planType;
}