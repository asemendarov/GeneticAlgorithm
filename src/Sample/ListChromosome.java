package Sample;

import java.util.ArrayList;
import java.util.Comparator;

public class ListChromosome {
    private final ArrayList<Chromosome> chromosomeArrayList = new ArrayList<>();

    public void add(Chromosome chromosome){
        chromosomeArrayList.add(chromosome);
    }

    public void add(double geneX, double geneY){
        chromosomeArrayList.add(new Chromosome(geneX, geneY));
    }

    public void update(int index, Chromosome chromosome){
        chromosomeArrayList.get(index).setGane(chromosome);
    }

    public ArrayList<Chromosome> getArrayList(){
        return chromosomeArrayList;
    }

    public ArrayList<Chromosome> sort() {
        ArrayList<Chromosome> newChromosomeArrayList = new ArrayList<>(chromosomeArrayList);
        newChromosomeArrayList.sort(Comparator.comparingDouble(Chromosome::getResult));
        return newChromosomeArrayList;
    }

    public double sumResult(){
        double sum = 0;
        for (Chromosome chromosome: chromosomeArrayList){
            sum = sum + chromosome.getResult();
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder str_1 = new StringBuilder();
        StringBuilder str_2 = new StringBuilder();
        StringBuilder str_3 = new StringBuilder();

        for (Chromosome chromosome: chromosomeArrayList){
            str_1.append(String.format("\t%.03f", chromosome.getGene(0).getGene()));
            str_2.append(String.format("\t%.03f", chromosome.getGene(1).getGene()));
            str_3.append(String.format("\t%.03f", chromosome.getResult()));
        }

        return String.format("%s\n%s\n%s\t%.03f\n", str_1.toString(), str_2.toString(), str_3.toString(), sumResult());
    }
}
