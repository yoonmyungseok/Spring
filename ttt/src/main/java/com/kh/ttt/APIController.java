package com.kh.ttt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
    public static final String KEY = "muCEXu22q9%2BFJCMxprw%2BFyXNLspw%2BEjUvJH713oKaPGPihbWWDCyC4YGSeMEqBKgdxMja70XEJ8HkUKEQ8nw1w%3D%3D";

    @ResponseBody
    @RequestMapping(value = "disaster.do", produces = "application/json; charset=UTF-8")
    public String getShelterList() throws IOException {
        String url = "http://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
        url += "?ServiceKey=" + KEY;
        url += "&pageNo=1";
        url += "&type=json";
        url += "&numOfRows=20";

        URL requestUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String responseText = "";
        String line = "";

        while ((line = br.readLine()) != null) {
            responseText += line;
        }
        br.close();
        urlConnection.disconnect();

        return responseText;
    }
}
