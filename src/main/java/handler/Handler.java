package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public abstract class Handler implements HttpHandler {
    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

    protected void handleException(Throwable exception, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
        exchange.getResponseBody().close();
        exception.printStackTrace();
    }
}
