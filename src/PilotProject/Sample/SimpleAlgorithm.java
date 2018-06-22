package PilotProject.Sample;

import PilotProject.Window.Controller;

import java.util.ArrayList;
import java.util.Random;

// Моиск максимального результата
public class SimpleAlgorithm {
    private ListChromosome listChromosome;
    private ArrayList<Chromosome> sortArrayListChromosome;
    private Controller controller;

    public SimpleAlgorithm(ListChromosome listChromosome, Controller controller) {
        if (listChromosome.getArrayList().size() != 4)
            throw new IllegalArgumentException("Кол-во хромосомов должно быть больше 5");
        this.listChromosome = listChromosome;
        this.controller = controller;

        System.out.println(listChromosome);
    }

    public void run(){
        sortArrayListChromosome = listChromosome.sort();

        Chromosome chromosome_0 = listChromosome.getArrayList().get(0);
        Chromosome chromosome_1 = listChromosome.getArrayList().get(1);
        Chromosome chromosome_2 = listChromosome.getArrayList().get(2);
        Chromosome chromosome_3 = listChromosome.getArrayList().get(3);

        chromosome_0.setGane(shaker(sortArrayListChromosome.get(1)));
        chromosome_1.setGane(shaker(sortArrayListChromosome.get(2)));
        chromosome_2.setGane(shaker(sortArrayListChromosome.get(3)));
        chromosome_3.setGane(shaker(sortArrayListChromosome.get(3)));

        System.out.println(listChromosome);
    }

    public ListChromosome getListChromosome(){
        return listChromosome;
    }

    public ArrayList<Chromosome> getSortArrayListChromosome(){
        return sortArrayListChromosome;
    }

    private Chromosome shaker(Chromosome chromosome){
        double x = chromosome.getGene(0).getGene();
        double y = chromosome.getGene(1).getGene();

        x = x + (x * random() -  x * random());
        y = y + (y * random() -  y * random());

        return new Chromosome(x + (x * random() -  x * random()), y + (y * random() -  y * random()));
    }

    private double random(){
        Random r = new Random();
        double rangeMin = controller.getTextMin();
        double rangeMax = controller.getTextMax();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
}
