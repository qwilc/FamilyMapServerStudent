package handler;

import com.sun.net.httpserver.HttpExchange;
import result.Result;
import service.AllPeopleService;
import service.PersonService;

import java.io.IOException;

public class GetDataHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(hasCorrectRequestMethod(exchange, "get")) {
                String[] splitPath = getSplitPath(exchange);
                String authToken = getAuthToken(exchange);

                Result result;
                if(splitPath.length > 2) {
                    String ID = splitPath[2];
                    PersonService service = new PersonService();
                    result = service.person(authToken, ID);
                }
                else {
                    AllPeopleService service = new AllPeopleService();
                    result = service.people(authToken);
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
