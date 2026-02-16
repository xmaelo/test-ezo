package com.ezo.test;

import java.util.List;

public interface IService {

    List<String> parse(String input);

    double calculate(List<String> tokens) throws TestException;

    Object run(String input);
}
