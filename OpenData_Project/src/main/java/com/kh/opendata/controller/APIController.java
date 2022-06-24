package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	//발급받은 인증키 정보를 변수에 담아두기
    //=>어차피 변할 일이 없기 때문에 상수필드로 구성
    public static final String SERVICEKEY = "muCEXu22q9%2BFJCMxprw%2BFyXNLspw%2BEjUvJH713oKaPGPihbWWDCyC4YGSeMEqBKgdxMja70XEJ8HkUKEQ8nw1w%3D%3D";

    //JSON 형식으로 OpenAPI 활용하기
    /*
    @ResponseBody
    @RequestMapping(value = "air.do", produces="application/json; charset=UTF-8")
    public String airPollution(String location) throws IOException{
        String url="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
                url+="?serviceKey="+SERVICEKEY;
                url+="&sidoName="+URLEncoder.encode(location,"UTF-8");
                url+="&returnType=json";
                url += "&numOfRows=50";//결과 갯수
        
        URL requestUrl = new URL(url);
        
        HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();
        
        urlConnection.setRequestMethod("GET");
    
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    
        String responseText = "";//응답데이터 기록용 변수
    
        String line;//null
    
        while ((line = br.readLine()) != null) {
            responseText += line;
        }
        br.close();
        urlConnection.disconnect();
    
        System.out.println(responseText);
    
        //기존의 ajax 흐름=>ArrayList나 VO 객체타입의 내용둘을 JSONObject, JSONArray로 가공하여 return
        //공공데이터 ajax흐름=>애초에 결과값이 JSON으로 나와있음(따로 처리하여 return 하지 않아도 됨)
        return responseText;
    }*/
    
    //XML 형식으로 OpenAPI 활용하기
    @ResponseBody
    @RequestMapping(value = "air.do", produces = "text/xml; charset=UTF-8")
    public String airPollution(String location) throws IOException {
        String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
        url += "?serviceKey=" + SERVICEKEY;
        url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
        url += "&returnType=xml";
        url += "&numOfRows=50";

        URL requestUrl = new URL(url);

        HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();

        urlConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String responseText = "";

        String line;

        while ((line = br.readLine()) != null) {
            responseText += line;
        }
        br.close();
        urlConnection.disconnect();

        //System.out.println(responseText);

        return responseText;
    }
}
