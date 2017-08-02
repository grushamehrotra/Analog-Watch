import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Watch implements Runnable {

    private String title;
    private int size;
    private Destination destination;
    private Thread thread;
    private BufferStrategy buffer ;
    private Graphics2D g;

    public static void main(String[] args){
        Watch set = new Watch("Watch",300);
        set.start();
    }

        public Watch(String title, int size){
            this.title = title;
            this.size = size;
        }

        public void init(){
            destination = new Destination(title, size);
        }

        public void draw(){
            buffer = destination.canvas.getBufferStrategy();
            if(buffer == null){
                destination.canvas.createBufferStrategy(3);
                return;
            }

            int center = size/2;
            g =(Graphics2D)buffer.getDrawGraphics();
            g.clearRect(0 , 0, size,size);

            //draw
            g.setColor(Color.red);
            g.fillOval( 10, 10, size-20, size-20);
            g.setColor(Color.white);
            g.fillOval( 20, 20, size-40, size-40);


            // draw divisions
            int angleX,angleY;
            int radius;
            double position;
            double time = System.currentTimeMillis();

            for(int i= 1 ; i<= 60 ; i++){
                position = i/ 60.0 * Math.PI*2;
                radius = center - 20 ;  //20
                angleX = (int)((center) + (Math.sin(position)*radius));
                angleY = (int)((center) - (Math.cos(position)*radius));
                if(i==15 || i == 30 || i == 45 || i == 60){
                    radius = center - 60 ;
                }
                else{
                    radius = center - 30;
                }
                int angleW = (int)((center) + (Math.sin(position)*radius));
                int angleZ = (int)((center) - (Math.cos(position)*radius));
                g.setColor(Color.black);
                g.drawLine(angleW, angleZ, angleX, angleY);
            }

            //hour hand
            radius = center - 70 ;
            time = System.currentTimeMillis()/ (60.0*12*60*1000.0)* Math.PI*2;
            angleX = (int)((center) +( Math.sin(time) * radius));
            angleY = (int)((center) - (Math.cos(time)  * radius));
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine(center, center, angleX, angleY);
            g.setStroke(new BasicStroke(0));

            //minute hand
            radius = center - 40 ;
            time = System.currentTimeMillis()/ (60.0 * 60 *1000.0)* Math.PI*2;
            angleX = (int)((center) +( Math.sin(time) * radius));
            angleY = (int)((center) - (Math.cos(time)  * radius));
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine(center, center, angleX, angleY);
            g.setStroke(new BasicStroke(0));

            // second hand;
            radius = center - 50 ;
            time = System.currentTimeMillis()/ (60.0 * 1000.0)* Math.PI*2;
            angleX = (int)((center)+( Math.sin(time)*radius));
            angleY = (int)((center)-(Math.cos(time)*radius));
            g.setColor(Color.red);
            g.drawLine(center, center, angleX, angleY);

            //center oval
            g.setColor(Color.BLACK);
            g.fillOval(center-5, center-5, 12, 12);

            //end
            buffer.show();
            g.dispose();
        }

        public synchronized void start(){
            thread = new Thread(this);
            thread.start();
        }

        public synchronized void stop(){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            init();
            while(true){
                draw();
            }
        }
    }
