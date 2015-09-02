package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileDialogs {

    private JFrame frame;
    private JPanel topPanel;
    private JButton btnNew;
    private JButton btnOpen;
    private JButton btnSave;
    private JButton btnSaveAs;
    private JTextArea textArea;

    private boolean isSave = true;

    private File activeFile;

    private void saveFile(File file, String data) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        }

    }

    private String readFile(File file) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s).append("\n");
            }
        }
        return sb.toString();
    }

    private class SaveClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activeFile != null) {

                try {
                    saveFile(activeFile, textArea.getText());
                    isSave = true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else if (!isSave) {
                saveAs();
            }
        }
    }

    private class OpenClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser();
            int res = fileChooser.showOpenDialog(frame);

            if (res == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();
                try {
                    String text = readFile(file);
                    textArea.setText(text);
                    activeFile = file;
                    isSave = true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        }
    }

    private boolean saveAs() {

        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showSaveDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {

            File fileToSave = fileChooser.getSelectedFile();

            try {
                saveFile(fileToSave, textArea.getText());
                activeFile = fileToSave;
                isSave = true;
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        return false;
    }

    private class SaveAsClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    }

    private class NewFileClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String[] options = {"Yes", "No", "Cancel"};

            int res = JOptionPane.showOptionDialog(frame,
                    "Would you save file? ",
                    "Info",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);

            switch (res) {
                case JOptionPane.YES_OPTION:
                    saveAs();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }

            createNewFile();
        }
    }

    public void createNewFile() {
        textArea.setText("");
    }

    public FileDialogs() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setTitle("Text editor");
        frame.setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 125, 125));
        topPanel.setLayout(new GridLayout(1, 4));

        btnNew = new JButton("New");
        btnOpen = new JButton("Open");
        btnSave = new JButton("Save");
        btnSaveAs = new JButton("Save as");

        btnOpen.addActionListener(new OpenClick());
        btnSave.addActionListener(new SaveClick());
        btnSaveAs.addActionListener(new SaveAsClick());
        btnNew.addActionListener(new NewFileClick());

        topPanel.add(btnNew);
        topPanel.add(btnOpen);
        topPanel.add(btnSave);
        topPanel.add(btnSaveAs);

        textArea = new JTextArea();

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                isSave = false;
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                closeAndSave();

            }

        });


        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea));

        frame.setVisible(true);

    }

    public void closeAndSave() {
        if (activeFile != null && !isSave) {

            String[] options = {"Yes", "No", "Cancel"};

            int res = JOptionPane.showOptionDialog(frame,
                    "Would you save file? ",
                    "Info",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);

            switch (res) {
                case JOptionPane.YES_OPTION:

                    try {

                        saveFile(activeFile, textArea.getText());
                        frame.dispose();

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                frame,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }


                    break;
                case JOptionPane.NO_OPTION:
                    frame.dispose();
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }

        } else {
            frame.dispose();
        }
    }


    public static void main(String[] args) {
        FileDialogs dialog = new FileDialogs();
    }

}
