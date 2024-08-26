package com.example.posbackend.dao;




import com.example.posbackend.dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerData {
    CustomerDTO getCustomer(String id, Connection connection);
    String saveCustomer(CustomerDTO customerDTO, Connection connection);
    boolean deleteCustomer(String id, Connection connection);

    boolean updateCustomer(CustomerDTO customerDTO,Connection connection, String nic);
}
