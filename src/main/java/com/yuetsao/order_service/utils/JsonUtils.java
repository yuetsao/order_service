package com.yuetsao.order_service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json工具类
 */
public class JsonUtils {

    private final static ObjectMapper objectMapperj = new ObjectMapper();

    /**
     * json字符串专程jsonnode 方
     */
    public static JsonNode str2JsonNode(String str) {
        try {
            return objectMapperj.readTree(str);
        } catch (IOException e) {
//            return objectMapperj.createObjectNode();
            return null;
        }
    }
}
