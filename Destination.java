import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Destination {

    private String title;
    int size;
    private JFrame frame;
    public Canvas canvas;

    public Destination(String title, int size){
        this.title=title;
        this.size=size;
        createDisplay();
    }

    public void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(size-200,size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas= new Canvas();
        canvas.setPreferredSize(new Dimension(size,size));
        canvas.setBackground(new Color(0,0,0)); //50,205,50
        frame.add(canvas);
        frame.pack();
    }
}
