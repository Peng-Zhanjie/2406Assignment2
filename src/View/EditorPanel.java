package View;

import Model.Road;
import Model.TrafficLight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EditorPanel extends JPanel {

    public ArrayList<Road> roads;
    public ArrayList<TrafficLight> lights;
    private int scale;
    public JPanel infoRoadPanel;


    public void newMap() {
        roads = new ArrayList<>();
        lights = new ArrayList<>();
        infoRoadPanel = new JPanel();
        infoRoadPanel.setLayout(new GridLayout(1, 0));
        JLabel XLabel = new JLabel(" Road x position: ");
        infoRoadPanel.add(XLabel);
        JLabel YLabel = new JLabel("   Road y position: ");
        infoRoadPanel.add(YLabel);
        add(infoRoadPanel, BorderLayout.SOUTH);


        MouseAdapter mouseLis = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int xValue = e.getX() / scale;
                int yValue = e.getY() / scale;
                XLabel.setText(" Road x position: " + xValue);
                YLabel.setText("   Road y position: " + yValue);
                if (roads.size() == 0) {
                    if (e.getY() < 10) {
                        roads.add(new Road(Integer.toString(roads.size()), 1, 50, new int[]{xValue, 0}
                                , Road.Orientation.VERTICAL));
                        System.out.print("New road added\n");
                    } else if (e.getX() < 10) {
                        roads.add(new Road(Integer.toString(roads.size()), 1, 50, new int[]{0, yValue}
                                , Road.Orientation.HORIZONTAL));
                        System.out.print("New road \n");
                    }

                } else {
                    String[] orientationOptions = {"Horizontal", "Vertical"};
                    int orientationSelection = JOptionPane.showOptionDialog(null, "Choose Orientation:",
                            "Orientation Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, orientationOptions, roads);
                    switch (orientationSelection) {
                        case 0:
                            roads.add(new Road(Integer.toString(roads.size()), 1, 50, new int[]{xValue,
                                    yValue}, Road.Orientation.HORIZONTAL));
                            break;
                        case 1:
                            roads.add(new Road(Integer.toString(roads.size()), 1, 50, new int[]{xValue,
                                    yValue}, Road.Orientation.VERTICAL));
                    }
                    String[] connectionOptions = new String[30];
                    for (int i = 0; i < connectionOptions.length; i++) {
                        connectionOptions[i] = Integer.toString(i);
                    }
                    int connectionSelection = JOptionPane.showOptionDialog(null, "Choose Connecting Model.Road:",
                            "Connections Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, connectionOptions, connectionOptions[0]);
                    roads.get(connectionSelection).getConnectedRoads().add(roads.get(roads.size() - 1));
                }
                    lights.add(new TrafficLight("1", roads.get(roads.size() - 1)));
                repaint();
            }
        };
        addMouseListener(mouseLis);

    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public ArrayList<TrafficLight> getLights() {
        return lights;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (roads.size() == 0) {
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, this.getWidth(), 10);
            g.fillRect(0, 0, 10, this.getHeight());
        }

        if (!roads.isEmpty()) {
            for (Road road : roads
            ) {
                road.draw(g, scale);
                g.setColor(Color.YELLOW);
                int[] location = road.getEndLocation();
                int x = location[0];
                int y = location[1];
                int width = 10 * scale;
                int height = road.getWidth() * scale;
                g.fillRect(x,y,width,height);
            }
        }

        if (!lights.isEmpty()) {
            for (TrafficLight light : lights
            ) {
                light.draw(g, scale);
            }
        }
    }

}
