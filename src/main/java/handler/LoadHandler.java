package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.LoadRequest;
import result.Result;
import service.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class LoadHandler extends Handler {
    private final Logger logger = Logger.getLogger("LoadHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);
                requestBody.close();

                logger.fine(requestData);

                Gson gson = new Gson();
                LoadRequest request = gson.fromJson(requestData, LoadRequest.class);

                LoadService service = new LoadService();
                Result result = service.load(request);

                sendResponse(exchange, result);

                logger.info(result.getMessage());

                success = result.isSuccess();
            }

            if (!success) {
                sendBadRequestResponse(exchange);
            }
        }
        catch (IOException ex) {
            handleIOException(ex, exchange);
        }
        logger.info("Rcode: " + exchange.getResponseCode());
        exchange.getResponseBody().close();
    }
}
