/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

import intranetchatv3.display.*;
import intranetchatv3.df.VariableStore;
import intranetchatv3.saving.VariableSave;

/**
 *
 * @author Philip
 */
public class Main {
    VariableStore store;
    NetworkInterface net;
    MainDisplay display;
    Splash splash;

    public Main(){
        store = VariableStore.getInstance();
        (new Thread(new FontThread())).start();
    }

    public void startNetwork(){
        net = NetworkInterface.getInstance();
        (new Thread(new NetworkListener())).start();
    }

    public void variableSearch(){
        VariableSave vs = new VariableSave();
        if(vs.isSavePresent()){
            vs.loadValues();
        }else{
            vs.saveValues();
        }
    }

    public void startDisplay(){
        display = MainDisplay.getInstance();
    }

    public void showDisplay(){
        display.setVisible(true);
    }

    public void startSplash(){
        splash = new Splash();
        splash.setVisible(true);
    }

    public void closeSplash(){
        splash.setVisible(false);
        splash.dispose();
    }
    
    public static void main(String args[]){
        Main m = new Main();
        m.startSplash();
        m.variableSearch();
        m.startNetwork();
        m.startDisplay();
        m.closeSplash();
        m.showDisplay();
    }
}
