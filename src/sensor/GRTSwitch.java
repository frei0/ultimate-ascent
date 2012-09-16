/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.Sensor;
import edu.wpi.first.wpilibj.DigitalInput;
import event.events.SwitchEvent;
import event.listeners.SwitchListener;
import java.util.Vector;

/**
 *
 * @author gerberduffy
 */
public class GRTSwitch extends Sensor {
    
    private DigitalInput in;
    
    private static final int STATE = 0;
    private static final int NUM_DATA = 1;
    
    private Vector listeners;
    
    public GRTSwitch(int slot, int polltime, String id){
        
        super(id, polltime, NUM_DATA);
        
        in = new DigitalInput(slot);
        
        listeners = new Vector();
    }
    
    public boolean isOn(){
        return in.get();
    }

    protected void poll() {
        setState(STATE, isOn() ? TRUE : FALSE);
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        
        SwitchEvent e = new SwitchEvent(this, newDatum);
        
        for (int i=0; i < listeners.size(); i++){
            ((SwitchListener)listeners.elementAt(i)).switchStateChanged(e);
        }
    }

    
}
