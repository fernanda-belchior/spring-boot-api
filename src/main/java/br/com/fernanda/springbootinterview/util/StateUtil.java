package br.com.fernanda.springbootinterview.util;

import java.util.Arrays;
import java.util.List;

public class StateUtil {

    private StateUtil(){}

    private static List<String> stateList = Arrays.asList(
            "AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MS",
            "MT","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO");

    public static List<String> getStateList() {
        return stateList;
    }
}

