package com.example.posbackend.dao;

import com.example.posbackend.dto.CustomerDTO;
import com.example.posbackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.posbackend.dao.CustomerDataImpl.GET_STUDENT;

public class ItemDataImpl implements ItemData{
    static String SAVE_ITEM="INSERT INTO item (ItemCode,ItemName,Description,Qty,UnitPrice)VALUES(?,?,?,?,?) ";
    static String GET_ITEM="SELECT * FROM item WHERE ItemCode=?";
    static String UPDATE_ITEM="UPDATE item SET  ItemName=?,Description=?,Qty=?,UnitPrice=? WHERE ItemCode=?";
    static String DELETE_ITEM="DELETE FROM item WHERE ItemCode=?";
    @Override
    public ItemDTO getItem(String code, Connection connection) {
        ItemDTO itemDTO = new ItemDTO();

        try {
            PreparedStatement pstm = connection.prepareStatement(GET_ITEM);
            pstm.setString(1, code);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
               itemDTO.setItemCode(resultSet.getString("ItemCode"));
              itemDTO.setItemName(resultSet.getString("ItemName"));
               itemDTO.setDescription(resultSet.getString("Description"));
                itemDTO.setQty(Integer.parseInt(resultSet.getString("Qty")));
               itemDTO.setUnitPrice(Double.valueOf(resultSet.getString("UnitPrice")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return itemDTO;
    }

    @Override
    public String saveItem(ItemDTO itemDTO, Connection connection) {
        try {
            PreparedStatement pstm = connection.prepareStatement(SAVE_ITEM);
            pstm.setString(1,itemDTO.getItemCode());
            pstm.setString(2,itemDTO.getItemName());
            pstm.setString(3,itemDTO.getDescription());
            pstm.setString(4, String.valueOf(itemDTO.getQty()));
            pstm.setString(5, String.valueOf(itemDTO.getUnitPrice()));

            if(pstm.executeUpdate()>0){
                return  "save successful";
            }else{
                return "save failed";
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean deleteItem(String code, Connection connection) {
        try {
            var ps=  connection.prepareStatement(DELETE_ITEM);
            ps.setString(1,code);
            return ps.executeUpdate()!=0;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(ItemDTO itemDTO, Connection connection, String code) {
        try {
            PreparedStatement pstm = connection.prepareStatement(UPDATE_ITEM);

            pstm.setString(1, itemDTO.getItemName());
            pstm.setString(2, itemDTO.getDescription());
            pstm.setString(3, String.valueOf(itemDTO.getQty()));
            pstm.setString(4, String.valueOf(itemDTO.getUnitPrice()));
            pstm.setString(5, code);
            return pstm.executeUpdate()>0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
