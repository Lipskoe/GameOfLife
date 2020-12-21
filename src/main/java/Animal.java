import java.util.*;

public class Animal {

    private Vector2d position;
    private MapDirection direction;
    private GrassField map;
    private Genes genes;
    private int energy;
    private int days_lived;
    private int number_of_kids;

    public Animal(GrassField map, Vector2d initial_position, int initial_energy){
        this.genes=new Genes();
        this.position = initial_position;
        this.direction = MapDirection.NORTH;
        this.map=map;
        this.energy=initial_energy;
        this.number_of_kids=0;
        this.days_lived=0;
    }

    public Vector2d move(int move_energy){
        int rotate =this.genes.SelectRandomGene();
        Vector2d newposition;
        switch(rotate) {
            case 0:
                ;
            case 1:
                this.direction=this.direction.next();
            case 2:
                this.direction=this.direction.next();
                this.direction=this.direction.next();
            case 3:
                this.direction=this.direction.next();
                this.direction=this.direction.next();
                this.direction=this.direction.next();
            case 4:
                this.direction=this.direction.next();
                this.direction=this.direction.next();
                this.direction=this.direction.next();
                this.direction=this.direction.next();
            case 5:
                this.direction=this.direction.previous();
                this.direction=this.direction.previous();
                this.direction=this.direction.previous();
            case 6:
                this.direction=this.direction.previous();
                this.direction=this.direction.previous();
            case 7:
                this.direction=this.direction.previous();
        }
        newposition=this.position.add(this.direction.toUnitVector());

        if ((newposition.getx()>map.getWidth()-1)&&(newposition.gety()>map.getHeight()-1)){
            newposition.setx(newposition.getx()-map.getWidth());
            newposition.sety(newposition.gety()-map.getHeight());
        } else if ((newposition.getx()>map.getWidth()-1)&&(newposition.gety()<0)){
            newposition.setx(newposition.getx()-map.getWidth());
            newposition.sety(newposition.gety()+map.getHeight());
        } else if ((newposition.getx()<0)&&(newposition.gety()>map.getHeight()-1)){
            newposition.setx(newposition.getx()+map.getWidth());
            newposition.sety(newposition.gety()-map.getHeight());
        } else if ((newposition.getx()<0)&&(newposition.gety()<0)){
            newposition.sety(newposition.gety()+map.getHeight());
            newposition.setx(newposition.getx()+map.getWidth());
        } else if (newposition.getx()>map.getWidth()-1){
            newposition.setx(newposition.getx()-map.getHeight());
        } else if (newposition.getx()<0) {
            newposition.setx(newposition.getx()+map.getWidth());
        } else if (newposition.gety()>map.getHeight()-1) {
            newposition.sety(newposition.gety()-map.getHeight());
        } else if (newposition.gety()<0) {
            newposition.sety(newposition.gety()+map.getHeight());
        }

        if (map.canMoveTo(newposition, this)){
            this.position=newposition;
        }

        this.changeEnergy(-move_energy, this.map.getMax_energy());
        this.days_lived+=1;
        return this.position;
    }

    public void changeEnergy(int value, int max_energy) {
        this.energy = this.energy + value;
        if (this.energy < 0) {
            this.energy = 0;
        }
        if(this.energy>max_energy){
            this.energy=max_energy;
        }
    }

    public boolean isDead(){
        if(this.energy==0){
            return true;
        }
        else {
            return false;
        }
    }

    public Vector2d getPosition(){ return this.position; }

    public MapDirection getDirection(){ return this.direction; }

    public int getDays_lived(){ return this.days_lived; }

    public int getNumber_of_kids(){
        return this.number_of_kids;
    }

    public int GetEnergy(){ return this.energy; }

    public Genes GetGenes(){ return this.genes; }

    public void setNumber_of_kids(int new_number_of_kids){
        this.number_of_kids=new_number_of_kids;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return energy == animal.energy &&
                position.equals(animal.position) &&
                direction == animal.direction &&
                map.equals(animal.map) &&
                genes.equals(animal.genes);
    }

    public int hashCode() {
        return Objects.hash(position, direction, map, genes, energy);
    }
}

