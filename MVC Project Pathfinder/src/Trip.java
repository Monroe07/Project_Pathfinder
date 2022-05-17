import javax.swing.*;

public class Trip {
    JPanel map;
    double distance;
    double direction;
    Location start;
    Location end;

    int startHour;
    int startMin;

    double descendDist;
    double cruiseSpeed;


    public Trip(){

    }

    /*******************************************************************************************************************
     * Setters
     ******************************************************************************************************************/
    public void setMap(JPanel p){
        map = p;
    }

    public void setDistance(double d){
        distance = d;
    }

    public void setDirection(double d){
        direction = d;
    }

    public void setStart(Location s){
        start = s;
    }

    public void setEnd(Location e){
        end = e;
    }

    public void setDescendDist(double d){
        descendDist = d;
    }
    public void setCruiseSpeed(double d){
        cruiseSpeed = d;
    }

    /*******************************************************************************************************************
     * Getters
    *******************************************************************************************************************/
    public JPanel getMap(){
        return map;
    }

    public double getDistance(){
        return distance;
    }

    public double getDirection(){
        return direction;
    }

    public Location getStart(){
        return start;
    }

    public Location getEnd(){
        return end;
    }

    public double getDescendDist(){
        return descendDist;
    }

    public double getCruiseSpeed(){
        return cruiseSpeed;
    }
}
