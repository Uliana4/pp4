package pl.ulianak.creditcard;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class AssertjTest {
    @Test
    void helloAssertJ(){
        var hello = "Hello World";

        assertThat(hello).contains("Hello World");
    }

    @Test
    void testSomeListExpression(){
        var names = Collections.singleton("Uliana");

        assertThat(names)
                .isUnmodifiable()
                .hasSize(1)
                .containsAll(Arrays.asList("Uliana"));
    }
}
