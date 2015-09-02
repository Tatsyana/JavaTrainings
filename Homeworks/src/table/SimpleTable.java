package table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Uzed on 03.08.2015.
 */
public class SimpleTable {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400,300);
        frame.setTitle("Simple Table");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Object[] columns = {"Name", "Number", "Note"};
        Object [] [] data = {
                {"Name1", "Number1", "Note1"},
                {"Name2", "Number2", "Note2"},
                {"Name3", "Number3", "Note3"},
        };

        final TableModel tableModel = new MyModel(data, columns);
        final JTable table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) { // когда 2 раза щелчок
                    JTable target = (JTable) e.getSource();// получаем ссылку на таблицу и преобразуем ее к таблице
                    int row = target.getSelectedRow();// выделяем из таблицы строку
                    String name = target.getModel().getValueAt(row, 0).toString();// чтобы можно было передвигать и выводить верно - .getModel()
                    String number = target.getModel().getValueAt(row, 1).toString();
                    String note = target.getModel().getValueAt(row, 2).toString();
//                    JOptionPane.showMessageDialog(null, row);
                    WindowTable dialog = new WindowTable(name, number,note);// создали свой тип окошка
                    dialog.showDialog();

                }
            }
        });


        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//чтобы  можно было менять размеры столбцов  в таблице
        frame.add(new JScrollPane(table), BorderLayout.CENTER);//добавляем скроллинг (+ заголовки добавляются вмесе со скроллингом), а в него - таблицу
        table.setAutoCreateRowSorter(true);// сортировка по разным критериям в таблице


        JButton button = new JButton("button ADD");
        frame.add(button, BorderLayout.NORTH);
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                String str = JOptionPane.showInputDialog(null, "Enter record");
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//
//                String [] row = str.split(";");// сделали split введенной строки
//                model.addRow(row);// обавили в таблицу введенную строку
//               // System.out.println(str);
//               // JOptionPane.showMessageDialog(null, "Click", "My alert", JOptionPane.QUESTION_MESSAGE);
//            }
//        });
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String name = JOptionPane.showInputDialog(null, "Enter name");
//                String number = JOptionPane.showInputDialog(null, "Enter number");
//                String note = JOptionPane.showInputDialog(null, "Enter note");
//                String[] row = {name, number, note};
//
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                model.addRow(row);// обавили в таблицу введенную строку
//               // System.out.println(str);
//               // JOptionPane.showMessageDialog(null, "Click", "My alert", JOptionPane.QUESTION_MESSAGE);
//            }
//        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField(5);
                JTextField number = new JTextField(5);
                JTextField note = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Name:"));
                myPanel.add(name);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Number:"));
                myPanel.add(number);
                myPanel.add(Box.createHorizontalStrut(15));
                myPanel.add(new JLabel("Note:"));
                myPanel.add(note);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please Enter name and  number and note", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("name: " + name.getText());
                    System.out.println("number: " + number.getText());
                    System.out.println("note: " + note.getText());
                    String [] row = {name.getText(),number.getText(),note.getText()};
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(row);

                }

            }
        });




        frame.setVisible(true);
    }

}


class MyModel extends DefaultTableModel{

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;// указываем, что редактировать строки нельзя (пришлось по этой прчине создать свою модель)
    }

    MyModel(Object[][] data, Object[] column){
        super(data,column);
    }
}