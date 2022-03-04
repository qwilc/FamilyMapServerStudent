package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.RegisterRequest;
import result.LoginRegisterResult;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class RegisterHandler extends Handler {
    private Logger logger = Logger.getLogger("RegisterHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (hasCorrectRequestMethod(exchange, "post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);
                requestBody.close();

                logger.info(requestData);

                Gson gson = new Gson();
                RegisterRequest request = gson.fromJson(requestData, RegisterRequest.class);

                RegisterService service = new RegisterService();
                LoginRegisterResult result = service.register(request);

                sendResponse(exchange, result);

                success = result.isSuccess();
            }

            if (!success) {
                sendBadRequestResponse(exchange);
            }
        }
        catch (IOException ex) {
            handleIOException(ex, exchange);
        }
        exchange.getResponseBody().close();
    }
}
