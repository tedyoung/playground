package dev.ted;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AppTest {

    @Test
    public void helloShouldReturnName() {
        App app = new App();
        assertThat(app.hello("Bob")).isEqualTo("Hello, Bob");
    }
}
