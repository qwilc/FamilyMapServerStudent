package handler;

import com.sun.net.httpserver.HttpExchange;
import result.Result;
import service.AllEventsService;
import service.EventService;

import java.io.IOException;

public class EventHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                String[] splitPath = getSplitPath(exchange);
                String authToken = getAuthToken(exchange);
                String username = splitPath[2];

                Result result;
                if(splitPath.length > 3) {
                    String eventID = splitPath[3];
                    EventService service = new EventService();
                    result = service.event(authToken, username, eventID);
                }
                else {
                    AllEventsService service = new AllEventsService();
                    result = service.events(authToken, username);
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
