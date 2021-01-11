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
                    try {
                        roads.get(connectionSelection).getConnectedRoads().add(roads.get(roads.size() - 1));
                    }catch (Exception mistake){
                        JOptionPane.showMessageDialog(null, "Can not find the connected Road!!","Mistake",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Message: " + mistake);
                    }
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

    public void openMap(String[] Ourroads) {  //This method is used to transform the string[] to roads on new map
        for(String line : Ourroads){     //get each road string and split them to information
            String[] line0=line.split("road_");
            String[] RoadName=line0[1].split(" Length:");
            String[] Length=RoadName[1].split(" Start X:");
            String[] X=Length[1].split(" Start Y:");
            String[] Y=X[1].split(" ");

            if(((Integer.parseInt(X[0])<10/scale) || (Integer.parseInt(Y[0])<10/scale))&& roads.size() == 0) {  //Check whether it is initial road or not
                if (Y[1].equals("Horizontal")) {
                    roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                            , Road.Orientation.HORIZONTAL));
                } else {
                    roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                            , Road.Orientation.VERTICAL));
                }
            } else{   //If it is not initial Road we must select the orientation and connected road fot it
                String[] orientationOptions = {"Horizontal", "Vertical"};
                int orientationSelection = JOptionPane.showOptionDialog(null, "Choose Loading Road Orientation:",
                        "Reset Road Orientation Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, orientationOptions, roads);
                switch (orientationSelection) {
                    case 0 -> roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                            , Road.Orientation.HORIZONTAL));
                    case 1 -> roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                            , Road.Orientation.VERTICAL));
                }

                String[] connectionOptions = new String[30];  //Connected Road option
                for (int i = 0; i < connectionOptions.length; i++) {
                    connectionOptions[i] = Integer.toString(i);
                }
                int connectionSelection = JOptionPane.showOptionDialog(null, "Choose Connecting Model.Road:",
                        "Connections Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, connectionOptions, connectionOptions[0]);

                try {   //If can not set connected road, it will show the dialog and jump to next one
                    roads.get(connectionSelection).getConnectedRoads().add(roads.get(roads.size() - 1));
                } catch (Exception mistake){
                    JOptionPane.showMessageDialog(null, "Can not find the connected Road!!","Mistake",JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Message: " + mistake);
                }
            }
            repaint();
        }
        for (Road road : roads) {   //Add the light for loaded road
            lights.add(new TrafficLight("1", road));}
    }

    public void deleteRoad(){
        if(roads.size()>0) {  //There must be road to delete
            Road roadToDelete = roads.get(roads.size() - 1);  //Select the latest road the user add
            for (TrafficLight The: roadToDelete.getLightsOnRoad()){
                System.out.println(roadToDelete.id+" has "+roadToDelete.getLightsOnRoad().size()+" to Delete");
            }
            if(roadToDelete.getLightsOnRoad().size()>0&&lights.size()>0){
                for(int i=1;i<=roadToDelete.getLightsOnRoad().size();i++){   //Delete all the light on the selected road
                    if(lights.size()>=1){lights.remove(lights.size()-1);
                        System.out.println(lights.size()+" lights remain");}
                }
            }
            roads.remove(roadToDelete);  //delete the road
            repaint();
        }
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
