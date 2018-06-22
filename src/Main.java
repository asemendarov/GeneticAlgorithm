import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Main {

    private static int epoch = 30;
    private static int gene_size = 4;
    private static int population_size = 4;
    private static int chromosome_size = gene_size * 2;
    private static int chromosome_min_value = 0;
    private static int chromosome_max_value = 79;

    private static BinaryOperator<Double> f1 = (x, y) -> 0.2 * Math.pow(x - 70, 2) + 0.8 * Math.pow(y - 20, 2);
    private static BinaryOperator<Double> f2 = (x, y) -> 0.2 * Math.pow(x - 10, 2) + 0.8 * Math.pow(y - 70, 2);

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(BinaryCodeGray.generationBinaryCodeGray(population_size, chromosome_size)));
        System.out.println(Arrays.toString(BinaryCodeGray.decodeGrayToInteger(new int[]{0, 1, 1, 1, 0, 1, 1, 1})));
        System.out.println(Arrays.toString(BinaryCodeGray.DecodeGrayToFloat(
                new int[]{0, 1, 1, 1, 0, 1, 1, 1},
                chromosome_min_value,
                chromosome_max_value,
                gene_size
        )));
    }
}
