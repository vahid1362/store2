import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmMain  extends JFrame{
    private JPanel mainPnl;
    private JMenu File;
    private JMenuItem New;

    public  frmMain()
    {
        setContentPane(mainPnl);
        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog( frmMain.this,
                        "This is an example\nof using menus",
                        "About", JOptionPane.PLAIN_MESSAGE );
            }
        });
    }
}
