import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel{
    private JButton loadWorld;
    private JButton loadAirports;
    public Sidebar() {
        loadWorld = new JButton("Load World");
        loadAirports = new JButton("Load Airports");
        setBackground(Color.WHITE);
        add(loadAirports);
        add(loadWorld);
    }

    public Dimension getPreferredSize() {
        return new Dimension(128,128);
    }
}
