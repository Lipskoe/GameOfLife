import java.util.Random;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Genes {

    public int[] genes;
    private static int[] essential_genes = {0, 1, 2, 3 ,4, 5, 6, 7};
    Random random = new Random();

    public Genes() {
        this.genes = GenerateRandomGenes();
    }

    public int SelectRandomGene(){
        int index = random.nextInt(32);
        return genes[index];
    }

    public int[] GenerateRandomGenes(){
        int[] rest_of_genes = new int[24];
        for (int i = 0; i < rest_of_genes.length; i++) {
            rest_of_genes[i] = random.nextInt(8);
        }
        int[] all_genes = new int[32];
        System.arraycopy(essential_genes, 0, all_genes, 0, 8);
        System.arraycopy(rest_of_genes, 0, all_genes, 8, 24);

        //shuffle array
        for (int i=0; i<all_genes.length; i++) {
            int random_position = random.nextInt(all_genes.length);
            int temp = all_genes[i];
            all_genes[i] = all_genes[random_position];
            all_genes[random_position] = temp;
        }
        return all_genes;
    }


    public void InheritGenes(Genes mother, Genes father){
        int[] indexes = random.ints(0, 32).distinct().limit(32).toArray();
        int[] new_genes = new int[32];
        int[] father_indexes;
        int[] mother_indexes;
        Arrays.fill(new_genes, 0);
        boolean most_genes=random.nextBoolean();
        if (most_genes==true){
            father_indexes = Arrays.copyOfRange(indexes,0,22);
            mother_indexes = Arrays.copyOfRange(indexes,22,32);
        }
        else{
            mother_indexes = Arrays.copyOfRange(indexes,0,22);
            father_indexes = Arrays.copyOfRange(indexes,22,32);
        }
        for(int i=0; i<father_indexes.length; i++){
            new_genes[father_indexes[i]]=father.genes[father_indexes[i]];
        }
        for(int i=0; i<mother_indexes.length; i++){
            new_genes[mother_indexes[i]]=mother.genes[mother_indexes[i]];
        }

        while(((IntStream.of(new_genes).anyMatch(x -> x == 0))&&(IntStream.of(new_genes).anyMatch(x -> x == 1))&&(IntStream.of(new_genes).anyMatch(x -> x == 2))&&(IntStream.of(new_genes).anyMatch(x -> x == 3))&&(IntStream.of(new_genes).anyMatch(x -> x == 4))&&(IntStream.of(new_genes).anyMatch(x -> x == 5))&&(IntStream.of(new_genes).anyMatch(x -> x == 6))&&(IntStream.of(new_genes).anyMatch(x -> x == 7)))==false) {
            if((IntStream.of(new_genes).anyMatch(x -> x == 0))==false){
                new_genes[random.nextInt(32)]=0;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 1))==false){
                new_genes[random.nextInt(32)]=1;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 2))==false){
                new_genes[random.nextInt(32)]=2;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 3))==false){
                new_genes[random.nextInt(32)]=3;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 4))==false){
                new_genes[random.nextInt(32)]=4;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 5))==false){
                new_genes[random.nextInt(32)]=5;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 6))==false){
                new_genes[random.nextInt(32)]=6;
            }
            if((IntStream.of(new_genes).anyMatch(x -> x == 7))==false){
                new_genes[random.nextInt(32)]=7;
            }
        }
        this.genes=new_genes;
    }
}


