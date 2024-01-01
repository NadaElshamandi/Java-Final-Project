import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JFrame;
import javax.swing.JLabel;
import org.json.JSONObject;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;

public class WeatherApp {
public static void main(String[] args) throws Exception {
    // Define the API endpoint and your API key
    String apiEndPoint = "https://api.openweathermap.org/data/2.5/weather";
    String apiKey = "443ab3c19a3bd5a9ac210179db2b46b4";

    // Create a simple user interface
    JFrame frame = new JFrame("Weather App");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridLayout(6, 2));

    JLabel locationLabel = new JLabel("Enter Location:");
    JTextField locationField = new JTextField(20);
    JButton button = new JButton("Get Weather");
    JLabel temperatureLabel = new JLabel();
    JLabel humidityLabel = new JLabel();
    JLabel errorLabel = new JLabel();

    button.addActionListener(e -> {
        String location = locationField.getText();

        try {
            // Construct the request URL
            URI uri = new URI(apiEndPoint + "?q=" + URLEncoder.encode(location, "UTF-8") + "&appid=" + apiKey);
            URL url = uri.toURL();
            // Open a connection to the API
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parse the weather data
            JSONObject weatherData = new JSONObject(content.toString());
            JSONObject main = weatherData.getJSONObject("main");
            double temperature = main.getDouble("temp") - 273.15;  // Convert from Kelvin to Celsius
            double humidity = main.getDouble("humidity");

            // Display the weather data
            temperatureLabel.setText("Temperature: " + String.format("%.2f", temperature) + "°C");
            humidityLabel.setText("Humidity: " + humidity + "%");
            errorLabel.setText("");
        } catch (Exception ex) {
            errorLabel.setText("Error: " + ex.getMessage());
        }
    });

    frame.add(locationLabel);
    frame.add(locationField);
    frame.add(button);
    frame.add(new JLabel("Weather Details:"));
    frame.add(temperatureLabel);
    frame.add(humidityLabel);
    frame.add(errorLabel);

    frame.pack();
    frame.setVisible(true);
}
}
