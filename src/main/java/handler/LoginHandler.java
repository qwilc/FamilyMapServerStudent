package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.LoginRequest;
import result.LoginRegisterResult;
import service.LoginService;

import java.io.*;
import java.util.logging.Logger;

public class LoginHandler extends Handler {
    private final Logger logger = Logger.getLogger("LoginHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);
                requestBody.close();

                logger.info(requestData);

                Gson gson = new Gson();
                LoginRequest request = gson.fromJson(requestData, LoginRequest.class);

                LoginService service = new LoginService();
                LoginRegisterResult result = service.login(request);

                success = result.isSuccess();
                logger.info(result.getMessage());

                if(success) {
                    sendResponse(exchange, result);
                }
            }

            if (!success) {
                sendBadRequestResponse(exchange);
            }
        }
        catch(IOException ex) {
            handleIOException(ex, exchange);
        }
        logger.info("Rcode: " + exchange.getResponseCode());
        exchange.getResponseBody().close();
    }
}
