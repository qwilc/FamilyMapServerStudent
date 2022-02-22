package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class Handler implements HttpHandler {
    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;
}
