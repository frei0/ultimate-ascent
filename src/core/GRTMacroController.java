/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import event.events.MacroEvent;
import event.listeners.EncoderListener;
import event.listeners.MacroListener;
import java.util.Enumeration;
import java.util.Vector;
import logger.GRTLogger;

/**
 * Creates a new MacroController that executes macros in sequence.
 * 
 * @author keshav
 */
public class GRTMacroController extends EventController implements MacroListener {
    private Vector macros;
    private int currentIndex = 0;
            
    public GRTMacroController(Vector macros){
        super("Macro controller");
        this.macros = macros;        
    }

    protected void startListening() {
        GRTLogger.logInfo("start listen");
        currentIndex = 0;
        for (Enumeration en = macros.elements(); en.hasMoreElements();) {
            GRTMacro m = (GRTMacro) en.nextElement();
            m.reset();
            m.addListener(this);
            
        }
        
        ((GRTMacro) macros.elementAt(currentIndex)).execute();
    }

    protected void stopListening() {
        for (Enumeration en = macros.elements(); en.hasMoreElements();)
            ((GRTMacro) en.nextElement()).removeListener(this);    
    }   

    public void macroInitialized(MacroEvent e) {
        GRTLogger.logInfo("Initialized macro: " + e.getSource());
    }

    public void macroDone(MacroEvent e) {
        GRTLogger.logInfo("Completed macro: " + e.getSource());
        currentIndex++;
        if(currentIndex < macros.size()){
            ((GRTMacro) macros.elementAt(currentIndex)).execute();
        } else {
            GRTLogger.logSuccess("Completed all macros. Waiting for teleop!");
            return;
        }
    }

    public void macroTimedOut(MacroEvent e) {
        GRTLogger.logError("Macro timed out. Skipping macros.");        
    }
     
}
