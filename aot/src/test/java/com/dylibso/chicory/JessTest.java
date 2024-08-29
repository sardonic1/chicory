package com.dylibso.chicory;

import org.junit.jupiter.api.Test;

public class JessTest {
    @Test
    public void testDiv() {
        int x = 100;
        int count = 0;
        for (int i = 0; i <= x / 50; i++) {
            count++;
        }
        System.out.println(count);
    }
}
