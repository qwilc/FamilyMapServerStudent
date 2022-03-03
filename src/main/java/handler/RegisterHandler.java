package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.RegisterRequest;
import result.LoginRegisterResult;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class RegisterHandler extends Handler {
    private Logger logger = Logger.getLogger("RegisterHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);

                logger.info(requestData);

                Gson gson = new Gson();
                RegisterRequest request = gson.fromJson(requestData, RegisterRequest.class);

                RegisterService service = new RegisterService();
                LoginRegisterResult result = service.register(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer responseBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, responseBody);
                responseBody.close();

                success = result.isSuccess();
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException ex) {
            handleIOException(ex, exchange);
        }
    }
}
