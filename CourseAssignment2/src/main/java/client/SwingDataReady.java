/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import gui.Frame;
import gui.IDataReady;

/**
 *
 * @author vfgya_000
 */
public class SwingDataReady implements IDataReady {

    private Frame f;

    public SwingDataReady(Frame f) {
        this.f = f;
    }

    @Override
    public void messegaeReady(String msg) {
        String part[] = msg.split(":");
        if (part[0].equals("MSGRES")) {
            msg = part[1] + " -> " + part[2];
            f.updateTextArea(msg);
        }
        if (part[0].equals("CLIENTLIST")) {
            msg = "Users online: " + part[1];
            f.updateTextArea(msg);
        }
    }

}
