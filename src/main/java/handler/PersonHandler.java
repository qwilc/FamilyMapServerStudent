package handler;

import com.sun.net.httpserver.HttpExchange;
import result.Result;
import service.AllPeopleService;
import service.PersonService;

import java.io.IOException;

public class PersonHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                String[] splitPath = getSplitPath(exchange);
                String authToken = getAuthToken(exchange);
                String username = splitPath[2];

                Result result;
                if(splitPath.length > 3) {
                    String personID = splitPath[3];
                    PersonService service = new PersonService();
                    result = service.person(authToken, username, personID);
                }
                else {
                    AllPeopleService service = new AllPeopleService();
                    result = service.people(authToken, username);
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
