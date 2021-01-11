import Model.OpenFile;
import Model.SaveFile;
import Model.TrafficLight;
import View.EditorPanel;
import View.SimulationPanel;
import Model.Road;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import java.lang.IndexOutOfBoundsException;

public class Main {

    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 1024;
    private static SimulationPanel simulationPanel = new SimulationPanel();
    private static EditorPanel editorPanel = new EditorPanel();
    private static final int SCALE = 8;

    public static void main(String[] args) {
        // Simulation Window setup:
        JFrame mainWindow = new JFrame("Traffic Simulator");
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        //Status Bar
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 0));
        bottomPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        JLabel modeLabel = new JLabel("Mode: ");
        bottomPanel.add(modeLabel);
        JLabel statusLabel = new JLabel("Status: ");
        bottomPanel.add(statusLabel);
        mainWindow.add(bottomPanel, BorderLayout.SOUTH);

        //Menu bar:
        JMenuBar menuBar = new JMenuBar();
        mainWindow.add(menuBar, BorderLayout.NORTH);

        //Editor Menu:
        JMenu editMenu = new JMenu("City Editor");
        MenuListener cityLis = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                modeLabel.setText("Mode: Editor");
                mainWindow.repaint();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }


        };
        editMenu.addMenuListener(cityLis);
        menuBar.add(editMenu);

        JMenuItem newMapItem = new JMenuItem("New");
        newMapItem.addActionListener(e -> {
            simulationPanel.setVisible(false);
            mainWindow.remove(editorPanel);
            editorPanel = new EditorPanel();
            editorPanel.newMap();
            editorPanel.setScale(SCALE);
            mainWindow.add(editorPanel);
            editorPanel.setVisible(true);
            statusLabel.setText("Status: New Map");
            mainWindow.validate();
            mainWindow.repaint();
        });
        editMenu.add(newMapItem);

        JMenuItem openMapItem = new JMenuItem("Open");  //Get a string from txt
        openMapItem.addActionListener(e -> {
            simulationPanel.setVisible(false);
            mainWindow.remove(editorPanel);    //Create a new map to load
            editorPanel = new EditorPanel();
            editorPanel.newMap();
            editorPanel.setScale(SCALE);
            mainWindow.add(editorPanel);
            editorPanel.setVisible(true);
            statusLabel.setText("Status: Loaded Map");
            mainWindow.validate();
            mainWindow.repaint();


            OpenFile file1=new OpenFile();
            byte[] item=file1.getItem();    //Added function, open file in the method
            String textWeOpen=new String(item, StandardCharsets.UTF_8);
            System.out.println(textWeOpen);
            String[] Ourroads=textWeOpen.split("\n");   //Get individual road information
            for(String line : Ourroads){

                String[] line0=line.split("road_");
                String[] RoadName=line0[1].split(" Length:");
                String[] Length=RoadName[1].split(" Start X:");
                String[] X=Length[1].split(" Start Y:");
                String[] Y=X[1].split(" ");
                System.out.println(Y[1]);


                if(((Integer.parseInt(X[0])<10/SCALE) || (Integer.parseInt(Y[0])<10/SCALE))&& editorPanel.roads.size() == 0) {  //Check whether it is initial road or not
                    if (Y[1].equals("Horizontal")) {
                        editorPanel.roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                                , Road.Orientation.HORIZONTAL));
                    } else {
                        editorPanel.roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                                , Road.Orientation.VERTICAL));
                    }
                } else{   //If it is not initial Road we must select the orientation and connected road fot it
                    String[] orientationOptions = {"Horizontal", "Vertical"};
                    int orientationSelection = JOptionPane.showOptionDialog(null, "Choose Loading Road Orientation:",
                            "Reset Road Orientation Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, orientationOptions, editorPanel.roads);
                    switch (orientationSelection) {  //As same as the EditorPanel
                        case 0 -> editorPanel.roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
                                , Road.Orientation.HORIZONTAL));
                        case 1 -> editorPanel.roads.add(new Road(RoadName[0], 1, Integer.parseInt(Length[0]), new int[]{Integer.parseInt(X[0]), Integer.parseInt(Y[0])}
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
                        editorPanel.roads.get(connectionSelection).getConnectedRoads().add(editorPanel.roads.get(editorPanel.roads.size() - 1));
                    } catch (Exception mistake){
                        JOptionPane.showMessageDialog(null, "Can not find the connected Road!!","Mistake",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Message: " + mistake);
                    }
                }
                editorPanel.repaint();
            }
            for (Road road : editorPanel.roads) {   //Add the light for loaded road
                editorPanel.lights.add(new TrafficLight("1", road));}
        });
        editMenu.add(openMapItem);

        JMenuItem saveMapItem = new JMenuItem("Save");
        saveMapItem.addActionListener(e -> {
            SaveFile file2=new SaveFile();    //Added function, Save the roads
            String textToSave="";
            for(Road road : editorPanel.roads) {
                if (road.orientation == Road.Orientation.HORIZONTAL) {
                    textToSave = textToSave + road.id + " Length:" + road.length + " Start X:" + road.startLocation[0] + " Start Y:" + road.startLocation[1] + " Horizontal" + "\n";
                } else{
                    textToSave = textToSave + road.id + " Length:" + road.length + " Start X:" + road.startLocation[0] + " Start Y:" + road.startLocation[1] + " Vertical" + "\n";
                }
            }
            textToSave = textToSave.substring(0, textToSave.length() - 1);
            System.out.println(textToSave);
            file2.saveItem(textToSave);
        });
        editMenu.add(saveMapItem);

        JMenuItem DeleteRoadItem = new JMenuItem("Delete");
        DeleteRoadItem.addActionListener(e -> {
            editorPanel.deleteRoad();
        });
        editMenu.add(DeleteRoadItem);

        JMenuItem exitProgramItem = new JMenuItem("Exit");
        exitProgramItem.addActionListener(e -> System.exit(0));
        editMenu.add(exitProgramItem);

        //Simulation Menu:
        JMenu simMenu = new JMenu("Simulation");
        MenuListener simLis = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                modeLabel.setText("Mode: Simulation");
                mainWindow.repaint();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        };
        simMenu.addMenuListener(simLis);


        JMenuItem loadSimItem = new JMenuItem("Load Map");
        simMenu.add(loadSimItem);

        JMenuItem spawnItem = new JMenuItem("Add Vehicles");
        spawnItem.setEnabled(false);
        simMenu.add(spawnItem);

        JMenuItem startSimItem = new JMenuItem("Start");
        startSimItem.setEnabled(false);
        startSimItem.addActionListener(e -> {
            simulationPanel.simulate();
            statusLabel.setText("Status: Simulation Started");
            simulationPanel.setStopSim(false);
            mainWindow.validate();
            mainWindow.repaint();
        });
        simMenu.add(startSimItem);

        spawnItem.addActionListener(e -> {
            String spawnInput = JOptionPane.showInputDialog("Total number of Vehicles to spawn:");
            int spawns = Integer.parseInt(spawnInput);
            simulationPanel.setVehicleSpawn(spawns);
            String spawnRateInput = JOptionPane.showInputDialog("Number of Simulation tics between spawns:");
            int spawnRate = Integer.parseInt(spawnRateInput);
            simulationPanel.setVehicleSpawnRate(spawnRate);
        });

        JMenuItem stopSimItem = new JMenuItem("Stop");
        stopSimItem.setEnabled(false);
        stopSimItem.addActionListener(e -> {
            simulationPanel.setStopSim(true);
            statusLabel.setText("Status: Simulation Stopped");
            mainWindow.validate();
            mainWindow.repaint();
        });
        simMenu.add(stopSimItem);

        loadSimItem.addActionListener(e -> {
            statusLabel.setText("Status: Map Loaded!");
            editorPanel.setVisible(false);
            simulationPanel = new SimulationPanel();
            simulationPanel.setScale(SCALE);
            simulationPanel.loadMap(editorPanel.getRoads(), editorPanel.getLights());
            mainWindow.add(simulationPanel);
            startSimItem.setEnabled(true);
            spawnItem.setEnabled(true);
            stopSimItem.setEnabled(true);
            mainWindow.repaint();
        });

        JMenuItem setUpdateRateItem = new JMenuItem("Update Rate");
        setUpdateRateItem.addActionListener(e -> {
            String updateRateInput = JOptionPane.showInputDialog("Enter the Update Rate of the Simulation");
            int updateRate = Integer.parseInt(updateRateInput);
            simulationPanel.setUpdateRate(updateRate);
            statusLabel.setText("Status: Update Rate set to " + updateRate);
            mainWindow.validate();
            mainWindow.repaint();
        });
        simMenu.add(setUpdateRateItem);

        menuBar.add(simMenu);

        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);

    }
}
