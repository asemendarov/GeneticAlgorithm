import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class BinaryCodeGray {
    public static int[][] generationBinaryCodeGray(int population_size, int chromosome_size) {
        int min = 0;
        int max = 1;
        int[][] population = new int[population_size][chromosome_size];
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                population[i][j] = (int) (Math.random() * ((max - min) + 1));
            }
        }
        return population;
    }

    public static void grayCodeeEncode(int n, int gene_size) {
        // pass
    }

    public static int[] decodeGrayToInteger(int[] chromosomeCodeGray){
        int gene_size = chromosomeCodeGray.length >> 1;
        int[] z1 = new int[gene_size];
        int[] z2 = new int[gene_size];

        for (int i = 0; i < gene_size; i++){
            z1[i] = chromosomeCodeGray[i];
            z2[i] = chromosomeCodeGray[gene_size + i];
        }

//        List<Integer> resultIntList = new ArrayList<>(2);
        int[] arrayL = new int[2];
        int indexArrayL = 0;

        for (int[] z: new int[][]{z1, z2}){
            int s1 = z[0] * (2 << z.length - 2);
            int s2 = 0;

            for (int i = 1; i < z.length - 1; i++){
                int summa = 0;

                for (int j = 0; j < i; j++)
                    summa = summa + z[j];

                summa = summa % 2;
                summa = summa * (1 - 2 * z[i]) + z[i];
                summa = summa * (2 << z.length - i - 2);
                s2 = s2 + summa;
            }

            int summa = 0;

            for (int i = 0; i < z.length - 1; i++)
                summa = summa + z[i];

            summa = summa % 2;

            int s3 = summa * (1 - 2 * z[z.length - 1]);
            int s4 = z[z.length - 1];

//            resultIntList.add(s1 + s2 + s3 + s4);
            arrayL[indexArrayL++] = s1 + s2 + s3 + s4;
        }

//        return toIntArray(resultIntList);
        return arrayL;
    }

    public static double[] DecodeGrayToFloat(int[] chromosomeCodeGray, int chromosome_min_value, int chromosome_max_value, int gene_size){
        int[] arrayL = decodeGrayToInteger(chromosomeCodeGray);

        double[] arrayR = new double[arrayL.length];
        for (int i = 0; i < arrayL.length; i++){
            arrayR[i] = chromosome_min_value + (double) (arrayL[i] * (chromosome_max_value - chromosome_min_value)) / ((2 << gene_size - 1) - 1);
        }

        return arrayR;
    }

//    private static int[] toIntArray(List<Integer> list)  {
//        int[] ret = new int[list.size()];
//        int i = 0;
//        for (Integer e : list)
//            ret[i++] = e;
//        return ret;
//    }
}
