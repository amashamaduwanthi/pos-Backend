package com.example.posbackend.dao;



import com.example.posbackend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderData {
     boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException;
}
