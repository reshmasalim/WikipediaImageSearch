package app.wiki.com.wikiapplication.utility;

import app.wiki.com.wikiapplication.model.ApiResponse;
/**
 * <h1>ServerDataListener</h1>
 *
 * Listener for response from the server.
 *
 * @author Reshma Salim
 * @version 1.0
 */
public interface ServerDataListener
{
    void onComplete(ApiResponse apiResponse);

    void onFailure(String errorMessage);
}
