import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class Weather {
    private static Weather single_instance = null;
    final String urlFirstPart = "https://api.openweathermap.org/data/2.5/weather?lat=";
    String _lat;
    final String urlMiddlePart = "&lon=";
    String _lon;
    final String units = "&units=imperial";
    final String urlSecondPart = "&appid=";
    final String apiKey = "3f5a6027649bfe25164da8446d98ba65";




    String windSpeed;
    String windDir;
    String temp;

    private Weather(){

    }

    public static Weather getInstance(){
        if(single_instance != null){
            return single_instance;
        }

        single_instance = new Weather();
        return single_instance;
    }

    public String getTemp(){
        return temp;
    }

    public String getWindSpeed(){
        return windSpeed;
    }

    public String getWindDir(){
        return windDir;
    }

    public void updateLocation(double lat, double lon){
        // Update Lat/Lon
        _lat = Double.toString(lat);
        _lon = Double.toString(lon);
        boolean fileCompleted = true;
        // Send new Request for data
        try {
            URL apiCall = new URL(urlFirstPart + _lat + urlMiddlePart + _lon  + units + urlSecondPart +apiKey);
            // Save the reply
            ReadableByteChannel myChannel = Channels.newChannel(apiCall.openStream());
            FileOutputStream fout = new FileOutputStream("weather.json");
            long max = Long.MAX_VALUE;
            fout.getChannel().transferFrom(myChannel, 0 , max);
            fout.close();
            myChannel.close();
            System.out.println("File Downloaded Successfully!");
            // Parse the reply and update variables
            Scanner scan = new Scanner(new File("weather.json"));
            String jsonString  = "";
            while(scan.hasNextLine()){
                jsonString += scan.next();
            }
            JSONObject fileToObject = new JSONObject(jsonString);
            JSONObject mainSection = fileToObject.getJSONObject("main");
            // Update temp;
            temp = mainSection.get("temp").toString();

            // Update windSpeed
            JSONObject windSection = fileToObject.getJSONObject("wind");
            windSpeed = windSection.get("speed").toString();
            // Update windDir
            windDir = windSection.get("deg").toString();

            System.out.println("Location: " + lat + ", " + lon);
            System.out.println("Temp: " + temp);
            System.out.println("Wind Speed: " + windSpeed);
            System.out.println("Wind Direction: " + windDir);
            System.out.println("\n");

        }catch (MalformedURLException m){
            System.out.println("Problem Occurred with Weather API Call: MalformedException");
            System.out.println(m.getMessage());
            fileCompleted = false;
        }catch (IOException e){
            System.out.println("Problem Occurred with File Read/Write Call: IOException");
            System.out.println(e.getMessage());
            fileCompleted = false;
        }


    }
//    FOR TESTING PURPOSES ONLY
//    public static void main (String[] Args){
//        Weather w = new Weather();
//        w.updateLocation(32.8255, -116.9745);
//    }


}
