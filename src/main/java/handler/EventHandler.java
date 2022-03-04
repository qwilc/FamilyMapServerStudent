package handler;

import com.sun.net.httpserver.HttpExchange;
import result.Result;
import service.AllEventsService;
import service.EventService;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.Logging;

public class EventHandler extends Handler {
    private final Logger logger = Logger.getLogger("EventHandler");
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Logging.initializeLogger(logger, Level.FINER);

        try {
            if(hasCorrectRequestMethod(exchange, "get")) {
                String[] splitPath = getSplitPath(exchange);

                logger.finest("[0]: " + splitPath[0] +
                        "\n [1]: " + splitPath[1] +
                        "Length: " + splitPath.length);

                String authToken = getAuthToken(exchange);

                Result result;
                if(splitPath.length > 2) {
                    String eventID = splitPath[2];
                    EventService service = new EventService();
                    result = service.event(authToken, eventID);
                }
                else {
                    AllEventsService service = new AllEventsService();
                    result = service.events(authToken);
                }

                sendResponse(exchange, result);

                success = result.isSuccess();
            }

            if(!success) {
                sendBadRequestResponse(exchange);
            }
        }
        catch (IOException ex) {
            handleIOException(ex, exchange);
        }
        exchange.getResponseBody().close();
    }
}
