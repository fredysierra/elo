package io.fysus.elo.controller;

/**
 * Every option in {@link io.fysus.elo.EloApplication} is handled by a Controller class. The specific action of the
 * Option is implemented in the execute method.
 */
public interface Controller {

    /**
     * Controller handler execution method.
     * @param args contains parameters for the specific option.
     */
    void execute(String[] args);

}
