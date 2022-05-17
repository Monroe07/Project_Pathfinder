import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View {
    JFrame frame;
    JTabbedPane tabbedPane;


    ///////////////////////////////////////////////////////////////////////////////
    // Pulled from buildDataInputPanel so it can be accessed from outside function
    ///////////////////////////////////////////////////////////////////////////////
    JButton submitButton;
    DefaultListModel selectedModel;
    DefaultComboBoxModel startLocationModel;
    JTextField cruiseAltitudeField;
    JTextField maxDescentRateField;
    JComboBox startLocationDropDown;
    JComboBox weatherSourceSelector;
    JTextField departureTimeHourField;
    JTextField departureTimeMinField;
    JComboBox departureTimeAmPm;
    JTextField crusingSpeedField;

    public View(String title){
        int selection = JOptionPane.showConfirmDialog(null, "By Clicking YES, You acknowledge that Project Pathfinder \ncannot be your primary means of Navigational aid as per FAA Regulation", "Liability Disclaimer",JOptionPane.OK_OPTION, JOptionPane.OK_CANCEL_OPTION);
        if(selection == JOptionPane.OK_OPTION){


        frame = new JFrame(title);
        //System.out.println(1);
        // Display the window.
        frame.setSize(820, 620);
        //System.out.println(2);
        frame.setVisible(true);
        //System.out.println(3);
        frame.setResizable(false);
        //System.out.println(4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //System.out.println(5);

        // set grid layout for the frame
        frame.getContentPane().setLayout(new GridLayout(1, 1));
        //System.out.println(6);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        frame.getContentPane().add(tabbedPane);


        }
        // Otherwise display a error message
        else{
            JOptionPane.showMessageDialog(null, "You must accept the terms to use Project Pathfinder\nGoodbye!","ERROR" ,JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setLocationList(ArrayList<String> al){
        //System.out.println("In setLocationList");
        // Upon Location List generation from
        tabbedPane.add(buildDataInputPanel(al), "DATA ENTRY");
    }

    JPanel buildDataInputPanel(ArrayList<String> _airportList){
        JPanel panel = new JPanel(false);

        panel.setLayout(null);

        panel.setPreferredSize (new Dimension(1100, 817));

        startLocationModel = new DefaultComboBoxModel<String>();
        DefaultListModel locationModel = new DefaultListModel<String>();
        selectedModel = new DefaultListModel<String>();

        for(String current : _airportList){
            startLocationModel.addElement(current);
            locationModel.addElement(current);
        }

        // Main Toolbar (File, Edit etc.)
        JMenuBar toolBar;

        // Cruising Altitude
        JLabel cruiseAltitudeLabel;

        JLabel ftLabel;

        // Max Descent Rate
        JLabel maxDescentRateLabel;

        JLabel feetPerMinLabel;

        // Start Location
        JLabel startLocationLabel;


        // Airport List to choose from
        JLabel airportsLabel;
        JList airportsSelectionSet;
        JScrollPane airportScrollPane;

        // Selected Airports
        JLabel selectedTextField;
        JList selectedList;
        JScrollPane selectedListScrollPane;

        // Buttons to Add and Remove from Selected Airports
        JButton addToSelectedListButton;
        JButton removeFromSelectedListButton;

        // Submit Button


        // Trip Data Section
        JLabel tripDataLabel;

        // Weather Source
        JLabel weatherSourceLabel;


        // Estimated Time frame
        JLabel departureTimeLabel;

        JLabel colon;


        // Airframe data
        JLabel airframeDataLabel;
        JLabel cruisingSpeedLabel;

        JLabel knotsLabel;

        //construct preComponents

        JMenu fileMenu = new JMenu ("File");
        JMenuItem manage_databaseItem = new JMenuItem ("Manage Database");
        fileMenu.add (manage_databaseItem);
        JMenuItem printItem = new JMenuItem ("Print");
        fileMenu.add (printItem);
        JMenuItem exitItem = new JMenuItem ("Exit");
        fileMenu.add (exitItem);
        JMenu helpMenu = new JMenu ("Help");
        JMenuItem contentsItem = new JMenuItem ("Contents");
        helpMenu.add (contentsItem);
        JMenuItem aboutItem = new JMenuItem ("About");
        helpMenu.add (aboutItem);

        // Weather
        String[] weatherSourceSelectorItems = {"OpenWeatherMap"};

        // Time
        String[] departureTimeAmPmItems = {"AM", "PM"};


        //construct components
        cruiseAltitudeLabel = new JLabel ("Cruise Altitude");
        maxDescentRateLabel = new JLabel ("Max Descent Rate");
        cruiseAltitudeField = new JTextField (5);
        maxDescentRateField = new JTextField (5);
        ftLabel = new JLabel ("ft");
        feetPerMinLabel = new JLabel ("ft/min.");
        startLocationLabel = new JLabel ("Start Location");

        startLocationDropDown = new JComboBox ();
        startLocationDropDown.setModel(startLocationModel);
        airportsSelectionSet = new JList ();
        airportsSelectionSet.setModel(locationModel);
        airportScrollPane = new JScrollPane(airportsSelectionSet);// Selected Lists
        airportsLabel = new JLabel ("Airports");
        selectedList = new JList ();
        selectedList.setModel(selectedModel);
        selectedListScrollPane = new JScrollPane(selectedList);
        selectedTextField = new JLabel ("Selected");
        addToSelectedListButton = new JButton(">>>");
        addToSelectedListButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!airportsSelectionSet.isSelectionEmpty()){
                    //System.out.println(airportsSelectionSet.getSelectedIndex());
                    selectedModel.addElement(locationModel.elementAt(airportsSelectionSet.getSelectedIndex()));
                }else{
                    System.out.println("No Item Has Been Selected To Be Added!");
                }

            }
        });
        removeFromSelectedListButton = new JButton ("<<<");
        removeFromSelectedListButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!selectedList.isSelectionEmpty()){
                    System.out.println(selectedList.getSelectedIndex());
                    selectedModel.remove(selectedList.getSelectedIndex());
                }
                else{
                    System.out.println("No Item Has Been Selected For Removal!");
                }

            }
        });
        submitButton = new JButton ("Submit");
        toolBar = new JMenuBar();
        toolBar.add (fileMenu);
        toolBar.add (helpMenu);
        weatherSourceSelector = new JComboBox (weatherSourceSelectorItems);
        weatherSourceLabel = new JLabel ("Weather Source");
        departureTimeHourField = new JTextField (5);
        colon = new JLabel (":");
        departureTimeMinField = new JTextField (5);
        departureTimeLabel = new JLabel ("Departure Time");
        departureTimeAmPm = new JComboBox (departureTimeAmPmItems);
        airframeDataLabel = new JLabel ("AIRFRAME DATA");
        tripDataLabel = new JLabel ("TRIP DATA");
        cruisingSpeedLabel = new JLabel ("Cruising Speed");
        crusingSpeedField = new JTextField (5);
        knotsLabel = new JLabel ("Knots");

        //adjust size and set layout
        panel.setPreferredSize (new Dimension(748, 494));
        panel.setLayout (null);

        //add components
        panel.add (cruiseAltitudeLabel);
        panel.add (maxDescentRateLabel);
        panel.add (cruiseAltitudeField);
        panel.add (maxDescentRateField);
        panel.add (ftLabel);
        panel.add (feetPerMinLabel);
        panel.add (startLocationLabel);
        panel.add (startLocationDropDown);
        panel.add (airportScrollPane);
        panel.add (airportsLabel);
        panel.add (selectedListScrollPane);
        panel.add (selectedTextField);
        panel.add (addToSelectedListButton);
        panel.add (removeFromSelectedListButton);
        panel.add (submitButton);
        panel.add (toolBar);
        panel.add (weatherSourceSelector);
        panel.add (weatherSourceLabel);
        panel.add (departureTimeHourField);
        panel.add (colon);
        panel.add (departureTimeMinField);
        panel.add (departureTimeLabel);
        panel.add (departureTimeAmPm);
        panel.add (airframeDataLabel);
        panel.add (tripDataLabel);
        panel.add (cruisingSpeedLabel);
        panel.add (crusingSpeedField);
        panel.add (knotsLabel);

        //set component bounds (only needed by Absolute Positioning)
        cruiseAltitudeLabel.setBounds (35, 50, 90, 25);
        maxDescentRateLabel.setBounds (15, 75, 105, 25);
        cruiseAltitudeField.setBounds (125, 50, 85, 25);
        maxDescentRateField.setBounds (125, 75, 85, 25);
        ftLabel.setBounds (215, 50, 40, 25);
        feetPerMinLabel.setBounds (215, 75, 40, 25);
        startLocationLabel.setBounds (535, 50, 100, 25);
        startLocationDropDown.setBounds (620, 50, 100, 25);
        airportScrollPane.setBounds (250, 125, 200, 280);
        airportsLabel.setBounds (325, 100, 50, 25);

        selectedListScrollPane.setBounds (535, 125, 200, 280);
        selectedTextField.setBounds (595, 100, 55, 25);
        // Buttons between Lists
        addToSelectedListButton.setBounds (460, 160, 60, 25);
        removeFromSelectedListButton.setBounds (460, 215, 60, 25);

        submitButton.setBounds (620, 425, 100, 25);
        toolBar.setBounds (0, 0, 800, 25);
        weatherSourceSelector.setBounds (125, 160, 115, 25);
        weatherSourceLabel.setBounds (25, 160, 100, 25);
        departureTimeHourField.setBounds (125, 190, 30, 25);
        colon.setBounds (157, 190, 10, 25);
        departureTimeMinField.setBounds (162, 190, 30, 25);
        departureTimeLabel.setBounds (30, 190, 100, 25);
        departureTimeAmPm.setBounds (195, 190, 45, 25);
        airframeDataLabel.setBounds (65, 25, 100, 25);
        tripDataLabel.setBounds (85, 130, 100, 25);
        cruisingSpeedLabel.setBounds (250, 50, 100, 25);
        crusingSpeedField.setBounds (340, 50, 100, 25);
        knotsLabel.setBounds (440, 50, 100, 25);

        //tabbedPane.addTab(Name, panel);
        return panel;
    }

    JPanel buildTripPanel(Trip t){
        //JPanel panel = new JPanel();
        //construct components
         JLabel windspeedLabel;
         JLabel windDirectionLabel;
         JLabel airTempLabel;
         JLabel travelTimeLabel;
         JLabel windspeedData;
         JLabel windDirectionData;
         JLabel airTempData;
         JLabel travelTimeData;
         JLabel approachInfoHeader;
         JLabel distanceToDescendLabel;
         JLabel distanceToDescendData;
         JLabel headingLabel;
         JLabel distanceLabel;
         JLabel locationDataHeader;
         JLabel weatherHeading;

        weatherHeading = new JLabel ("Weather Data");
        windspeedLabel = new JLabel ("Windspeed:");     windspeedData = new JLabel (t.getEnd().getWindSpeed() + "mph");
        windDirectionLabel = new JLabel ("Wind Dir:");  windDirectionData = new JLabel (t.getEnd().getWindDirection() + "°");
        airTempLabel = new JLabel ("Air Temp.:");       airTempData = new JLabel (t.getEnd().getTemp() + "°F");


        locationDataHeader = new JLabel ("Location Data");
        distanceLabel = new JLabel ("Distance:    " + t.getDistance() + "mi.");
        headingLabel = new JLabel ("Heading:     " + t.getDirection() +"°");

        approachInfoHeader = new JLabel ("Approach Info");
        travelTimeLabel = new JLabel ("Travel Time:");  travelTimeData = new JLabel (Calculator.getTimeString(t.getCruiseSpeed() ,t.getDistance()));
        distanceToDescendLabel = new JLabel ("Descend Dist:");  distanceToDescendData = new JLabel ("" + t.getDescendDist() + "mi.");








        //adjust size and set layout
//        panel.setPreferredSize (new Dimension (1100, 817));
//        panel.setLayout (null);

        //add components
        JPanel map = t.getMap();
//        map.setBounds(300,175,400,300);
        map.setBounds(0,0,820,620);

        map.add (windspeedLabel);
        map.add (windDirectionLabel);
        map.add (airTempLabel);
        map.add (travelTimeLabel);
        map.add (windspeedData);
        map.add (windDirectionData);
        map.add (airTempData);
        map.add (travelTimeData);
        map.add (approachInfoHeader);
        map.add (distanceToDescendLabel);
        map.add (distanceToDescendData);
        map.add (headingLabel);
        map.add (distanceLabel);
        map.add (locationDataHeader);
        map.add (weatherHeading);

        //set component bounds (only needed by Absolute Positioning)
        windspeedLabel.setBounds (620, 45, 70, 25);
        windDirectionLabel.setBounds (635, 65, 90, 25);
        airTempLabel.setBounds (630, 85, 60, 25);
        travelTimeLabel.setBounds (620, 235, 70, 25);
        windspeedData.setBounds (710, 45, 100, 25);
        windDirectionData.setBounds (710, 65, 100, 25);
        airTempData.setBounds (710, 85, 100, 25);
        travelTimeData.setBounds (710, 235, 100, 25);
        approachInfoHeader.setBounds (650, 215, 105, 25);
        distanceToDescendLabel.setBounds (610, 255, 120, 25);
        distanceToDescendData.setBounds (710, 255, 100, 25);
        headingLabel.setBounds (640, 160, 110, 25);
        distanceLabel.setBounds (635, 140, 150, 25);
        locationDataHeader.setBounds (650, 120, 100, 25);
        weatherHeading.setBounds (650, 25, 100, 25);






        return map;
    }

    public void clearTabs(){
        while(tabbedPane.getTabCount() > 1){
            tabbedPane.remove(tabbedPane.getTabCount() -1);
        }
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public ArrayList<String> getSelectedList(){
        ArrayList<String> temp = new ArrayList<String>();
        for(int i = 0 ; i < selectedModel.getSize() ; i ++){
            temp.add(selectedModel.get(i).toString());
        }
        return temp;
    }

    public String getSelectedStart(){
        return startLocationModel.getSelectedItem().toString();
    }

    public String getCrusingAltitude(){
        if(cruiseAltitudeField.getText() != "")
            return cruiseAltitudeField.getText().toString();
        else{
            return "0";
        }
    }

    public String getmaxDescentRate(){
        if(maxDescentRateField.getText() != "")
            return maxDescentRateField.getText().toString();
        else{
            return "0";
        }

    }

    public String getWeatherSource(){
        return weatherSourceSelector.getSelectedItem().toString();
    }

    public String getDepartureTimeHour(){
        return departureTimeHourField.getText().toString();
    }

    public String getDepartureTimeMin(){
        return departureTimeMinField.getText().toString();
    }

    public String getDepartureTimeAmPm(){
        return departureTimeAmPm.getSelectedItem().toString();
    }

    public String getCrusingSpeed(){
        if(crusingSpeedField.getText() != "")
            return crusingSpeedField.getText().toString();
        else{
            return "0";
        }

    }

    public void setTrips(ArrayList<Trip> trips){
        for(int i = 0 ; i < trips.size() ; i ++){
            tabbedPane.add(buildTripPanel(trips.get(i)), trips.get(i).getStart().getICAO() + " → " + trips.get(i).getEnd().getICAO());
        }
    }



}

