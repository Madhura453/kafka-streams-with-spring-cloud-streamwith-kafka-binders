package com.eventdrivenkafka.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domain {

    private String domain;

    private String create_date;

    private String update_date;

    private String country;

    private boolean isDead;

    private String A;

    private String NS;

    private String CNAME;

    private String MX;

    private String TXT;
}
