package pl.ulianak.hello;

import java.util.Collections;

public class App {
    public static void main(String[] args) {
        String name = "Uliana";

        var a = 2;
        var b = 3;
        System.out.println(a+b);

        System.out.println(String.format("Hello %s", name));

        var myList = Collections.emptyList();
        System.out.println(myList);
    }
}
