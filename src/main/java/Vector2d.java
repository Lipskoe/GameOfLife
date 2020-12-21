import java.util.Objects;

public class Vector2d {
    private  int x;
    private  int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setx(int x){
        this.x=x;
    }
    public void sety(int y){
        this.y=y;
    }
    public int getx(){
        return this.x;
    }
    public int gety(){
        return this.y;
    }

    public String toString(){
        String s1=String.valueOf(this.x);
        String s2=String.valueOf(this.y);
        return "("+s1+","+s2+")";
    }

    Vector2d add(Vector2d other){
        return new Vector2d(other.x+ this.x, other.y+ this.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return that.x == this.x && that.y == this.y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }
}