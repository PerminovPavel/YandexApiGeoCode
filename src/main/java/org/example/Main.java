package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.*;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

    String ApiKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    System.out.println("Введите что ищем:");
    Scanner sc = new Scanner(System.in);
    String name = sc.nextLine();
    name=name.replace(" ","+");
    HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://geocode-maps.yandex.ru/1.x/?apikey=4183213c-37c6-4fc9-98b8-1d5820ba4bce&geocode="+name+"&format=json&type=biz")).GET().build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject jo = new JSONObject(response.body());
    JSONObject ja = jo.getJSONObject("response").getJSONObject("GeoObjectCollection");
    JSONArray res = (JSONArray) ja.get("featureMember");

    int resOfres = res.length();

    if (resOfres==0){
      System.out.println("404 error");
    } else {
      System.out.println("Всего нашлось: " +resOfres);
      for (Object o : res) {
        JSONObject properties = ((JSONObject) o).getJSONObject("GeoObject").getJSONObject("metaDataProperty").getJSONObject("GeocoderMetaData");
        System.out.println(properties.get("text"));
      }
    }
  }
}
