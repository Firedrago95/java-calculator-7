package calculator.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputParserTest {

    private InputParser inputParser = new InputParser();

    @Test
    @DisplayName("기본구분자 사용확인 테스트")
    public void checkDefaultDelimiterTest() {
        //given
        String input = "1,2:3";
        String input2 = "1";

        //when
        boolean hasDefaultDelimiter = inputParser.checkDefaultDelimiter(input);
        boolean hasDefaultDelimiter2 = inputParser.checkDefaultDelimiter(input2);

        //then
        assertThat(hasDefaultDelimiter).isTrue();
        assertThat(hasDefaultDelimiter2).isTrue();
    }

    @Test
    @DisplayName("기본구분자 사용확인 예외 테스트")
    public void checkDefaultDelimiterExceptionTest() {
        //given
        String input = "1;2,3";

        //when
        boolean hasDefaultDelimiter = inputParser.checkDefaultDelimiter(input);

        //then
        assertThat(hasDefaultDelimiter).isFalse();
    }

    @Test
    @DisplayName("기본구분자 List 변환 테스트")
    public void convertDefaultDelimiterTest() {
        //given
        String input = "1,2:3";
        String input2 = "1";

        //when
        List<Integer> result1 = inputParser.convertDefaultDelimiter(input);
        List<Integer> result2 = inputParser.convertDefaultDelimiter(input2);

        //then
        assertThat(result1).isEqualTo(List.of(1, 2, 3));
        assertThat(result2).isEqualTo(List.of(1));
    }

    @Test
    @DisplayName("커스텀 구분자 사용확인 테스트")
    public void checkCustomDelimiterTest() {
        //given
        String input = "//;\\n1";

        //when
        boolean hasCustomDelimiter1 = inputParser.checkCustomDelimiter(input);

        //then
        assertThat(hasCustomDelimiter1).isTrue();
    }

    @Test
    @DisplayName("커스텀 구분자 List 변환 테스트")
    public void convertCustomDelimiterTest() {
        //given
        String input1 = "//;\\n1;2;3";
        String input2 = "//;\\n1";

        //when
        List<Integer> result1 = inputParser.convertCustomDelimiter(input1);
        List<Integer> result2 = inputParser.convertCustomDelimiter(input2);

        //then
        assertThat(result1).isEqualTo(List.of(1, 2, 3));
        assertThat(result2).isEqualTo(List.of(1));
    }

    @Test
    @DisplayName("커스텀 구분자 List 변환 예외테스트")
    public void convertCustomDelimiterExceptionTest() {
        //given
        String input1 = "//]\\n1;2;3";
        String input2 = "/;\\n1;2;3";

        //then
        assertThrows(IllegalArgumentException.class,
            () -> inputParser.convertCustomDelimiter(input1));
        assertThrows(IllegalArgumentException.class,
            () -> inputParser.convertCustomDelimiter(input2));
    }

    @Test
    @DisplayName("양수 검사 테스트")
    public void checkPositiveNumberTest() {
        //given
        List<Integer> list = List.of(1, 2, 3, -1);

        //then
        assertThrows(IllegalArgumentException.class,
            () -> inputParser.checkPositiveNumber(list));
    }
}