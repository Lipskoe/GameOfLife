import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    public Frame(String title, int width, int height){
        super(title);
        this.setSize(width,height);
        this.setLocation(100,100);
        this.setVisible(true);
        Container mainContainer=this.getContentPane();
        mainContainer.setBackground(Color.white);
        this.setLayout(null);
    }
}
