package table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Uzed on 03.08.2015.
 */
public class WindowTable extends JDialog{

    public WindowTable(String name, String number, String note){
        this.setTitle("Window Table");
        this.setModal(true);// теперь не можем переключится, пок ане закроем его
        this.setSize(400,200);
        this.setLayout(new GridLayout(4,2));
        // добавляем переданные значения на форму:
        this.add(new JLabel("Name: "));// this.add(new TextField());
        this.add(new JLabel(name));

        this.add(new JLabel("Number: "));
        this.add(new JLabel(number));

        this.add(new JLabel("Note: "));
        this.add(new JLabel(note));

        JButton button = new JButton("Close");
        final JDialog dialog = this;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);// закрывается по нажатию кнопки (событие: нажатие на кнопку - окошко становится невидимым)
            }
        });
        this.add(button);

  //      field = new JTextField();
  //     this.add(field);
    }
    JTextField field;

    public  void  showDialog(){

        this.setVisible(true);
   //     System.out.println(field.getText());

    }
    public static void main(String[] args) {

    }

}
