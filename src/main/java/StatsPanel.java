import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    public JLabel label1;
    public JLabel label2;
    public JLabel label3 ;
    public JLabel label4 ;
    public JLabel label5 ;
    public JLabel label6 ;
    public JLabel label7 ;
    public JLabel label8;
    public JLabel label9;
    public JLabel label10 ;
    public JLabel label11 ;
    public JLabel label12 ;
    public JLabel label13 ;
    public JLabel label14 ;
    public JLabel label15 ;
    public GrassField map;
    public JButton pause_button;
    public int previous_dead_number;
    public int previous_dead_sum;


    public StatsPanel(GrassField map) {
        this.setBackground(Color.lightGray);
        this.setLayout(null);
        this.map=map;
        this.label1 = new JLabel();
        label1.setBounds(10, 0, 200, 50);
        this.label2 = new JLabel();
        label2.setBounds(10, 20, 200, 50);
        this.label3 = new JLabel();
        label3.setBounds(10, 40, 200, 50);
        this.label4 = new JLabel();
        label4.setBounds(10, 60, 200, 50);
        this.label5 = new JLabel();
        label5.setBounds(10, 80, 200, 50);
        this.label6 = new JLabel();
        label6.setBounds(10, 100, 200, 50);
        this.label7 = new JLabel();
        label7.setBounds(10, 120, 200, 50);
        this.label8 = new JLabel();
        label8.setBounds(10, 140, 200, 50);
        this.label9 = new JLabel();
        label9.setBounds(10, 160, 200, 50);
        this.label10 = new JLabel();
        label10.setBounds(10, 180, 200, 50);
        this.label11 = new JLabel();
        label11.setBounds(10, 200, 200, 50);
        this.label12 = new JLabel();
        label12.setBounds(10, 220, 200, 50);
        this.label13 = new JLabel();
        label13.setBounds(10, 240, 200, 50);
        this.label14 = new JLabel();
        label14.setBounds(10, 260, 200, 50);
        this.label15 = new JLabel();
        label15.setBounds(10, 280, 200, 50);
        this.pause_button=new JButton("pause");
        pause_button.setBounds(10, 320, 150, 50);

        add(label1);
        add(label2);
        add(label3);
        add(label4);
        add(label5);
        add(label6);
        add(label7);
        add(label8);
        add(label9);
        add(label10);
        add(label11);
        add(label12);
        add(label13);
        add(label14);
        add(label15);
        add(pause_button);

        setVisible(true);
        previous_dead_number=0;
        previous_dead_sum=0;
    }

    public void UpdateStats(){
        int energy_sum=0;
        for (int i=0; i<map.getAnimals_list().size(); i++){
            energy_sum+=map.getAnimals_list().get(i).GetEnergy();
        }
        int average_energy=energy_sum/map.getAnimals_list().size();

        int days_lived_sum=0;
        for (int i=0; i<map.getDead_animals_list().size(); i++){
            days_lived_sum+=map.getDead_animals_list().get(i).getDays_lived();
        }

        float average_days_lived=0;
        if((map.getDead_animals_list().size()+previous_dead_number==0)==false){
            average_days_lived=((float)(days_lived_sum+previous_dead_sum))/(map.getDead_animals_list().size()+previous_dead_number);
        }

        int number_of_kid_sum=0;
        for (int i=0; i<map.getAnimals_list().size(); i++){
            number_of_kid_sum+=map.getAnimals_list().get(i).getNumber_of_kids();
        }
        float average_number_of_kid=((float) number_of_kid_sum)/map.getAnimals_list().size();

        int number_of_genotype_0=0;
        int number_of_genotype_1=0;
        int number_of_genotype_2=0;
        int number_of_genotype_3=0;
        int number_of_genotype_4=0;
        int number_of_genotype_5=0;
        int number_of_genotype_6=0;
        int number_of_genotype_7=0;

        for (int i=0; i<map.getAnimals_list().size();i++){
            number_of_genotype_0 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 0);
            number_of_genotype_1 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 1);
            number_of_genotype_2 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 2);
            number_of_genotype_3 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 3);
            number_of_genotype_4 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 4);
            number_of_genotype_5 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 5);
            number_of_genotype_6 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 6);
            number_of_genotype_7 += count_occurances(map.getAnimals_list().get(i).GetGenes().genes, 7);
        }
        float gen_0_popularity = ((float) number_of_genotype_0) / (32*map.getAnimals_list().size());
        float gen_1_popularity = ((float) number_of_genotype_1) / (32*map.getAnimals_list().size());
        float gen_2_popularity = ((float) number_of_genotype_2) / (32*map.getAnimals_list().size());
        float gen_3_popularity = ((float) number_of_genotype_3) / (32*map.getAnimals_list().size());
        float gen_4_popularity = ((float) number_of_genotype_4) / (32*map.getAnimals_list().size());
        float gen_5_popularity = ((float) number_of_genotype_5) / (32*map.getAnimals_list().size());
        float gen_6_popularity = ((float) number_of_genotype_6) / (32*map.getAnimals_list().size());
        float gen_7_popularity = ((float) number_of_genotype_7) / (32*map.getAnimals_list().size());

        this.label1.setText("animals amount: "+Integer.toString(this.map.getAnimals_list().size()));
        this.label2.setText("grass amount: "+Integer.toString(this.map.getGrass_list().size()));
        this.label3.setText("average energy: "+Integer.toString(average_energy));
        this.label4.setText("dead animals: "+Integer.toString(previous_dead_number+map.getDead_animals_list().size()));
        this.label5.setText("average lifespan: "+String.format("%2.01f", average_days_lived));
        this.label6.setText("days passed: "+Integer.toString(this.map.getDays_passed()));
        this.label7.setText("average number of kids: "+String.format("%2.01f", average_number_of_kid));
        this.label8.setText("Genotype 0 popularity: "+String.format("%2.02f", gen_0_popularity));
        this.label9.setText("Genotype 1 popularity: "+String.format("%2.02f", gen_1_popularity));
        this.label10.setText("Genotype 2 popularity: "+String.format("%2.02f", gen_2_popularity));
        this.label11.setText("Genotype 3 popularity: "+String.format("%2.02f", gen_3_popularity));
        this.label12.setText("Genotype 4 popularity: "+String.format("%2.02f", gen_4_popularity));
        this.label13.setText("Genotype 5 popularity: "+String.format("%2.02f", gen_5_popularity));
        this.label14.setText("Genotype 6 popularity: "+String.format("%2.02f", gen_6_popularity));
        this.label15.setText("Genotype 7 popularity: "+String.format("%2.02f", gen_7_popularity));

        previous_dead_number+=map.getDead_animals_list().size();
        previous_dead_sum+=days_lived_sum;
        map.getDead_animals_list().clear();
    }

    public int count_occurances(int[] genes, int gene){
        int genecounter=0;
        for(int j=0; j<genes.length; j++){
            if(genes[j]==gene){
                genecounter+=1;
            }
        }
        return genecounter;
    }
}