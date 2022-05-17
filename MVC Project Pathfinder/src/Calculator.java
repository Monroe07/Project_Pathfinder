import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * A MATH CLASS USING THE SINGLETON DESIGN PATTERN
 */
class Calculator {
    private static Calculator single_instance = null;

    private Calculator(){

    }

    public static Calculator getInstance(){
        if(single_instance == null){
            single_instance = new Calculator();
        }
        return single_instance;
    }

    /** getDijkstraList(double[][] graph, int src)
     *  Generates a shortest path of nodes
     * @param graph
     * @param src
     * @return A Path consisting of location indexes.
     */

    public static ArrayList<Integer> getDijkstraList(double[][] graph, int src){
        // Temp arraylist to store the path generated
        ArrayList<Integer> path = new ArrayList<Integer>();
        // Placeholder for the current point the function is at
        int currentPoint = src;
        // Array to keep track of which points have already been visited
        boolean[] haveSeen = new boolean[graph.length];
        // Set the first point as being visited
        haveSeen[src] = true;
        path.add(currentPoint);
        // Loop through the same number of times as there are rows
        for (int numOfRows = 0 ; numOfRows < graph.length ; numOfRows++){
            // Look at currentRow and find the min value and only update if the haveSeen[currentRow] == false
            // Insire that the min value is updated from one of the points
            Double min = Double.MAX_VALUE;
            // Scan through current row
            //System.out.println("IN ROW: " + currentPoint);

            for(int col = 0 ; col < graph.length ; col++){
                // Check if we've been there
                if(haveSeen[col] == false){
                    // Check if its lower than current min value
                    // currentPoint = Row = Waypoint from list
                    if(graph[currentPoint][col] < min){
                        //Update Min Value
                        min = graph[currentPoint][col];
                        // update currentPoint
                        currentPoint = col;
                    }
                    else{
                        // Do nothing, it hasn't been seen, but we've already found a point in this row that's lower
                    }
                }
                else{
                    // Do nothing, its already been seen
                }

            }
            // We've checked entire row and have found the min value and its index

            if(min != Double.MAX_VALUE){
                // update haveSeen[currentPoint] = true;
                haveSeen[currentPoint] = true;
                // Add its index to the path list
                path.add(currentPoint);
            }


        } // End of for (int numOfRows = 0 ; numOfRows < graph.length ; numOfRows++)
        // [ ONLY USED TO ENSURE EVERY ROW IS VISITED]

        return path;
    } // End of public int[] getPath(double[][] graph, int src)

    public static ArrayList<Location> reorderLocations(ArrayList<Location> l, ArrayList<Integer> i){
        ArrayList<Location> temp = new ArrayList<Location>();
        for(int j = 0 ; j < i.size() ; j++){
            // Increments through integer arraylist and pushes current location index of integer arraylist to new temp Location list
            temp.add(l.get(i.get(j)));
        }
        return temp;
    }

    public static double[][] generateGraph(ArrayList<Location> l ){
        // Create Empty Graph
        double[][] g = new double[l.size()][l.size()];
        // Get Distances and fill in graph
        /**
         *   Get Distance between 0->0, 0->1, 0->2,....0->N
         *   Get Distance between 1->0, 1->1, 1->2,....1->N
         *   Get Distance between 2->0, 2->1, 2->2,....2->N
         *   .      .       .       .    .      .       .
         *   .      .       .       .    .      .       .
         *   .      .       .       .    .      .       .
         *   .      .       .       .    .      .       .
         *   Get Distance between N->0, N->1, N->2,....N->N
         */
        // Iterate through rows
        for(int r = 0 ; r < l.size() ; r++){
            for(int c = 0 ; c < l.size(); c++){
                g[r][c] = getDistance(l.get(r).get_lat(), l.get(r).get_lon(), l.get(c).get_lat(), l.get(c).get_lon());
            }
        }
        return g;
    }

    /**
     * getDistance
     * Given 2 Lat/Lon Pairs, get the distance in miles between them
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        /**
         *  Calculate the distance between the two points from point one -> point two
         */
        // Timer used in tracking how long it took to complete calculation
        long start = System.currentTimeMillis();
        // Radius of Earth in meters
        double R = 6378137;
        // Resulting Distance
        double distance;
        // Lat One
        double φ1 = toRadians(lat1);
        // Lon One
        double λ1 = toRadians(lon1);
        // Lat Two
        double φ2 = toRadians(lat2);
        // Lon Two
        double λ2 = toRadians(lon2);

        double Δφ = φ2 - φ1;
        double Δλ = λ2 - λ1;

        double Δψ = Math.log(Math.tan(PI / 4 + φ2 / 2) / Math.tan(PI / 4 + φ1 / 2));
        double q = Math.abs(Δψ) > 10e-12 ? Δφ / Δψ : Math.cos(φ1); // E-W course becomes ill-conditioned with 0/0
        // if dLon over 180° take shorter rhumb line across the anti-meridian:
        if (Math.abs(Δλ) > PI) Δλ = Δλ > 0 ? -(2 * PI - Δλ) : (2 * PI + Δλ);

        double dist = Math.sqrt(Δφ * Δφ + q * q * Δλ * Δλ) * R;
        // Distance ot miles
        distance = dist / 1609.344;
        distance = truncate(distance);
        //distance = toIntExact(round(distance));
        long end = System.currentTimeMillis();
        //System.out.println("Calculate Distance: " +(end-start) + " ms");
        return distance;
    }

    /**
     * getHeading
     * Given 2 Lat/Lon Pairs, get the direction from lat/lon 1 -> lat/Lon 2 in degree's
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double getHeading(double lat1, double lon1, double lat2, double lon2) {
        // Calculate Heading in rads of object 2 in relation ot object 1
        double φ1 = toRadians(lat1);
        // Lon One
        double λ1 = toRadians(lon1);
        // Lat Two
        double φ2 = toRadians(lat2);
        // Lon Two
        double λ2 = toRadians(lon2);

        double Δφ = φ2 - φ1;
        double Δλ = λ2 - λ1;

        double Δψ = Math.log(Math.tan(PI / 4 + φ2 / 2) / Math.tan(PI / 4 + φ1 / 2));
        // if dLon over 180° take shorter rhumb line across the anti-meridian:
        if (Math.abs(Δλ) > PI) Δλ = Δλ > 0 ? -(2 * PI - Δλ) : (2 * PI + Δλ);

        double tempRad = Math.atan2(Δλ, Δψ);
        double tempDeg = toDegrees(tempRad);

        // If Degree Angle is less than 0
        if (tempDeg < 0) {
            return truncate(360 + tempDeg);
        } else {
            return truncate(tempDeg);
        }

    }

    public static double getDistToDescent(double tripDistance, double Velocity, double distanceToDescend, double descentRatePerMin){
        // Trip time in minutes
        double tripTimeMinutes = (tripDistance / Velocity) * (double)60;
        // Time needed to go from cruse altitude to field altitude
        double timeNeededToDescend = distanceToDescend / descentRatePerMin;
        // Distance out from airport to begin descent
        double distanceNeeded = timeNeededToDescend/tripTimeMinutes * tripDistance;
        return distanceNeeded;
    }

    public static String getTimeString(double speed, double dist){
        if(speed == 0 || dist == 0){
            return "UNK";
        }
        double timeInMinutes =  (dist / speed) * 60;
        int hr = (int)timeInMinutes / 60;
        int min = (int)timeInMinutes % 60;
        String out = "" + hr + ":" + min;
        return out;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////// Utility Methods ///////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Used as a helper to truncate values to 2 decimal places
     *
     * @param val
     * @return
     */
    public static double truncate(double val) {
        double v = val * 100;
        v = (int) v;
        return (v / 100);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// MAIN FOR TESTING ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static void main(String[] args){
//        Calulator m = new Calulator();
//        double graph[][] = new double [][] {
//               //A            B       C            D
//                {0,         14.2,   9.73,       473.05  },  // A
//                {14.2,      0,      6.23,       472.30  },  // B
//                {9.73,      6.23,   0,          475.03  },  // C
//                {473.05,    472.3,  475.03,     0       }   // D
//        };
//        ArrayList<Integer> p = m.getDijkstraList(graph, 0);
//        System.out.println(p);
//    }
}
