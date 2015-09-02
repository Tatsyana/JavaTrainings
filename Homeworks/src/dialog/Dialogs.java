package dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Tatsyana.
 */

public class Dialogs {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(400,300);


        JButton btn = new JButton("Open Dialog");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileopen = new JFileChooser();

                int ret = fileopen.showDialog(null, "File Open");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    /*
                     * Какие-то действия.
                     */
                    System.out.println(file);
                }

            }
        });

        f.add(btn);

        f.setVisible(true);
    }
}
