public class Config {
    public int engines;
    public int width;
    public int height;
    public float jungle_ratio;
    public int animals_on_start;
    public int start_energy;
    public int plant_energy;
    public int move_energy;
    public int max_energy;
    public int timeout_in_ms;
    public int start_field_grass;
    public int start_jungle_grass;
    public int daily_grass_on_field;
    public int daily_grass_on_jungle;
    public int days_to_report;

    public Config(int engines, int width,int height,float jungle_ratio,int animals_on_start,int start_energy,int plant_energy,int move_energy,int max_energy,int timeout_in_ms, int start_field_grass, int start_jungle_grass, int daily_grass_on_field, int daily_grass_on_jungle, int days_to_report){
        this.engines=engines;
        this.width=width;
        this.height=height;
        this.jungle_ratio=jungle_ratio;
        this.animals_on_start=animals_on_start;
        this.start_energy=start_energy;
        this.plant_energy= plant_energy;
        this.move_energy=move_energy;
        this.max_energy=max_energy;
        this.timeout_in_ms=timeout_in_ms;
        this.start_field_grass=start_field_grass;
        this.start_jungle_grass=start_jungle_grass;
        this.daily_grass_on_field=daily_grass_on_field;
        this.daily_grass_on_jungle=daily_grass_on_jungle;
        this.days_to_report=days_to_report;
    }
}
