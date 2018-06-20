package Sample;

// Хромосома
public class Chromosome {
    private Gene[] genes = new Gene[2];
    private double result;

    public Chromosome(double geneX, double geneY){
        genes[0] = new Gene(geneX);
        genes[1] = new Gene(geneY);
        result = result();
    }

    public Chromosome(Gene geneX, Gene geneY){
        genes[0] = geneX;
        genes[1] = geneY;
        result = result();
    }

    public void setGane(int index, double value) {
        genes[index] = new Gene(value);
        result = result();
    }

    public void setGane(int index, Gene value) {
        genes[index] = value;
        result = result();
    }

    public void setGane(Chromosome chromosome) {
        Gene[] genes = chromosome.getGenes();
        this.genes[0] = genes[0];
        this.genes[1] = genes[1];
        result = result();
    }

    public Gene getGene(int index){
        return genes[index];
    }

    public Gene[] getGenes() {
        return genes;
    }

    private double result(){
        /*     x / (x^2 + 2y^2 + 1)       */

        double x = genes[0].getGene();
        double y = genes[1].getGene();

        double denominator = Math.pow(x, 2) + 2 * Math.pow(y, 2) + 1;

        return (denominator != 0) ? (x / denominator) : 0;
    }

    public double getResult(){
        return result;
    }

    @Override
    public String toString() {
        return String.format("{%.03f; %.03f; %.03f} ", genes[0].getGene(), genes[1].getGene(), result);
    }
}
