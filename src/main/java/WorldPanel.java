import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class WorldPanel extends JPanel {

    public GrassField map;
    public int square_size;


    public WorldPanel(GrassField map,int square_size) {
        this.map = map;
        this.setLayout(null);
        this.square_size=square_size;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        clean(g);
        jungle(g);
        grid(g);
        grass(g);
        animal(g);
    }

    private void grid(Graphics g){
        g.setColor(Color.DARK_GRAY);
        int longer_side;
        if(map.getHeight()>=map.getWidth()){
            longer_side=map.getHeight();
        }
        else{
            longer_side=map.getWidth();
        }

        for(int i = 0; i < longer_side; i++){
            g.drawLine(0, i*square_size, square_size*map.getWidth(), i*square_size);
            g.drawLine(i*square_size, 0, i*square_size, square_size* map.getHeight());
        }
    }

    private void clean(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(0,0, map.getWidth()*square_size, map.getHeight()*square_size );
    }

    private void jungle(Graphics g){
        g.setColor(new Color(0,153,0));
        g.fillRect(map.getJungle_start_x()*square_size,map.getJungle_start_y()*square_size, map.getJungle_width()*square_size, map.getJungle_height()*square_size );
    }

    private void animal(Graphics g){
        g.setColor(Color.GRAY);
        for(int i = 0; i < map.getAnimals_list().size(); i++){
            switch ((int) map.getAnimals_list().get(i).GetEnergy()/(map.getMax_energy()/4)) {
                case 0:
                    g.setColor(new Color(170,120,0));
                case 1:
                    g.setColor(new Color(140,90,0));
                    break;
                case 2:
                    g.setColor(new Color(110,60,0));
                    break;
                case 3:
                    g.setColor(new Color(80,30,0));
                    break;
                case 4:
                    g.setColor(new Color(50,0,0));
                    break;
                default:
                    break;
            }
            g.fillRect(map.getAnimals_list().get(i).getPosition().getx()*square_size,map.getAnimals_list().get(i).getPosition().gety()*square_size , square_size, square_size);
        }
    }

    private void grass(Graphics g){
        g.setColor(new Color(0,102,0));
        LinkedList<Vector2d> listKeys = new LinkedList<Vector2d>( map.getGrass_list().keySet() );
        for(int i = 0; i < listKeys.size(); i++) {
            g.fillRect(listKeys.get(i).getx() * square_size, listKeys.get(i).gety() * square_size, square_size, square_size);
        }
    }
}