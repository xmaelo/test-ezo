package com.ezo.test;

import java.util.List;

public class Main {

    public static  void  main(String[] args){
        IService  serv = new Service();
        List<String> tests = List.of(
            "1+1",
            "1 + 2",
            "1 + -1",
            "-1 - -1",
            "5-4",
            "5*2",
            "(2+5)*3",
            "10/2",
            "2+2*5+5",
            "2.8*3-1",
            "2^8",
            "2^8*5-1",
            "sqrt(4)",
            "1/0"
        );
        for (int i = 0; i < tests.size(); i++) {
            System.out.println("Test: "+ tests.get(i) +" Result: " + serv.run(tests.get(i)));
        }
    }
}
