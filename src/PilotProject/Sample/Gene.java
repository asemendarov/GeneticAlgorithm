package PilotProject.Sample;

public class Gene {
    private double gene;

    public Gene(double gene) {
        this.gene = gene;
    }

    public double getGene() {
        return gene;
    }

    @Override
    public String toString() {
        return String.valueOf(gene);
    }
}
