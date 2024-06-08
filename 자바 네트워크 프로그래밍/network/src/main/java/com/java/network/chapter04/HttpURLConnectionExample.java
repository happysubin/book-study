package com.java.network.chapter04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpURLConnection을 사용하면 많은 로우레벨 코드의 사용을 피할 수 있다.
 */
public class HttpURLConnectionExample {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) {
        HttpURLConnectionExample http = new HttpURLConnectionExample();
        System.out.println("Sent Http GET Request");
        http.sendGet();
    }

    private void sendGet() {
        try {
            String urlQuery = "https://www.google.com/search?q=";
            String userQuery = "java sdk";
            String urlEncoded = urlQuery + URLEncoder.encode(userQuery, "UTF-8");
            System.out.println(urlEncoded);

            String query = "https://www.google.com/search?q=java+sdk&ie=utf-8&oe=utf-8";
            URL url = new URL(query);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();

            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                String response = getResponse(connection);
                System.out.println("response: " + response.toString());
            } else {
                System.out.println("Bad Response Code: " + responseCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResponse(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            // Process headers
            System.out.println("Request Headers");
            Map<String, List<String>> requestHeaders = connection.getHeaderFields();
            Set<String> keySet = requestHeaders.keySet();
            for (String key : keySet) {
                if ("Set-cookie".equals(key)) {
                    List values = requestHeaders.get(key);
                    String cookie = key + " = " + values.toString() + "\n";
                    String cookieName = cookie.substring(0, cookie.indexOf("="));
                    String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                    System.out.println(cookieName + ":" + cookieValue);
                }
            }
            System.out.println();

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
