package com.daviholanda.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class URL {

    public static List<Long> decodeIntList(String s) {

        String[] vet  =s.split(",");
        List<Long> listaRetorno = new ArrayList<>();

        for (int i = 0; i < vet.length; i++) {
            listaRetorno.add(Long.parseLong(vet[i]));
        }

        return listaRetorno;
    }
    
    public static String decodeParam(String param) {

        try {
            return URLDecoder.decode(param,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }

    }

}
