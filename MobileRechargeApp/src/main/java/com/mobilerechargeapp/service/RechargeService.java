package com.mobilerechargeapp.service;

import com.mobilerechargeapp.exception.RechargeNotFoundException;
import com.mobilerechargeapp.model.Recharge;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RechargeService {

	private final List<Recharge> recharges = new ArrayList<>();
	private final AtomicLong idCounter = new AtomicLong(1);

	// Validation already happened via @Valid in the controller before this runs
	public Recharge saveRecharge(Recharge recharge) {
		recharge.setRechargeId(idCounter.getAndIncrement());
		recharges.add(recharge);
		return recharge;
	}

	public List<Recharge> getAllRecharges() {
		return recharges;
	}

	public Recharge getRechargeById(Long id) {
		return recharges.stream().filter(r -> r.getRechargeId().equals(id)).findFirst()
				.orElseThrow(() -> new RechargeNotFoundException("Recharge with ID " + id + " not found"));
	}

	public String deleteRecharge(Long id) {
		Recharge recharge = getRechargeById(id);
		recharges.remove(recharge);
		return "Recharge with ID " + id + " deleted successfully";
	}
}