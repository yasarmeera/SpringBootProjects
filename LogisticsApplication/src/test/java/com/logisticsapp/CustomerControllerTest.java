package com.logisticsapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.logisticsapp.controller.CustomerController;
import com.logisticsapp.dto.response.CustomerResponseDTO;
import com.logisticsapp.service.CustomerService;

// controller test - checks the /api/customers endpoint with a fake service (no database needed)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    // test case 2: GET /api/customers/1 returns status 200 and the customer name
    @Test
    @WithMockUser
    void getCustomerById_returnsCustomer() throws Exception {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(1L);
        dto.setName("Ravi");
        when(customerService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ravi"));
    }
}