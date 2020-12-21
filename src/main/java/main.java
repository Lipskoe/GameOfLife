import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) {

        ReadJSONFile json = new ReadJSONFile();
        String json_content= json.ReadJSONFile("parameters.json");
        Gson gson = new Gson();
        Config config = gson.fromJson(json_content, Config.class);
        int engines_number=config.engines;
        if (engines_number==2) {
            try {
                SimulationEngine engine = new SimulationEngine(config.width, config.height, config.jungle_ratio, config.animals_on_start, config.start_energy, config.plant_energy, config.move_energy, config.max_energy, config.timeout_in_ms, "Game of Life", config.start_field_grass, config.start_jungle_grass, config.daily_grass_on_field, config.daily_grass_on_jungle);
                engine.start();
                SimulationEngine engine2 = new SimulationEngine(config.width, config.height, config.jungle_ratio, config.animals_on_start, config.start_energy, config.plant_energy, config.move_energy, config.max_energy, config.timeout_in_ms, "Game of Life", config.start_field_grass, config.start_jungle_grass, config.daily_grass_on_field, config.daily_grass_on_jungle);
                engine2.start();
                while (true) {
                    if ((engine.isAlive() || engine2.isAlive()) == false) {
                        System.exit(0);
                    } else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.toString());
            }
        }
        else{
            try {
                SimulationEngine engine = new SimulationEngine(config.width, config.height, config.jungle_ratio, config.animals_on_start, config.start_energy, config.plant_energy, config.move_energy, config.max_energy, config.timeout_in_ms, "Game of Life", config.start_field_grass, config.start_jungle_grass, config.daily_grass_on_field, config.daily_grass_on_jungle);
                engine.start();
                while (true) {
                    if (engine.isAlive()==false){
                        System.exit(0);
                    } else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}

