package com.example.posbackend.dao;

import com.example.posbackend.dto.CustomerDTO;
import com.example.posbackend.dto.ItemDTO;

import java.sql.Connection;

public interface ItemData {
    ItemDTO getItem(String id, Connection connection);
    String saveItem(ItemDTO itemDTO, Connection connection);
    boolean deleteItem(String id, Connection connection);

    boolean updateItem(ItemDTO itemDTO,Connection connection, String id);
}
