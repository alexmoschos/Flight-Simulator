import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map extends JPanel {
    public Map(int width, int height,File input) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLUE);
        BufferedReader reader = null;
        Integer i = 0;
        Integer j = 0;
        try {
            reader = new BufferedReader(new FileReader(input));
            String text = null;

            while ((text = reader.readLine()) != null) {
                String[] values = text.split(",");
                for(String x : values){
                    //System.out.println(i.toString() + "," + j.toString());
                    drawRect(translate(Integer.parseInt(x)),j,i,16,16);
                    //canvas.setRGB(j, i, Integer.parseInt(x));
                    j+=16;
                }
                i+=16;
                j = 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

    }

    private BufferedImage canvas;
    public ArrayList<Plane> planes = new ArrayList<>();
    public ArrayList<Airport> airports = new ArrayList<Airport>();
    public BufferedImage planeImage;
    public Map(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLUE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
        for(Plane plane : planes) {
            AffineTransform at = new AffineTransform();
            at.translate(16*(plane.y)-plane.planeImage.getWidth()/2 + 8,16*plane.x-plane.planeImage.getHeight()/2 + 8);
            at.rotate(plane.orientation,plane.planeImage.getWidth()/2,plane.planeImage.getHeight()/2);
            g2.drawImage(plane.planeImage,at,null);
        }
        for(Airport air : airports){
            AffineTransform at = new AffineTransform();
            at.translate(16*air.getY()-air.getAirportImage().getWidth()/2 + 8 ,16*air.getX()-air.getAirportImage().getHeight()/2 + 8);
            g2.drawImage(air.getAirportImage(),at,null);
        }
    }

    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }
    private Color translate(Integer c){
        if(c == 0) return new Color(0,0,255);
        if(c <= 200) return new Color(60,179,113);
        if(c <= 400) return new Color(46,139,87);
        if(c <= 700) return new Color(34,139,34);
        if(c <= 1500) return new Color(222,184,135);
        if(c <= 3500) return new Color(205,133,63);
        return new Color(145,80 ,20);
    }
    public void drawRect(Color c, int x1, int y1, int width, int height) {
        int color = c.getRGB();
        // Implement rectangle drawing
        for (int x = x1; x < x1 + width; x++) {
            for (int y = y1; y < y1 + height; y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

}