package com.lzhch.stream.lambda.init;

import lombok.*;

/**
 * Address
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/7/16 13:47
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String name;

    private String country;

    private String province;

    private String city;

    private String street;

}
