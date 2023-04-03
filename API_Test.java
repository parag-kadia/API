import API_Task.JSONArray;
import API_Task.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.lang.String;


public class API_Test {
    public static void main(String[] args) {
        String url = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                String responseBody = "";
                Scanner scanner = new Scanner(apiUrl.openStream());
                while (scanner.hasNext()) {
                    responseBody += scanner.nextLine();
                }
                scanner.close();

                JSONObject data = new JSONObject();

                // Acceptance Criteria 1: Name = "Carbon credits"
                if (data.getString("Name").equals("Carbon credits")) {
                    System.out.println("Acceptance Criteria 1 passed");
                } else {
                    System.out.println("Acceptance Criteria 1 failed");
                }

                // Acceptance Criteria 2: CanRelist = true
                if (data.getBoolean("CanRelist")) {
                    System.out.println("Acceptance Criteria 2 passed");
                } else {
                    System.out.println("Acceptance Criteria 2 failed");
                }

                // Acceptance Criteria 3: The Promotions element with Name = "Gallery" has a Description that contains the text "Good position in category"
                boolean promotionFound = false;
                String promotions = data.getJSONArray("Promotions");

                for (int i = 0; i < promotions.length(); i++) {
          //          JSONObject promotion = promotions.getJSONObject(i);
                    if (promotions.getString("Name").equals("Gallery")
                            && promotions.getString("Description").contains("Good position in category")) {
                        promotionFound = true;
                        break;
                    }
                }

                if (promotionFound) {
                    System.out.println("Acceptance Criteria 3 passed");
                } else {
                    System.out.println("Acceptance Criteria 3 failed");
                }
            } else {
                System.out.println("API request failed");
            }

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}