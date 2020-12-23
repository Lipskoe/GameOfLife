import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class SimulationEngine extends Thread implements ActionListener {

    public GrassField map;
    public WorldPanel world_panel;
    public StatsPanel stats_panel;
    boolean paused;
    boolean running;
    int timeout;
    int days_to_report;

    public SimulationEngine(int width, int height, double jungle_ratio, int howmany, int spawnenergy, int plantenergy, int moveenergy, int maxenergy, int timeout, String title, int start_field_grass, int start_jungle_grass, int daily_grass_on_field, int daily_grass_on_jungle, int days_to_report){

        this.timeout=timeout;
        this.paused=false;
        this.map = new GrassField(width, height, jungle_ratio,howmany, spawnenergy,plantenergy,moveenergy, maxenergy, start_field_grass, start_jungle_grass, daily_grass_on_field, daily_grass_on_jungle);
        int square;
        this.days_to_report=days_to_report;

        if((height>0)&&(height<=20)){
            square=40;
        }
        else if((height>20)&&(height<=40)){
            square=20;
        }
        else if((height>40)&&(height<=80)){
            square=10;
        }
        else{
            square=5;
        }

        int frame_height=420;
        if(map.getHeight()*square>=420){
            frame_height=map.getHeight()*square;
        }

        Frame frame = new Frame(title, square*map.getWidth()+200, frame_height);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                running=false;
            }
        });

        world_panel=new WorldPanel(map, square);
        world_panel.setBounds(0,0,map.getWidth()*square,map.getHeight()*square);
        stats_panel=new StatsPanel(map, title);
        stats_panel.setBounds(0,0,200,frame_height);
        stats_panel.pause_button.addActionListener(this);
        frame.add(world_panel);
        frame.add(stats_panel);
        world_panel.setLocation(0,0);
        stats_panel.setLocation(map.getWidth()*square,0);
    }

    public void run(){
        running=true;
        while (running) {
            if(paused==false) {
                map.run();
                world_panel.repaint();
                stats_panel.UpdateStats();
                if(this.days_to_report>=this.map.getDays_passed()){
                    stats_panel.reportToFile();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(this.timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    TimeUnit.MILLISECONDS.sleep(this.timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (paused==true){
            paused=false;
        }
        else {
            paused=true;
        }
    }
}