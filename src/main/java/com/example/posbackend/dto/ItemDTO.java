package com.example.posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO implements Serializable {
    private String ItemCode;
    private String ItemName;
    private String Description;
    private int Qty;
    private  Double UnitPrice;
}
