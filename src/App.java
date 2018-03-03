import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class App extends JFrame {
    private JPanel panel1;
    private Map mapPanel;
    private JLabel topLabel;
    private JPanel sideBar;

    public static void main(String[] args) {
        App frame = new App();
        frame.setVisible(true);
        Map map = frame.getMap();
        File airportFile = new File("input/airports_default.txt");
        BufferedReader reader = null;
        BufferedImage airportImage = null;
        try {
            reader = new BufferedReader(new FileReader(airportFile));
            String text = null;

            while ((text = reader.readLine()) != null) {
                String[] values = text.split(",");
                int id = Integer.parseInt(values[0]);
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);
                String name = values[3];
                int orientation = Integer.parseInt(values[4]);
                int type = Integer.parseInt(values[5]);
                boolean isOpen;
                if (Integer.parseInt(values[6]) == 0) isOpen = false;
                else isOpen = true;
                if (airportImage == null)
                    airportImage = ImageIO.read(new File("icons/airport.png"));

                Airport air = new Airport(id, name, Airport.AirportCategory.values()[type - 1], isOpen, x, y, airportImage, Airport.Direction.values()[orientation - 1]);
                map.airports.add(air);

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
                e.printStackTrace();
            }
        }

        File planeFile = new File("input/flights_default.txt");
        reader = null;
        try {
            reader = new BufferedReader(new FileReader(planeFile));
            String text = null;

            while ((text = reader.readLine()) != null) {
                String[] values = text.split(",");
                int id = Integer.parseInt(values[0]);
                int time = Integer.parseInt(values[1]);
                int startAirport = Integer.parseInt(values[2]);
                int endAirport = Integer.parseInt(values[3]);
                String flightName = values[4];
                int type = Integer.parseInt(values[5]);
                int speed = Integer.parseInt(values[6]);
                int height = Integer.parseInt(values[7]);
                int fuel = Integer.parseInt(values[8]);
                Plane n = null;
                switch (type) {
                    case 1:
                        n = new SingleEngine(id, time, startAirport, endAirport, flightName, speed, height, fuel);
                        break;
                    case 2:
                        n = new TurboProp(id, time, startAirport, endAirport, flightName, speed, height, fuel);
                        break;
                    case 3:
                        n = new Jet(id, time, startAirport, endAirport, flightName, speed, height, fuel);
                        break;
                    default:
                        System.out.println("Couldn't understand the plane type");
                }
                //Airport air = airportHashMap.get(startAirport);
                //n.x = air.getX();
                //n.y = air.getY();
                //n.orientation = air.getDirection().ordinal();
                if (n.verify(map.airports))
                    map.planes.add(n);
                else
                    System.out.println("not valid plane\n");
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
                e.printStackTrace();
            }
        }
        for (Plane plane : map.planes) {
            plane.calculatePath(map.airports);
        }
//      while (true)
//            map.repaint();
//

        for (Plane plane : map.planes) {
            plane.startTimer(map);
        }

    }

    public Map getMap() {
        return mapPanel;
    }

    public App() {
        $$$setupUI$$$();
        setTitle("MediaLab flight simulation");
        setSize(1100, 522);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel1);
        topLabel.setText("Simulated Time: 0 ");

    }

    private void createUIComponents() {
        mapPanel = new Map(960, 480, new File("input/world_default.txt"));
        topLabel = new JLabel("Label");
        sideBar = new Sidebar();
        //sideBar.setSize(420, 420);
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        topLabel.setText("Label");
        panel1.add(topLabel, BorderLayout.NORTH);
        panel1.add(sideBar, BorderLayout.EAST);
        panel1.add(mapPanel, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
