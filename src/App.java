import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.TimerTask;


public class App extends JFrame {
    private JPanel panel1;
    private Map mapPanel;
    private JLabel topLabel;
    private Sidebar sideBar;
    private JMenuBar menubar;
    private String airportFile;
    private String mapFile;
    private String flightFile;

    public static void main(String[] args) {
        App frame = new App();
        frame.createMenuBar();

        frame.setVisible(true);
    }

    public void start() {
        App frame = this;
        Map map = frame.getMap();
        System.out.println("Hello zzz");
        File airportFile = new File(this.airportFile);
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
        sideBar.addText("Airports loaded");
        File planeFile = new File(flightFile);
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
            }
        } catch (FileNotFoundException e) {
            System.out.println("Flight file not found");
            e.printStackTrace();
            System.exit(1);
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
        sideBar.addText("Flights loaded");


        for (Plane plane : map.planes) {
            plane.calculatePath(map.airports);
        }

        for (Plane plane : map.planes) {
            plane.startTimer(map, (long) (plane.time / 5.0 * 1000), (long) (10.0 / plane.getStartSpeed() * 60 / 5 * 1000), sideBar);
        }
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                updateLabel();
            }
        }, 0, 200);
        while (!map.change) {
            //System.out.println("hello world");
            for (Plane plane : map.planes) {
                for (Plane secondPlane : map.planes) {
                    if (plane != secondPlane) {
                        if (plane.x == secondPlane.x && plane.y == secondPlane.y && Math.abs(plane.currHeight - secondPlane.currHeight) < 500) {
                            //System.out.println("Let me know");
                            plane.crash(map, sideBar);
                            secondPlane.crash(map, sideBar);
                            //sideBar.addText("Collision" + plane.id + " " + secondPlane.id);
                            //frame.updateLabel();
                        }
                    }
                }
            }
        }
        t.cancel();

    }

    public Map getMap() {
        return mapPanel;
    }

    public App() {
        airportFile = "input/airports_default.txt";
        mapFile = "input/world_default.txt";
        flightFile = "input/flights_default.txt";
        $$$setupUI$$$();
        setTitle("MediaLab flight simulation");
        setSize(1100, 522);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(menubar);
        add(panel1);
        updateLabel();

    }

    long start = System.currentTimeMillis();


    //updateLabel will update the info for the top bar
    public void updateLabel() {
        String result = "Simulated Time: ";
        DecimalFormat df = new DecimalFormat("0.00");
        result += df.format((System.currentTimeMillis() - start) / (60 * 1000F));
        result += "              ";
        result += "Total Aircrafts: ";
        result += Integer.toString(mapPanel.planes.size());
        result += "              ";
        result += "Total Landed: ";
        result += Integer.toString(mapPanel.landed.get());
        result += "              ";
        result += "Total Crashes: ";
        result += Integer.toString(mapPanel.crashed.get());
        topLabel.setText(result);
    }
    //createMenuBar will create the right listeners for the buttons at the top
    private void createMenuBar() {
        JMenu file = new JMenu("Game");
        App a = this;
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mapPanel.updateMap(960, 480, new File(mapFile));
                // System.out.println("Hello world");
                sideBar.addText("Simulation starting");
                //start it in a new thread to avoid delays in UI Thread
                Thread t = new Thread() {
                    public void run() {
                        a.start();
                    }
                };
                t.start();
            }
        });

        JMenuItem stop = new JMenuItem("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sideBar.addText("Simulation stopped");
                cancelTimers();
            }
        });
        // a.start();
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String id = JOptionPane.showInputDialog("Which Map ID should I load?");
                airportFile = "input/airports_" + id + ".txt";
                mapFile = "input/world_" + id + ".txt";
                flightFile = "input/flights_" + id + ".txt";
                mapPanel.updateMap(960, 480, new File(mapFile));
                sideBar.addText("Loaded " + id);

            }
        });


        JMenuItem end = new JMenuItem("Exit");
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sideBar.addText("Bye");
                System.exit(0);
            }
        });
        file.add(start);
        file.add(stop);
        file.add(load);
        file.add(end);
        menubar.add(file);

        JMenu sim = new JMenu("Simulation");
        JMenuItem airports = new JMenuItem("Airports");
        airports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String result = "";
                for (Airport air : mapPanel.airports) {
                    result += Integer.toString(air.getId()) + " ";
                    result += air.getName() + " ";
                    result += air.getCategory().ordinal() + " ";
                    result += air.getDirection().toString() + " ";
                    result += air.isOpen();
                    result += "\n";
                }
                JOptionPane.showMessageDialog(null, result);
            }
        });
        JMenuItem aircrafts = new JMenuItem("Aircrafts");
        aircrafts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println("Hello");
                String result = "";
                for (Plane plane : mapPanel.planes) {
                    if (!plane.crashed) {
                        Airport s = mapPanel.airports.get(plane.startAirport - 1);
                        Airport e = mapPanel.airports.get(plane.endAirport - 1);
                        result += Integer.toString(plane.id) + " from ";
                        result += s.getName() + " to ";
                        result += e.getName() + " ";
                        result += plane.fuel + " ";
                        result += plane.speed;
                        result += "\n";
                    }
                }
                JOptionPane.showMessageDialog(null, result);
            }
        });
        JMenuItem flights = new JMenuItem("Flights");
        flights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println("Hello");
                String result = "";
                for (Plane plane : mapPanel.planes) {
                    if (!plane.crashed) {
                        result += "NOT CRASHED ";
                    } else {
                        result += "CRASHED ";
                    }
                    Airport s = mapPanel.airports.get(plane.startAirport - 1);
                    Airport e = mapPanel.airports.get(plane.endAirport - 1);
                    result += Integer.toString(plane.id) + " from ";
                    result += s.getName() + " to ";
                    result += e.getName() + " ";
                    result += plane.fuel + " ";
                    result += plane.speed;
                    result += "\n";

                }
                JOptionPane.showMessageDialog(null, result);
            }
        });
        sim.add(airports);
        sim.add(aircrafts);
        sim.add(flights);
        menubar.add(sim);
    }

    private void cancelTimers() {
        Map map = getMap();
        for (Plane plane : map.planes) {
            plane.timer.cancel();
        }
    }


    private void createUIComponents() {
        mapPanel = new Map(960, 480, new File(mapFile));
        topLabel = new JLabel("Label");
        sideBar = new Sidebar(mapPanel);
        menubar = new JMenuBar();
        //createMenuBar();
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
