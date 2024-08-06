package com.cimu.usercenter.service;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @projectName: User-center
 * @package: com.cimu.usercenter.service
 * @className: md5Test
 * @author: 辞暮
 * @date: 5/7/2024 下午4:20
 * @version: 1.0
 */
public class md5Test {
    public static void main(String[] args) {
        String a="12345678";
        String salt="cimu";
        String encrypt= DigestUtils.md5DigestAsHex((salt+a).getBytes(StandardCharsets.UTF_8));
        System.out.println(encrypt);
    }
}
