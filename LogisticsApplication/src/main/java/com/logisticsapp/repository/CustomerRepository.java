package com.logisticsapp.repository;

import com.logisticsapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// repository - database access for customer (Spring Data JPA)
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // true if a customer with this email already exists
    boolean existsByEmail(String email);

    // true if another customer (different id) already uses this email
    boolean existsByEmailAndIdNot(String email, Long id);
}