import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class CheckArrTest {
    private CheckArr checkArr;
    @BeforeEach
    void setUp() {
        checkArr = new CheckArr();
    }

    public static Stream<Arguments> arrForTestAfter4() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] { 1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[] {1, 7}));
        out.add(Arguments.arguments(new int[] { 1, 2, 4, 4, 2, 3, 1, 7}, new int[] {2, 3, 1, 7}));
        out.add(Arguments.arguments(new int[] { 1, 2, 4, 4, 2, 3, 4, 1, 7, 4}, new int[] {}));
        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("arrForTestAfter4")
    void after4(int[] arr, int[] result) {
        Assertions.assertArrayEquals (result, checkArr.after4(arr));
    }

    @Test
    void after4ExceptionTest(){
        Assertions.assertThrows(RuntimeException.class, ()-> checkArr.after4(new int[] { 1, 2, 2, 3, 1, 7}));
    }

    public static Stream<Arguments> arrForTestIsOneOrFour () {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] { 1, 1, 1, 4, 4, 1, 4, 4}, true));
        out.add(Arguments.arguments(new int[] { 1, 1, 1, 1, 1, 1, 1, 1}, false));
        out.add(Arguments.arguments(new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, false));
        out.add(Arguments.arguments(new int[] { 1, 1, 1, 4, 4, 1, 4, 4, 3}, false));
        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("arrForTestIsOneOrFour")
    void isOneOrFour(int[] arr, boolean result) {
        Assertions.assertEquals(result, checkArr.isOneOrFour(arr));
    }
}