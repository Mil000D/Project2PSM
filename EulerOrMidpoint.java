/**
 * @author Miłosz Demendecki s24611
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EulerOrMidpoint extends JFrame {

    public static void main(String[] args) {
        createFrame();
    }

    /**
     * Function that adds records from list specified as parameter to text area,
     * so user can easily copy records and paste it to e.g. excel
     */
    public static void addListToArea(ArrayList<Double> list, String str, JTextArea textArea, JButton button,int rows){
        for (int i = 0; i < rows; i++) {
            textArea.append(list.get(i).toString().replace(".",",") + "\n");
            button.setText(str);
        }
    }

    /**
     * Constructor that takes multiple parameters to calculate all values needed to simulate projectile motion
     * and adds them to corresponding lists. After that correlated text areas and buttons are added to help user to utilize
     * calculated data easily.
     */
    public EulerOrMidpoint(int rows, double vx, double vy, double mass, double k, double dt, boolean eulerOrNot) {
        int gx = 0;
        int gy = -10;

        ArrayList<Double> sxList = new ArrayList<>();
        sxList.add(0.0);
        ArrayList<Double> syList = new ArrayList<>();
        syList.add(0.0);

        ArrayList<Double> vxList = new ArrayList<>();
        vxList.add(vx);
        ArrayList<Double> vyList = new ArrayList<>();
        vyList.add(vy);

        ArrayList<Double> axList = new ArrayList<>();
        ArrayList<Double> ayList = new ArrayList<>();

        ArrayList<Double> dsxList = new ArrayList<>();
        ArrayList<Double> dsyList = new ArrayList<>();

        ArrayList<Double> dvxList = new ArrayList<>();
        ArrayList<Double> dvyList = new ArrayList<>();

        for (int i = 0; i < rows; i++){
            axList.add(((mass * gx) - (k * vxList.get(i)))/mass);
            dvxList.add(axList.get(i) * dt);
            vxList.add(vxList.get(i) + dvxList.get(i));
            dsxList.add(vxList.get(i) * dt);
            sxList.add(sxList.get(i) + dsxList.get(i));

            ayList.add(((mass * gy) - (k * vyList.get(i)))/mass);
            dvyList.add(ayList.get(i) * dt);
            vyList.add(vyList.get(i) + dvyList.get(i));
            dsyList.add(vyList.get(i) * dt);
            syList.add(syList.get(i) + dsyList.get(i));
        }

        setTitle("Scrollable Numbers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        int columns = 10;

        if(!eulerOrNot){
            columns = 14;
        }
        for (int i = 1; i <= columns; i++) {
            JTextArea textArea = new JTextArea();
            JButton copyButton = new JButton();
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            textArea.setEditable(false);
            switch (i) {
                case 1 -> addListToArea(sxList,"Copy Sx", textArea, copyButton, rows);
                case 2 -> addListToArea(syList,"Copy Sy", textArea, copyButton, rows);
                case 3 -> addListToArea(vxList,"Copy Vx", textArea, copyButton, rows);
                case 4 -> addListToArea(vyList,"Copy Vy", textArea, copyButton, rows);
                case 5 -> addListToArea(axList,"Copy Ax", textArea, copyButton, rows);
                case 6 -> addListToArea(ayList,"Copy Ay", textArea, copyButton, rows);
                case 7 -> addListToArea(dsxList,"Copy dSx",textArea, copyButton, rows);
                case 8 -> addListToArea(dsyList, "Copy dSy", textArea, copyButton, rows);
                case 9 -> addListToArea(dvxList, "Copy dVx", textArea, copyButton, rows);
                case 10 -> addListToArea(dvyList,"Copy dVy", textArea, copyButton, rows);
                case 11 -> {} // TODO: 17.03.2023  
                case 12 -> {} // TODO: 17.03.2023 
                case 13 -> {} // TODO: 17.03.2023 
                case 14 -> {} // TODO: 17.03.2023  
            }
            copyButton.addActionListener(e -> {
                textArea.selectAll();
                textArea.copy();
            });
            JPanel scrollPanel = new JPanel(new BorderLayout());
            scrollPanel.add(copyButton, BorderLayout.NORTH);
            scrollPanel.add(scrollPane, BorderLayout.CENTER);
            panel.add(scrollPanel);
        }
        getContentPane().add(panel);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Function that creates user interface
     */
    public static void createFrame(){
        JFrame startFrame = new JFrame("Choose Calculation");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton calculateButton = new JButton("Calculate using Euler method");
        JLabel label1 = new JLabel("Provide number of rows :");
        JTextField textField = new JTextField();

        JLabel label2 = new JLabel("Provide Vx value:");
        JTextField vxField = new JTextField();

        JLabel label3 = new JLabel("Provide Vy value:");
        JTextField vyField = new JTextField();

        JLabel label4 = new JLabel("Provide mass of an object:");
        JTextField massField = new JTextField();

        JLabel label5 = new JLabel("Provide k value:");
        JTextField kField = new JTextField();

        JLabel label6 = new JLabel("Provide dt value:");
        JTextField deltaTField = new JTextField();

        calculateButton.addActionListener(e -> {
            try {
                new EulerOrMidpoint(Integer.parseInt(textField.getText()), Double.parseDouble(vxField.getText()),
                        Double.parseDouble(vyField.getText()),Double.parseDouble(massField.getText()),
                        Double.parseDouble(kField.getText()),Double.parseDouble(deltaTField.getText()), true);
            } catch (NumberFormatException ex){
                System.out.println("Try again, remember to pass all arguments");
            }
        });

        JButton calculateButton2 = new JButton("Calculate using Midpoint method");
        calculateButton2.addActionListener(e -> {
            try {
                new EulerOrMidpoint(Integer.parseInt(textField.getText()), Double.parseDouble(vxField.getText()),
                        Double.parseDouble(vyField.getText()),Double.parseDouble(massField.getText()),
                        Double.parseDouble(kField.getText()),Double.parseDouble(deltaTField.getText()), false);
            } catch (NumberFormatException ex){
                System.out.println("Try again, remember to pass all arguments");
            }
        });

        startFrame.setResizable(true);
        panel.add(label1);
        panel.add(textField);

        panel.add(label2);
        panel.add(vxField);

        panel.add(label3);
        panel.add(vyField);

        panel.add(label4);
        panel.add(massField);

        panel.add(label5);
        panel.add(kField);

        panel.add(label6);
        panel.add(deltaTField);

        panel.add(calculateButton);
        panel.add(calculateButton2);

        startFrame.getContentPane().add(panel);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
        startFrame.pack();
    }
}