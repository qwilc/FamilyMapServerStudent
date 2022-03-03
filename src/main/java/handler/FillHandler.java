package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.LoginRequest;
import result.Result;
import service.FillService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FillHandler extends Handler {
    private final Logger logger = Logger.getLogger("FillHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Level level = Level.FINE;
        logger.setLevel(level);
        logger.setUseParentHandlers(false);
        java.util.logging.Handler handler = new ConsoleHandler();
        handler.setLevel(level);
        logger.addHandler(handler);

        try {
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                String[] splitPath = getSplitPath(exchange);

                FillService service = new FillService();
                String username = splitPath[2];
                logger.fine("Username: " + username);

                Result result;
                if(splitPath.length > 3) {
                    int numGenerations = Integer.parseInt(splitPath[3]);
                    logger.fine("numGenerations: " + numGenerations);
                    result = service.fill(username, numGenerations);
                }
                else {
                    result = service.fill(username, 4);
                }

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
