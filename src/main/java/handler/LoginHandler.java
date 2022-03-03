package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.LoginRequest;
import result.LoginRegisterResult;
import service.LoginService;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class LoginHandler extends Handler {
    private final Logger logger = Logger.getLogger("LoginHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            LoginRegisterResult result = null;
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);

                logger.info(requestData);

                Gson gson = new Gson();
                LoginRequest request = gson.fromJson(requestData, LoginRequest.class);

                LoginService service = new LoginService();
                result = service.login(request);

                success = result.isSuccess();
            }

            if (!success) {
                sendBadRequestResponse(exchange);
            }

            sendResponseBody(exchange, result);
        }
        catch(IOException ex) {
            handleIOException(ex, exchange);
        }
    }
}
