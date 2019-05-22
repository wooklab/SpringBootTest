package com.wmp.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Stream;

public class HttpHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

    enum CharSetEnum {
        EUC_KR("EUC-KR"), UTF_8("UTF-8"), MS949("MS949"), KS_C("KS_C_5601-1987");

        private String charset;

        CharSetEnum(String charset) {
            this.charset = charset;
        }

        public String getCharSet() {
            return charset;
        }

        public static Stream<CharSetEnum> stream() {
            return Stream.of(CharSetEnum.values());
        }

    }

    private HttpHandler() {
        throw new AssertionError();
    }


    /**
     * Method to retrieves HTML source code using HTTP URL Connection.<br/>
     *
     * @param seedUrl 연결할 URL
     * @return HTML Content
     */
    public static String getHttpHTML(String seedUrl) {
        URL url;
        HttpURLConnection con = null;

        BufferedReader br = null;
        String          readLine;
        String          contentCharset;
        StringBuilder   sb = new StringBuilder();

        try {
            url = new URL(seedUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            /* When response code is not 200, return to response code */
            String responseCode = String.valueOf(con.getResponseCode());
            log.debug("Response CODE : {}", responseCode);
            if (responseCode.equals("200") == false)
                return null;

            /* To get the encoding value of the document */
            contentCharset = con.getContentType();
            log.debug("load charset : {}", contentCharset);

            br = getBufferedReaderNewInstance(con, contentCharset);

            while ((readLine = br.readLine()) != null) {
//                sb.append(readLine + "\r\n");   // add a newline string
                sb.append(readLine);
            }
            br.close();

        } catch (FileNotFoundException fileNotFoundException) {
            log.error("File Not Found Error in getHttpHTML method", fileNotFoundException);
        } catch (IOException iOException) {
            log.error("IO Error Error in getHttpHTML method", iOException);
        } finally {
            destroy(con, br);
        }

        return sb.toString();
    }

    private static BufferedReader getBufferedReaderNewInstance(HttpURLConnection conn, String contentCharset) throws IOException {
        CharSetEnum correctCharset = CharSetEnum.stream()
                .filter(charset -> contentCharset.toUpperCase().contains(charset.getCharSet()))
                .findAny().orElse(CharSetEnum.EUC_KR);
        log.debug("Set Charset: {}", correctCharset.getCharSet());
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), correctCharset.getCharSet()));

    }

    private static void destroy(HttpURLConnection conn, BufferedReader br) {
        if (conn != null)
            conn.disconnect();
        try {
            if (br != null)
                br.close();
        } catch (IOException e) {
            log.error("IO Error in destory method", e);
        }
    }

}
