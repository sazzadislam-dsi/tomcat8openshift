import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class ScreeningTest {
    public static void main(String[] args) {
        List<Integer> data1 = Arrays.asList(1, 4, 7);
        List<Integer> data2 = Arrays.asList(123, -2, 477, 3, 14, 6551);

        ScreeningTest obj = new ScreeningTest();

        int result = obj.fold(data1);
        System.out.println(result);

        int yourAnswer = obj.fold(data2); //what is the answer for this one???
        System.out.println(yourAnswer);
    }


    private int fold(List<Integer> input) {
        Integer res;
        if (input.size() == 1) {
            return input.get(0);
        }else {
            try {
                res = input.get(0);
                for (int i = 1; i < input.size(); i++) {
                    input.set(i, input.get(i) + res);
                }
                input.remove(0);
                return fold(input);
            } catch (UnsupportedOperationException ex) {
                input = new ArrayList<>(input);
                input.remove(0);
                return fold(input);
            }
        }

    }
}