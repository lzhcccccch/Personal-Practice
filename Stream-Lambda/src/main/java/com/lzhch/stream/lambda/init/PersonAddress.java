package com.lzhch.stream.lambda.init;

import lombok.*;

import java.io.Serializable;

/**
 * 人员地址
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/7/16 13:57
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonAddress implements Serializable {
    private static final long serialVersionUID = -6769093003470718554L;

    private String name;

    private String sex;

    private int age;

    private int salary;

    private String address;

    private String country;

    private String province;

    private String city;

    private String street;

}
