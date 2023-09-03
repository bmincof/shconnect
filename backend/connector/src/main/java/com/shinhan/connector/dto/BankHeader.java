package com.shinhan.connector.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class BankHeader {
    String apikey;

    public BankHeader(String apikey) {
        this.apikey = apikey;
    }
}
