import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class Sidebar extends JPanel{
    private JButton loadWorld;
    private JButton loadAirports;
    private JLabel pane;
    private String oldText;
    public Sidebar(Map map) {
        oldText = "";
        pane = new JLabel();
//        addText("HELLO WORLD");
//        addText("HELLO WORLD");
//        addText("HELLO WORLD");


        setBackground(Color.WHITE);
        add(pane);
    }
    public synchronized void addText(String text) {
        pane.setText("<html>" + oldText + "<br>" + text + "</html>");
        oldText += "<br>" + text;
    }
    public Dimension getPreferredSize() {
        return new Dimension(128,128);
    }
}
