package main.java;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Window extends JFrame{
    Window(){
        try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.exit(0);
    }

        this.addWindowListener(new Closer());

        JPanel panel = new JPanel();
        JMenuBar menuBar=new JMenuBar();

        JMenu fileMenu=new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand("New");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");
        JTextArea area=new JTextArea();

        ActionListener menuItemListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String ingest = null;
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Choose destination.");
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
                String ae = e.getActionCommand();
                if (ae.equals("Open")) {
                    int returnValue = jfc.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    try{
                        FileReader read = new FileReader(f);
                        Scanner scan = new Scanner(read);
                        while(scan.hasNextLine()){
                            String line = scan.nextLine() + "\n";
                            ingest = ingest + line;
                    }
                        area.setText(ingest);
                    }
                catch ( FileNotFoundException ex) { ex.printStackTrace(); }
            }
                // SAVE
                } else if (ae.equals("Save")) {
                    int returnValue = jfc.showSaveDialog(null);
                    try {
                        File f = new File(jfc.getSelectedFile().getAbsolutePath());
                        FileWriter out = new FileWriter(f);
                        out.write(area.getText());
                        out.close();
                    } catch (FileNotFoundException ex) {
                        Component f = null;
                        JOptionPane.showMessageDialog(f,"File not found.");
                    } catch (IOException ex) {
                        Component f = null;
                        JOptionPane.showMessageDialog(f,"Error.");
                    }
                } else if (ae.equals("New")) {
                    area.setText("");
                } else if (ae.equals("Quit")) { System.exit(0); }
              }
        };
        panel.add(area);
        newMenuItem.addActionListener(menuItemListener);
        openMenuItem.addActionListener(menuItemListener);
        saveMenuItem.addActionListener(menuItemListener);

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        

        this.setJMenuBar(menuBar);
        this.add(panel);
        this.setBounds(50,0, 800, 600);
        panel.setBounds(0,0,700,540);
        panel.setBackground(Color.BLACK);
        this.setVisible(true);

    }
}
class Closer extends WindowAdapter{
    public void windowClosing(WindowEvent we){
        System.exit(0);
    }
}
