package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.LoginRequest;
import result.LoginRegisterResult;
import service.LoginService;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class LoginHandler extends Handler {
    private Logger logger = Logger.getLogger("LoginHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);

                logger.info(requestData);

                Gson gson = new Gson();
                LoginRequest request = gson.fromJson(requestData, LoginRequest.class);

                LoginService service = new LoginService();
                LoginRegisterResult result = service.login(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer responseBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, responseBody);
                responseBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException ex) {
            handleException(ex, exchange);
        }
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
