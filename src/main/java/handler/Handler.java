package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import result.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public abstract class Handler implements HttpHandler {
    protected boolean success = false;

    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

    protected void handleIOException(Throwable exception, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
        exchange.getResponseBody().close();
        exception.printStackTrace();
    }

    protected String getRequestData(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        return readString(requestBody);

    }

    protected String[] getSplitPath(HttpExchange exchange) {
        String url = exchange.getRequestURI().getPath();
        return url.split("/");
    }

    protected String getAuthToken(HttpExchange exchange) {
        if(exchange.getRequestHeaders().containsKey("Authorization")) {
            return exchange.getRequestHeaders().getFirst("Authorization");
        }
        else {
            return null;
        }
    }

    protected void sendResponseBody(HttpExchange exchange, Result result) throws IOException {
        Writer responseBody = new OutputStreamWriter(exchange.getResponseBody());

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Gson gson = new Gson();
        gson.toJson(result, responseBody);
        responseBody.close();
    }

    protected void sendBadRequestResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
    }

    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024]; //TODO: Is this right/good?
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
