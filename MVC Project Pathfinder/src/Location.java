/*
Class Name: Location
Purpose: Store Data about a singular location
 */
public class Location {
    private String _ICAO;
    private String _Name;
    private int _ele = 0;
    private double _lat = 0.00;
    private double _lon = 0.00;
    boolean start;
    boolean end;
    double _DistanceToNext;

    String temp;
    String windSpeed;
    String windDirection;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Location()
    {
        _Name = "N/A";
    }

    // Overloaded Constructor
    public Location(String ICAO, String Name, int ele, double lat, double lon)
    {
        _ICAO = ICAO;
        _Name = Name;
        _ele = ele;
        _lat = lat;
        _lon = lon;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Setter Functions //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void setICAO(String ICAO)
    {
        _ICAO = ICAO;
    }

    void setName(String Name)
    {
        _Name = Name;
    }

    void setEle(int ele)
    {
        _ele = ele;
    }

    void setLatLonPair(double lat, double lon)
    {
        _lat = lat;
        _lon = lon;
    }

    public void print()
    {
        System.out.println(_ICAO + "\t" + _Name + "\t" + _ele + "\t" + _lat + "\t" + "\t" + _lon);
    }

    void setTemp(String t){
        temp = t;
    }

    void setWindSpeed(String ws){
        windSpeed = ws;
    }

    void setWindDirection(String wd){
        windDirection = wd;
    }

    void setElevation(String e){
        _ele = Integer.parseInt(e);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getter Functions //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getTitle()
    {
        return "" + _ICAO + " - " + _Name;
    }

    public String getICAO(){
        return _ICAO;
    }

    public double get_lat(){
        return _lat;
    }
    public double get_lon(){
        return _lon;
    }

    public String getTemp(){
        return temp;
    }

    public String getWindSpeed(){
        return windSpeed;
    }

    public String getWindDirection(){
        return windDirection;
    }
    public int get_ele(){
        return _ele;
    }

}
