package controller;

import core.EventController;
import event.events.ButtonEvent;
import event.events.JoystickEvent;
import event.listeners.ButtonListener;
import event.listeners.GRTJoystickListener;
import logger.GRTLogger;
import mechanism.GRTDriveTrain;
import sensor.GRTJoystick;

/**
 * Robot base driver.
 *
 * Operates for any DriverStation.
 *
 * @author andrew, keshav
 */
public class DriveController extends EventController implements GRTJoystickListener, ButtonListener {

    //sensor
    GRTJoystick left, right;
    
    //actuator
    private final GRTDriveTrain dt;
    
    //state
    private double leftVelocity;
    private double rightVelocity;
    
    private boolean leftTriggerHeld, rightTriggerHeld;
    
    /**
     * Creates a new driving controller.
     * 
     * @param base robot base to drive
     * @param ds driver station to control with
     * @param name name of controller
     */
    public DriveController(GRTDriveTrain dt, GRTJoystick leftStick, GRTJoystick rightStick) {
        super("Driving Controller");
        this.dt = dt;
        
        this.left = leftStick;
        this.right = rightStick;
        
        this.leftTriggerHeld = this.rightTriggerHeld = false;
    }

    protected void startListening() {
        left.addJoystickListener(this);
        left.addButtonListener(this);
        
        right.addJoystickListener(this);
        right.addButtonListener(this);
    }

    protected void stopListening() {
        left.removeJoystickListener(this);
        left.removeButtonListener(this);
        
        right.removeJoystickListener(this);
        right.removeButtonListener(this);
    }

    /**
     * Callback that reponds to changes in the X-axis
     * of the joysticks. Not implemented in this controller.
     * 
     * @param e The joystick event
     */
    public void XAxisMoved(JoystickEvent e) {
    }

    public void YAxisMoved(JoystickEvent e) {
        if ( e.getSource() == left ){
            leftVelocity = e.getData();
            System.out.println(leftVelocity);
        } else if ( e.getSource() == right ){
            rightVelocity = e.getData();
        }
        
        dt.setMotorSpeeds(leftVelocity, rightVelocity);
    }

    public void AngleChanged(JoystickEvent e) {
    }

    public void buttonPressed(ButtonEvent e) {
        
        if ( e.getButtonID() == GRTJoystick.KEY_BUTTON_TRIGGER ){
            dt.shiftDown();
            
            if( e.getSource() == left ){
                leftTriggerHeld = true;
            } else if ( e.getSource() == right ){
                rightTriggerHeld = false;
            }
        }
    }
    
    public void buttonReleased(ButtonEvent e) {
        if ( e.getButtonID() == GRTJoystick.KEY_BUTTON_TRIGGER ){
            if ( e.getSource() == left ){
                leftTriggerHeld = false;
            } else if ( e.getSource() == right ) {
                rightTriggerHeld = false;
            }
            
            //If neither trigger is still being held, then it's safe to shift back up.
            if ( ! (leftTriggerHeld || rightTriggerHeld) ){
                dt.shiftUp();
            }
            
            GRTLogger.logInfo("Buttons release? Left_Triger=" + leftTriggerHeld + "\tRightTrigger=" + rightTriggerHeld);

        }
    }
}