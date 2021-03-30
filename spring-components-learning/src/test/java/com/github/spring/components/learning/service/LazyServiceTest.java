package com.github.spring.components.learning.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
@SpringBootTest
public class LazyServiceTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp----");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLazy() {
        System.out.println("testLazy----");
    }
}