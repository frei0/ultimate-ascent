/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event.events;

import sensor.GRTXBoxJoystick;

/**
 *
 * @author student
 */
public class XboxJoystickEvent {

    private GRTXBoxJoystick source;
    private int id;
    private double value;

    public XboxJoystickEvent(GRTXBoxJoystick source, int id, double value) {
        this.source = source;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public GRTXBoxJoystick getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }
}