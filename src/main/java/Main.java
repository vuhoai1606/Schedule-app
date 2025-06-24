import view.LoginFormGUI;
import view.ScheduleAppGUI;
import view.VerificationDialog;
//import view.ScheduleAppGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                // instantiate a LoginFormGUI obj and make it visible
//                new ScheduleAppGUI().setVisible(true);
                new LoginFormGUI().setVisible(true);
            }
        });
    }
}