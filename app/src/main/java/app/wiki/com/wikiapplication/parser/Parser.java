package app.wiki.com.wikiapplication.parser;

import org.json.JSONException;

import app.wiki.com.wikiapplication.model.ApiResponse;
/**
 * <h1>Parser</h1>
 *
 * Base parser class
 * @author Reshma Salim
 * @version 1.0
 */
public interface Parser<T extends ApiResponse>
{
    T parse(String response) throws JSONException;
}
