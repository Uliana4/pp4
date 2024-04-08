package pl.ulianak.creditcard;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class HelloTest {
    @Test
    void helloTest(){
        var name = "Uliana";
        var message = String.format("Hello %s", name);

        System.out.println(message);
    }

    @Test
    void equationTestV2(){
        //A  / Arrange
        int a = 2;
        int b = 3;
        //A  / Act
        var result = a+b;
        //A  / Assert
        assert 5 == result;
    }

    @Test
    void itGreetUsername() {
        //Arrange
        String name = "Jakub";
        //Act
        String message = String.format("Hello %s", name);
        //Assert
        assertEquals("Hello Jakub", message);
    }

    @Test
    void listExpectedToBeEmpty() {
        var list = new ArrayList<>();

        list.add("Jakub");
        list.remove(0);

        assert list.isEmpty();
    }
}
