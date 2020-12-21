import java.util.*;

public class GrassField{
    private int plant_energy;
    private int spawn_energy;
    private int move_energy;
    private int max_energy;
    private int jungle_width;
    private int jungle_height;
    private int jungle_start_x;
    private int jungle_start_y;
    private int width;
    private int height;
    private int days_passed;
    private int daily_grass_on_field;
    private int daily_grass_on_jungle;
    private LinkedHashMap<Vector2d, LinkedList<Animal>> animals_map;
    private LinkedList<Animal> animals_list;
    private LinkedList<Animal> dead_animals_list;
    private LinkedList<Vector2d> jungle_list;
    private LinkedList<Vector2d> field_list;
    private LinkedHashMap<Vector2d, Grass> grass_list;
    private Random r;


    public GrassField(int width, int height, double jungle_ratio, int start_animals, int spawn_energy, int plant_energy, int move_energy, int max_energy, int start_field_grass, int start_jungle_grass, int daily_grass_on_field, int daily_grass_on_jungle){
        this.width = width;
        this.height = height;
        this.jungle_width= (int) Math.ceil(jungle_ratio * width);
        this.jungle_height= (int) Math.ceil(jungle_ratio * height);
        this.jungle_start_x= (int) Math.floor((this.width-this.jungle_width)/2);
        this.jungle_start_y= (int) Math.floor((this.height-this.jungle_height)/2);
        this.spawn_energy=spawn_energy;
        this.plant_energy=plant_energy;
        this.move_energy=move_energy;
        this.max_energy=max_energy;
        this.days_passed=0;
        this.daily_grass_on_field=daily_grass_on_field;
        this.daily_grass_on_jungle=daily_grass_on_jungle;
        this.animals_map = new LinkedHashMap<Vector2d, LinkedList<Animal>>();
        this.animals_list=new LinkedList<Animal>();
        this.dead_animals_list=new LinkedList<Animal>();
        this.jungle_list=new LinkedList<Vector2d>();
        this.field_list=new LinkedList<Vector2d>();
        this.grass_list = new LinkedHashMap<Vector2d, Grass>();
        this.r = new Random();

        for(int i=jungle_start_x; i<jungle_start_x+jungle_width; i++){
            for(int j=jungle_start_y; j<jungle_start_y+jungle_height; j++){
                this.jungle_list.add(new Vector2d(i, j));
            }
        }

        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                if( jungle_list.contains(new Vector2d(i,j))==false ){
                    this.field_list.add(new Vector2d(i,j));
                }
            }
        }

        spawnAnimals(start_animals);
        newPlantsOnField(start_field_grass);
        newPlantsOnJungle(start_jungle_grass);
    }

    public boolean canMoveTo(Vector2d position, Animal animal){
        int Px=animal.getPosition().getx();
        int Py=animal.getPosition().gety();
        int Dx=position.getx();
        int Dy=position.gety();
        MapDirection direction = animal.getDirection();
        if((Math.abs(Dx-Px)==1 || Px-Dx==this.width-1 )&& Dy==Py && direction==MapDirection.EAST){
            return true;
        }
        else if((Math.abs(Dx-Px)==1 || Dx-Px==this.width-1 )&& Dy==Py && direction==MapDirection.WEST){
            return true;
        }
        else if((Math.abs(Dy-Py)==1 || Py-Dy==this.height-1 )&& Dx==Px && direction==MapDirection.NORTH){
            return true;
        }
        else if((Math.abs(Dy-Py)==1 || Dy-Py==this.height-1 )&& Dx==Px && direction==MapDirection.SOUTH){
            return true;
        }
        else if(((Math.abs(Dy-Py)==1 || Dy-Py==this.height-1)&& (Math.abs(Dx-Px)==1 || Dx-Px==this.width-1 ))&& ((direction==MapDirection.NORTHEAST)||(direction==MapDirection.NORTHWEST)||(direction==MapDirection.SOUTHEAST)||(direction==MapDirection.SOUTHEAST))){
            return true;
        }
        return false;
    }

    public boolean place(Animal animal){
        animals_list.add(animal);
        if (animals_map.containsKey(animal.getPosition())){
            animals_map.get(animal.getPosition()).add(animal);
        }
        else{
            LinkedList<Animal> newlist = new LinkedList<Animal>();
            animals_map.put(animal.getPosition(), newlist);
            animals_map.get(animal.getPosition()).add(animal);
        }
        return true;
    }

    public boolean isOccupied(Vector2d position){
        if (animals_map.containsKey(position)){
            return true;
        }
        if (grass_list.containsKey(position)){
            return true;
        }
        return false;
    }

    public void moving(){
        for(int i = 0; i < animals_list.size(); i++) {
            Vector2d old_vector = animals_list.get(i).getPosition();
            Vector2d new_vector = animals_list.get(i).move(this.move_energy);

            for (int j = 0; j < animals_map.get(old_vector).size(); j++) {
                if (animals_map.get(old_vector).get(j).equals(animals_list.get(i))) {
                    animals_map.get(old_vector).remove(j);
                    if(animals_map.get(old_vector).isEmpty()){
                        animals_map.remove(old_vector);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
            if (animals_map.containsKey(new_vector)) {
                animals_map.get(new_vector).add(animals_list.get(i));
            } else {
                LinkedList<Animal> newlist = new LinkedList<Animal>();
                animals_map.put(new_vector, newlist);
                animals_map.get(new_vector).add(animals_list.get(i));
            }
        }
    }

    public void eating(){
        for (int i = 0; i < animals_list.size(); i++) {
            LinkedList<Animal> animals_at_vector=new LinkedList<Animal>();
            Vector2d eating_vector = animals_list.get(i).getPosition();
            animals_at_vector = animals_map.get(animals_list.get(i).getPosition());

            if(grass_list.containsKey(eating_vector)==true){
                int max_energy=0;
                int number_of_strongest_animals=0;

                for (int j=0; j<animals_at_vector.size();j++){
                    if (animals_at_vector.get(j).GetEnergy()>max_energy){
                        max_energy=animals_at_vector.get(j).GetEnergy();
                        number_of_strongest_animals=1;
                    }
                    else if(animals_at_vector.get(j).GetEnergy()==max_energy){
                        number_of_strongest_animals=number_of_strongest_animals+1;
                    }
                }

                for (int k=0; k<animals_at_vector.size();k++){
                    if (animals_at_vector.get(k).GetEnergy()==max_energy) {
                        animals_at_vector.get(k).changeEnergy(plant_energy / number_of_strongest_animals, this.max_energy);
                    }
                }
                grass_list.remove(eating_vector);
            }
        }
    }

    public void mating(){
        for (int i = 0; i < animals_map.size(); i++) {
            LinkedList<Animal> animals_at_vector=new LinkedList<Animal>();
            Vector2d mating_vector = animals_list.get(i).getPosition();
            animals_at_vector = animals_map.get(animals_list.get(i).getPosition());
            LinkedList <Animal> animals_able_to_mate=new LinkedList<>();
            
            for (int k=0; k<animals_at_vector.size();k++){
                if (animals_at_vector.get(k).GetEnergy()>=spawn_energy/2){
                    animals_able_to_mate.add(animals_at_vector.get(k));
                }
            }
            
            if(animals_able_to_mate.size()>=2){
                int fatherindex=0;
                for(int j=0;j<animals_able_to_mate.size();j++){
                    if(animals_able_to_mate.get(j).GetEnergy()>animals_able_to_mate.get(fatherindex).GetEnergy()){
                        fatherindex=j;
                    }
                }
                Animal father=animals_able_to_mate.get(fatherindex);
                animals_able_to_mate.remove(fatherindex);
                int motherindex=0;
                for(int j=0;j<animals_able_to_mate.size();j++){
                    if(animals_able_to_mate.get(j).GetEnergy()>animals_able_to_mate.get(motherindex).GetEnergy()){
                        motherindex=j;
                    }
                }
                Animal mother=animals_able_to_mate.get(motherindex);

                LinkedList<Vector2d> neighbour_vectors=new LinkedList<Vector2d>();
                LinkedList<Vector2d> neighbour_vectors_not_occupied =new LinkedList<Vector2d>();
                if((mating_vector.getx()+1<this.width)&&(mating_vector.gety()+1<this.height)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx()+1,mating_vector.gety()+1));
                    if(this.isOccupied(new Vector2d(mating_vector.getx()+1,mating_vector.gety()+1))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx()+1,mating_vector.gety()+1));
                    }
                }
                if((mating_vector.getx()+1<this.width)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx()+1,mating_vector.gety()));
                    if(this.isOccupied(new Vector2d(mating_vector.getx()+1,mating_vector.gety()))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx()+1,mating_vector.gety()));
                    }
                }
                if((mating_vector.getx()+1<this.width)&&(mating_vector.gety()-1>=0)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx()+1,mating_vector.gety()-1));
                    if(this.isOccupied(new Vector2d(mating_vector.getx()+1,mating_vector.gety()-1))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx()+1,mating_vector.gety()-1));
                    }
                }
                if((mating_vector.gety()-1>=0)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx(),mating_vector.gety()-1));
                    if(this.isOccupied(new Vector2d(mating_vector.getx(),mating_vector.gety()-1))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx(),mating_vector.gety()-1));
                    }
                }
                if((mating_vector.getx()-1>=0)&&(mating_vector.gety()-1>=0)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx()-1,mating_vector.gety()-1));
                    if(this.isOccupied(new Vector2d(mating_vector.getx()-1,mating_vector.gety()-1))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx()-1,mating_vector.gety()-1));
                    }
                }
                if((mating_vector.getx()-1>=0)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx()-1,mating_vector.gety()));
                    if(this.isOccupied(new Vector2d(mating_vector.getx()-1,mating_vector.gety()))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx()-1,mating_vector.gety()));
                    }
                }
                if((mating_vector.getx()-1>=0)&&(mating_vector.gety()+1<this.height)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx()-1,mating_vector.gety()+1));
                    if(this.isOccupied(new Vector2d(mating_vector.getx()-1,mating_vector.gety()+1))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx()-1,mating_vector.gety()+1));
                    }
                }
                if((mating_vector.gety()+1<this.height)){
                    neighbour_vectors.add(new Vector2d(mating_vector.getx(),mating_vector.gety()+1));
                    if(this.isOccupied(new Vector2d(mating_vector.getx(),mating_vector.gety()+1))==false){
                        neighbour_vectors_not_occupied.add(new Vector2d(mating_vector.getx(),mating_vector.gety()+1));
                    }
                }
                if (neighbour_vectors_not_occupied.size()!=0){
                    Collections.shuffle(neighbour_vectors_not_occupied);
                    Animal kid = new Animal(this, neighbour_vectors_not_occupied.get(0), ((mother.GetEnergy()/4)+father.GetEnergy()/4));
                    kid.GetGenes().InheritGenes(mother.GetGenes(), father.GetGenes());
                    this.place(kid);
                }
                else{
                    Collections.shuffle(neighbour_vectors);
                    Animal kid = new Animal(this, neighbour_vectors.get(0), ((mother.GetEnergy()/4)+father.GetEnergy()/4));
                    kid.GetGenes().InheritGenes(mother.GetGenes(), father.GetGenes());
                    this.place(kid);
                }
                mother.changeEnergy(-(mother.GetEnergy()/4), this.max_energy);
                mother.setNumber_of_kids(mother.getNumber_of_kids()+1);
                father.changeEnergy(-(father.GetEnergy()/4), this.max_energy);
                father.setNumber_of_kids(father.getNumber_of_kids()+1);
            }
        }
    }

    public void deleteDead(){
        for(int i = 0; i < animals_list.size(); i++) {
            if (animals_list.get(i).isDead() == true) {
                Vector2d dead_position = animals_list.get(i).getPosition();
                for (int j = 0; j < animals_map.get(dead_position).size(); j++) {
                    if (animals_map.get(dead_position).get(j).equals(animals_list.get(i))) {
                        animals_map.get(dead_position).remove(j);
                        dead_animals_list.add(animals_list.get(i));
                        animals_list.remove(i);
                        if (animals_map.get(dead_position).isEmpty()) {
                            animals_map.remove(dead_position);
                            i=i-1;
                            break;
                        }
                        else {
                            i=i-1;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void newPlantsOnField(int how_many){
        Collections.shuffle(field_list);
        int counter=0;
        for (int i=0; i<field_list.size();i++){
            if(this.isOccupied(field_list.get(i))==false){
                grass_list.put(field_list.get(i), new Grass(field_list.get(i)));
                counter=counter+1;
                if(counter==how_many){
                    break;
                }
            }
        }
    }

    public void newPlantsOnJungle(int how_many){
        Collections.shuffle(jungle_list);
        int counter=0;
        for (int i=0; i<jungle_list.size();i++){
            if(this.isOccupied(field_list.get(i))==false){
                grass_list.put(jungle_list.get(i), new Grass(jungle_list.get(i)));
                counter=counter+1;
                if(counter==how_many){
                    break;
                }
            }
        }
    }

    public void spawnAnimals(int how_many){
        LinkedList<Vector2d> all_fields=this.field_list;
        all_fields.addAll(jungle_list);
        Collections.shuffle(all_fields);
        int counter=0;
        for(int i=0; i<how_many; i++){
            if(this.isOccupied(all_fields.get(i))==false){
                this.place(new Animal(this,all_fields.get(i),this.getSpawn_energy()));
                counter=counter+1;
                if(counter==how_many){
                    break;
                }
            }
        }
    }
    
    public void run() {

        deleteDead();

        moving();

        eating();

        mating();

        newPlantsOnField(this.daily_grass_on_field);

        newPlantsOnJungle(this.daily_grass_on_jungle);

        this.days_passed+=1;
    }
    public int getSpawn_energy(){ return this.spawn_energy; }
    public int getMax_energy(){return this.max_energy;}
    public int getJungle_height(){return this.jungle_height;}
    public int getJungle_width(){return this.jungle_width;}
    public int getJungle_start_x(){return this.jungle_start_x;}
    public int getJungle_start_y(){return this.jungle_start_y;}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public int getDays_passed(){return this.days_passed;}
    public LinkedList<Animal> getAnimals_list(){return this.animals_list;}
    public LinkedHashMap<Vector2d, Grass> getGrass_list(){return this.grass_list;}
    public LinkedList<Animal> getDead_animals_list(){return this.dead_animals_list;}
    
}

