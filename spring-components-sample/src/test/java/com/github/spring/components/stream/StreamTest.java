package com.github.spring.components.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jianlei.shi
 * @date 2021/1/18 11:51 上午
 * @description StreamTest
 */

public class StreamTest {
    public static void createStream_whenFindAnyResultIsPresent_thenCorrect() {
        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findAny();
        final String s = result.get();
        System.out.println("s->"+s);  //s->A
        assertTrue(result.isPresent());
//        assertThat(result.get(), anyOf(is("A"), is("B"), is("C"), is("D")));
    }

    public static void createParallelStream_whenFindAnyResultIsPresent_thenCorrect() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> result = list
                .stream().parallel()
                .filter(num -> num < 4).findAny();
        final Integer s = result.get();
        System.out.println("s->"+s);  //s->A

        assertTrue(result.isPresent());
    }
    public static void main(String[] args) {
//          createStream_whenFindAnyResultIsPresent_thenCorrect();
createParallelStream_whenFindAnyResultIsPresent_thenCorrect();
    }
}
