package com.tibianos.tibianosfanpage.utils;

import com.fasterxml.jackson.databind.JsonNode;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.EMPTY;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.SPACE;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.NULL;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.QUOTE;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.THREE_BACK_SLASH_QUOTE;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.EIGHT_BACK_SLASH;
import static com.tibianos.tibianosfanpage.utils.ConstanstHelper.FOUR_BACK_SLASH;

public class MethodsUtils {
    
    public static String getValueNode(JsonNode n, String field) {
        if (n == null){
            return EMPTY;
        }
        JsonNode valueNode = n.get(field);
        if (valueNode == null){
            return SPACE;
        }
        try {
            String stringNode = valueNode.toString();
            if (stringNode.equalsIgnoreCase(NULL)) {
                return SPACE;
            }
            String value = stringNode;
            if (value.indexOf(QUOTE) == 0) {
                value = stringNode.substring(1);
            }
            if (value.substring(value.length()-1).equalsIgnoreCase(QUOTE)) {
                value = value.substring(0, value.length() - 1);
            }
            value = value.replace(THREE_BACK_SLASH_QUOTE,QUOTE);
            value = value.replace(EIGHT_BACK_SLASH, FOUR_BACK_SLASH);
            return value.equalsIgnoreCase(EMPTY) ? SPACE: value;
        } catch (Exception e){
            return EMPTY;
        }
    }
}
