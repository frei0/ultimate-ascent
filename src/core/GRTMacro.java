/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import deploy.GRTRobot;

/**
 *
 * @author student
 */
public abstract class GRTMacro extends EventController {

    protected boolean hasCompletedExecution = false;

    public GRTMacro(String name) {
        super(name);
    }

    /**
     * A GRTMacro will perform its desired function, and then quietly die.
     *
     * @param robot
     */
    public void execute(GRTRobot robot) {
        while (!hasCompletedExecution) {
            perform();
        }
        die();
    }

    /**
     * Implemented on a per-macro basis
     */
    protected abstract void perform();

    /**
     * After executing
     */
    public abstract void die();
}
