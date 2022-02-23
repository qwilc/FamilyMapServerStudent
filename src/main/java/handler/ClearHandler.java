package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import result.Result;
import service.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearService service = new ClearService();
                Result result = service.clear();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();

                Gson gson = new Gson();
                gson.toJson((Object) result, (Type) resBody); //TODO: Are those castings right?
                resBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
