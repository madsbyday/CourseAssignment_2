/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import gui.Frame;
import gui.IDataReady;

/**
 *
 * @author vfgya_000
 */
public class SwingDataReady implements IDataReady{
    
    private Frame f;

    public SwingDataReady(Frame f) {
        this.f = f;
    }
    
    

    @Override
    public void messegaeReady(String msg) {
        f.updateTextArea(msg);
    }
    
}
