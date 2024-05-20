import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiTest {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");

        String key = "594e497a7a636b6437357441645668";

        /*URL*/
        urlBuilder.append("/" + key); /* 인증키 */
        urlBuilder.append("/" + "xml"); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + "TbPublicWifiInfo");
        urlBuilder.append("/" + "1");
        urlBuilder.append("/" + "5");
        urlBuilder.append("/" + "마포구");
        urlBuilder.append("/" + "마포나루길");

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");


        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/

        BufferedReader rd;
        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();


        String[] list = sb.toString().split("<row>");
        for (String str: list) {
            System.out.println(str);
            System.out.println();
        }
    }
}