package jd.demo.se.io.nio.buffer;

import jd.util.ArrUt;
import jd.util.ui.swing.ComponentUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.Buffer;

public class VisualDemoBuffer extends JPanel {

    private final Buffer buffer;
    private int btnx1,btny1,btnx2,btny2;
    private boolean pauseMode,enableNext ;

    public VisualDemoBuffer(Buffer buffer) {
        this(buffer,false);
    }

    public VisualDemoBuffer(Buffer buffer,boolean pauseMode) {
        this.buffer = buffer;
        this.pauseMode = pauseMode;
        init();
    }


    public void init(){
        btnx2 = this.getWidth() -10 ;
        btnx1 = btnx2 - 40 ;
        btny1 = 60 ;
        btny2 = btny1 + 40 ;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if(pauseMode && !enableNext){
                    int x = e.getX();
                    int y = e.getY();
                    if( x >= btnx1 && x <= btnx2 && y >= btny1 && y<=btny2){
                        enableNext = true ;
                    }
                }
            }
        });
    }
    public void display() {
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString(getBufferInfo(buffer),10,10);

        int position = buffer.position();

        int xStart =10,yStart=40,width=40,height=40;
        ArrUt.forEach(buffer.array(),(i,e)->{
            g2d.setColor(i<position ? Color.BLUE: Color.gray);
            g2d.fill3DRect(xStart+width*i, yStart, width, height, true);
            g2d.setColor( Color.WHITE);
            g2d.drawString(e==null?"":e.toString(),xStart+width*i+width/4, yStart+width/2);
        });

        if(pauseMode){
            g2d.setColor( Color.GREEN);
            g2d.fill3DRect(btnx1,btny1,btnx2-btnx1,btny2-btny1,false);
            g2d.setColor( Color.WHITE);
            g2d.drawString("Next",btnx1+10,btny1+10);
        }
    }

    public VisualDemoBuffer showOnFrame(String title, int width, int height){
        JFrame f = ComponentUtil.show(title,this,width,height);
        f.setAlwaysOnTop(true);
        return this ;
    }

    protected static String getBufferInfo(Buffer buffer){
        StringBuilder sb = new StringBuilder(buffer.getClass().getSimpleName())
                .append(": position=").append(buffer.position())
                .append(", limit=").append(buffer.limit())
                .append(", capacity=").append(buffer.capacity());
        return sb.toString();
    }


    protected VisualDemoBuffer run(long sleepMillis, Runnable... rs) throws InterruptedException {
        this.pauseMode = false;
        for(Runnable r : rs){
            r.run();
            this.repaint();
            Thread.sleep(sleepMillis);
        }
        return this;
    }

    protected VisualDemoBuffer run( Runnable... rs) throws InterruptedException {
        this.pauseMode = true;
        for (Runnable r : rs) {
            while (true) {
                synchronized (this) {
                    Thread.sleep(100);
                    if (this.enableNext) break;
                }
            }
            r.run();
            this.repaint();
        }
        this.pauseMode = false;
        return this;
    }
}


