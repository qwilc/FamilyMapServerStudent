package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import result.Result;
import service.ClearService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearService service = new ClearService();
                Result result = service.clear();

                sendResponseBody(exchange, result);

                success = result.isSuccess();
            }

            if (!success) {
                sendBadRequestResponse(exchange);
            }
        }
        catch(IOException ex) {
            handleIOException(ex, exchange);
        }
    }
}
