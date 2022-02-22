package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.logging.Logger;

public class FileHandler extends Handler {
    private static Logger logger = Logger.getLogger("FileHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                String urlPath = exchange.getRequestURI().toString();

                if(urlPath == null || urlPath.equals("/")) { //TODO: Could have FilePath(urlPath) method
                    urlPath = "/index.html";
                }
                logger.fine(urlPath);

                String filePath = "web" + urlPath;
                logger.fine(filePath);

                File file = new File(filePath);

                if(file.exists()) {
                    OutputStream responseBody = exchange.getResponseBody(); //TODO: Could have WriteToOutputStream(exchange, file) method
                    Files.copy(file.toPath(), responseBody);
                    responseBody.close();

                    success = true;
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }

            if(!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //TODO: Non-deprecated error?
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }



}
