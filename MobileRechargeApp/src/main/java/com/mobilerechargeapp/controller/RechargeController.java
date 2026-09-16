package com.mobilerechargeapp.controller;

import com.mobilerechargeapp.model.Recharge;
import com.mobilerechargeapp.service.RechargeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recharges")
public class RechargeController {

	private final RechargeService service;

	public RechargeController(RechargeService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // 201
	public Recharge createRecharge(@Valid @RequestBody Recharge recharge) {
		return service.saveRecharge(recharge);
	}

	@GetMapping
	public List<Recharge> getAllRecharges() {
		return service.getAllRecharges();
	}

	@GetMapping("/{id}")
	public Recharge getRechargeById(@PathVariable Long id) {
		return service.getRechargeById(id);
	}

	@DeleteMapping("/{id}")
	public String deleteRecharge(@PathVariable Long id) {
		return service.deleteRecharge(id);
	}
}