package service;

import result.Result;

/**
 * The FillService class performs the functionality for fill requests
 */
public class FillService {
    /**
     * Generates the specified number of generations for the user indicated by the given username
     *
     * @param username the username
     * @param numGenerations the number of generations
     * @return a Result object indicating the outcome of the request
     */
    public Result fill(String username, int numGenerations) {
        return null;
    }

    /**
     * Generates the default of four generations for the user indicated by the given username
     * @param username the username
     * @return a Result object indicating the outcome of the request
     */
    public Result fill(String username) {
        return fill(username,4);
    }
}
