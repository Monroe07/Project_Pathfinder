
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.io.*;
import java.util.List;


/**
 * Needed for JXMapVeiwer
 */
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;


public class Model {

    // Arraylist of all airports imported from spreadsheet
    ArrayList<Location> locationList;
    ArrayList<String> selectedListString;
    ArrayList<Location> selectedListLocation;
    ArrayList<Trip> trips;


    double cruiseAltitude;
    double maxDescentRate;
    double cruiseSpeed;





    String line;
    String delimiter = ",";

    public Model(){

    }

    public Model(String _fileName){
        locationList = new ArrayList<Location>();
        selectedListLocation = new ArrayList<Location>();
        trips = new ArrayList<Trip>();

        ;
        if(!setupDatabase(_fileName)){
            System.exit(1);
        }

    }

    public void clearPreviousData(){
        selectedListLocation.clear();
        trips.clear();
    }

    private boolean setupDatabase(String fn){
        System.out.println("Attempting to create database from file: " + fn);
        int lineNum = 0;
        boolean worked = true;
        //System.out.println("At the Try");
        try{
            InputStream is = getClass().getResourceAsStream(fn);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            //System.out.println("About to Hit While Loop");
            while((line = reader.readLine()) != null) // While there is a new line
            {
                //System.out.println("In While Loop");
                String[] airport = line.split(delimiter);
                for(int i = 0; i < airport.length ; i++)
                {
                    //System.out.print(airport[i] + "\t");
                }
                //System.out.println();
                if(lineNum != 0){
                    Location airportObject = new Location(
                            airport[0],                         // ICAO
                            airport[2],                         // Name
                            Integer.parseInt(airport[3]),       // Elevation
                            Double.parseDouble(airport[7]),     // Latitude
                            Double.parseDouble(airport[8]));    // Longitude

                    locationList.add(airportObject);


                }
                lineNum++;
            }



        }catch (Exception e)
        {
            System.out.println("Problem reading file: " + fn + " . Please ensure filename is correct and includes the extension type");
            JOptionPane error = new JOptionPane("Problem reading file: " + fn + " . Please ensure filename is correct and includes the extension type");
            worked = false;
            return worked;
        }
        return worked;
    }

    public ArrayList<String> getDatabaseStr(){
        ArrayList<String> temp = new ArrayList<String>();

        for(int i = 0 ; i < locationList.size() ; i++){
            temp.add(locationList.get(i).getTitle());
        }
        return temp;
    }

    public void setSelectedListString(ArrayList<String> sL){
        System.out.println("Selected Locations");

        // Loop through entire selected list
        for(String s : sL){

            // Get just the ICAO of the current sL
            String icao = s.split(" ")[0];
            // Search the locationList for matching ICAO
            for(Location l : locationList){
                // Search saved arrayList "locationList" for a locationObject with temp[0] as its ICAO
                if(l.getICAO() != null && l.getICAO().equals(icao)){
                    // When Found, add it to selectedListLocation;
                    selectedListLocation.add(l);
                    break;
                }else{
                    // Do Nothing
                } // End of else
            } // End of for(Location l : locationList)
            // If Found, Add to selectedListLocation

            // If not found, Print and error

        } // End of for(String s : sL)
        printSelectedList();
        //selectedListString = sL;


    }

    public void setSelectedStartString(String s){
        System.out.print("Setting Start Location: ");
        String[] temp =  s.split(" ");
        System.out.println(temp[0]);
        for(Location l : locationList){
            // Search saved arrayList "locationList" for a locationObject with temp[0] as its ICAO
            if(l.getICAO() != null && l.getICAO().equals(temp[0])){
                // When Found, add it to selectedListLocation;
                selectedListLocation.add(l);
                return;
            }else{
                // Do Nothing
            }
        }
        // If this point is reached, the entire locationList was searched and a match was not found;
        System.out.println("STARTING LOCATION NOT FOUND IN locationList !!! - setElectedStart");


    }

    public void setCruiseAltitude(String ca){
        if(ca.length() != 0) {
            System.out.println("Cruising Altitude: " + ca);
            cruiseAltitude = Double.parseDouble(ca);
        }
        else{
            cruiseAltitude = 0;
            System.out.println("Cruising Altitude: N/A");
        }

    }

    public void setMaxDescentRate(String mdr){

        if(mdr.length() != 0){
            System.out.println("Max Descent Rate: " + mdr);
            maxDescentRate = Double.parseDouble(mdr);
        }

        else
            maxDescentRate = 0;
    }

    public void setCruisingSpeed(String cs){

        if(cs.length() != 0){
            cruiseSpeed = Double.parseDouble(cs);
            System.out.println("Cruising Speed: " + cs);
        }

        else
            cruiseSpeed = 0;
    }

    public void setWeatherSource(String ws){
        System.out.println("Weather Source: " + ws);
    }

    public void setDepartureTime(String h, String m, String ampm){
        System.out.println("Departure Time: " + h + ":" + m + ampm);
    }

    public void setWeatherData(){
        Weather w = Weather.getInstance();
        for(int i = 0 ; i < selectedListLocation.size() ; i++){
            System.out.println("Weather at: " + selectedListLocation.get(i).getICAO());
            w.updateLocation(selectedListLocation.get(i).get_lat(), selectedListLocation.get(i).get_lon());
            selectedListLocation.get(i).setTemp(w.getTemp());
            selectedListLocation.get(i).setWindSpeed(w.getWindSpeed());
            selectedListLocation.get(i).setWindDirection(w.getWindDir());
        }

    }

    /** NOT USED
     *  get Image URL
     *  Gets the Google Maps URL for a given Trip between 2 Latitude/Longitude Pairs
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
//    public static String getImageURL(String lat1, String lon1, String lat2, String lon2){
//        String baseURL = "https://maps.googleapis.com/maps/api/staticmap?";
//
//        //String Zoom = "&zoom=12";
//
//        String Size = "&size=1280x1280&scale=2";
//        String MapType = "&maptype=satellite";
//
//        String marker1 = "&markers=size:tiny%7Ccolor:red%7Clabel:E%7C" + lat1 + ","+ lon1;
//        String marker2 = "&markers=size:tiny%7Ccolor:green%7Clabel:S%7C" + lat2 +"," + lon2;
//
//        String line = "&path=color:0xff000080|weight:2|" + lat1 + "," + lon1 + "|" + lat2 + "," + lon2;
//
//        String Key = "&key=AIzaSyCJY6zQUHsfwCWv9cqddxCRJd3S-V_OtrQ";
//
//        return baseURL + Size + MapType + marker1 + marker2 + line + Key;
//    }

    /** NOT USED
     *  Get Map Image
     *  Saves an image at a url as a buffered image
     * @param url
     * @return
     */
//    public static BufferedImage getMapImage(String url){
//        BufferedImage image = null;
//        File f = new File("testImage.png");
//        try{
//            URL link = new URL(url);
//            image = ImageIO.read(link);
//            ImageIO.write(image, "PNG", f);
//            return image;
//        } catch(MalformedURLException ml){
//            System.out.println("MalformedURLException Occurred");
//            return null;
//        } catch (IOException ie){
//            System.out.println("IOException Occurred");
//            return null;
//        }
//
//    }

    /** getMapJxMaps : Creates a map [sub]panel using a JxMapsViewer Library
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static JPanel getMapJxMapsPanel(double lat1, double lon1, double lat2, double lon2){
        JPanel tempPanel = new JPanel();
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setSize(600,550);
        tempPanel.setPreferredSize(new Dimension(600,550));
        tempPanel.setVisible(true);
        tempPanel.add(mapViewer);
        //tempPanel.setSize(8000, 6000);

        tempPanel.setLayout(null);
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition start = new GeoPosition(lat1, lon1);


        GeoPosition end = new GeoPosition(lat2, lon2);

        GeoPosition midpoint = new GeoPosition((lat1 + lat2)/2, (lon1 + lon2)/2);

        List<GeoPosition> track = Arrays.asList(start, end);
        RoutePainter routePainter = new RoutePainter(track);

        // Set the focus
        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 1);

        Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(Arrays.asList(
                //new DefaultWaypoint(start),
                //new DefaultWaypoint(end)));
                new MyWaypoint("S", Color.GREEN, start),
                new MyWaypoint("E", Color.RED, end)));


        // Create a waypoint painter that takes all the waypoints
//        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
//        waypointPainter.setWaypoints(waypoints);

        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer());


        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(routePainter);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);

        mapViewer.setOverlayPainter(painter);

        return tempPanel;
    }

    /**
     * Prints out selected ICAO's [JUST FOR TESTING]
     */
    public void printSelectedList(){
        System.out.println();
        System.out.println("Selected Location List");
        System.out.println("Size: " + selectedListLocation.size());
        System.out.println("---------------------------");
        for(Location l : selectedListLocation){
            System.out.println(l.getICAO());
        }

        System.out.println("---------------------------");
        System.out.println("End of SelectedList");
        System.out.println();
    }

    public void generateTripList(){
        // Reorder selectedListLocation using algorithm
        selectedListLocation = Calculator.reorderLocations(selectedListLocation, Calculator.getDijkstraList(Calculator.generateGraph(selectedListLocation), 0));

        int sIndex = 0;
        int eIndex = 1;
        for(int i = 0 ; i < selectedListLocation.size()-1 ; i++){
            // Create a new Trip
            Trip temp = new Trip();
            // set its start and end location objects
            temp.setStart(selectedListLocation.get(sIndex));
            temp.setEnd(selectedListLocation.get(eIndex));

            // calculate their distance and set trip distance
            temp.setDistance(
                    Calculator.getDistance(
                            selectedListLocation.get(sIndex).get_lat(),
                            selectedListLocation.get(sIndex).get_lon(),
                            selectedListLocation.get(eIndex).get_lat(),
                            selectedListLocation.get(eIndex).get_lon()
                    ));
            // calculate their direction and set trip direction
            temp.setDirection(
                    Calculator.getHeading(
                            selectedListLocation.get(sIndex).get_lat(),
                            selectedListLocation.get(sIndex).get_lon(),
                            selectedListLocation.get(eIndex).get_lat(),
                            selectedListLocation.get(eIndex).get_lon()
                    )
            );
            // Create their map panel and set their map panel
            temp.setMap(
                    getMapJxMapsPanel(
                            selectedListLocation.get(sIndex).get_lat(),
                            selectedListLocation.get(sIndex).get_lon(),
                            selectedListLocation.get(eIndex).get_lat(),
                            selectedListLocation.get(eIndex).get_lon()
                    )
            );

            temp.setDescendDist(Calculator.truncate(Calculator.getDistToDescent(temp.getDistance(), cruiseSpeed, selectedListLocation.get(sIndex).get_ele() - selectedListLocation.get(eIndex).get_ele(), maxDescentRate)));
            temp.setCruiseSpeed(cruiseSpeed);
            // Add temp trip to trips ArrayList
            trips.add(temp);
            // Add 1 to sIndex and eIndex [Advance forward in pair of locations
            sIndex++;
            eIndex++;
        }
    }

    public ArrayList<Trip> getTripList(){
        return trips;
    }

    public static void main(String[] args){
        Model m = new Model();
        Trip t = new Trip();

        JPanel p = m.getMapJxMapsPanel(32.7338, -117.1933, 32.8255, -116.9745);
        t.setMap(p);
        p.setSize(new Dimension(800,600));
        p.setLocation(0,0);
        JFrame frame = new JFrame();
        frame.add(t.getMap());
        frame.setVisible(true);
        frame.setSize(1100, 817);
        frame.pack();
    }

}
