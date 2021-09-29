import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Calc implements ActionListener {
    JTextField textField;
    JButton[] digitButtons;
    JButton undoButton, clearButton, clearAllButton, memoryPlusButton, memoryMinusButton, memoryClearButton,
            memoryRecallButton, divButton, multButton, plusButton, minusButton, sqrButton, sqrtButton, eqButton,
            percentButton, commaButton;

    JFrame frame;
    Container container;

    GridBagLayout gridLayout;
    GridBagConstraints gridConstraints;

    public void actionPerformed(ActionEvent actionEvent) {

    }

    void init() {
        frame = new JFrame();
        container = frame.getContentPane();

        gridLayout = new GridBagLayout();
        gridConstraints = new GridBagConstraints();

        gridConstraints.fill = GridBagConstraints.BOTH;

        container.setLayout(gridLayout);

        digitButtons = new JButton[10];

        // text field
        textField = new JTextField(5);
        textField.addActionListener(this);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 5;
        gridConstraints.gridheight = 1;
        gridConstraints.insets = new Insets(5, 5, 5, 5);
        gridLayout.setConstraints(textField, gridConstraints);
        container.add(textField);

        // common
        gridConstraints.gridwidth = 1;
        gridConstraints.gridheight = 1;
        gridConstraints.insets = new Insets(5, 5, 5, 5);

        // row 1
        gridConstraints.gridy = 1;

        // button: M+
        memoryPlusButton = new JButton("M+");
        memoryPlusButton.addActionListener(this);
        memoryPlusButton.setFocusable(false);
        gridConstraints.gridx = 0;
        gridLayout.setConstraints(memoryPlusButton, gridConstraints);
        container.add(memoryPlusButton);

        // button: M-
        memoryMinusButton = new JButton("M-");
        memoryMinusButton.addActionListener(this);
        memoryMinusButton.setFocusable(false);
        gridConstraints.gridx = 1;
        gridLayout.setConstraints(memoryMinusButton, gridConstraints);
        container.add(memoryMinusButton);

        // button: MC
        memoryClearButton = new JButton("MC");
        memoryClearButton.addActionListener(this);
        memoryClearButton.setFocusable(false);
        gridConstraints.gridx = 2;
        gridLayout.setConstraints(memoryClearButton, gridConstraints);
        container.add(memoryClearButton);

        // button: MR
        memoryRecallButton = new JButton("MR");
        memoryRecallButton.addActionListener(this);
        memoryRecallButton.setFocusable(false);
        gridConstraints.gridx = 3;
        gridLayout.setConstraints(memoryRecallButton, gridConstraints);
        container.add(memoryRecallButton);

        // button: %
        percentButton = new JButton("%");
        percentButton.addActionListener(this);
        percentButton.setFocusable(false);
        gridConstraints.gridx = 4;
        gridLayout.setConstraints(percentButton, gridConstraints);
        container.add(percentButton);

        // row 2
        gridConstraints.gridy = 2;

        // button: AC
        clearAllButton = new JButton("AC");
        clearAllButton.addActionListener(this);
        clearAllButton.setFocusable(false);
        gridConstraints.gridx = 0;
        gridLayout.setConstraints(clearAllButton, gridConstraints);
        container.add(clearAllButton);

        // button: C
        clearButton = new JButton("C");
        clearButton.addActionListener(this);
        clearButton.setFocusable(false);
        gridConstraints.gridx = 1;
        gridLayout.setConstraints(clearButton, gridConstraints);
        container.add(clearButton);

        // button: <-
        undoButton = new JButton("<--");
        undoButton.addActionListener(this);
        undoButton.setFocusable(false);
        gridConstraints.gridx = 2;
        gridLayout.setConstraints(undoButton, gridConstraints);
        container.add(undoButton);

        // button: /
        divButton = new JButton("/");
        divButton.addActionListener(this);
        divButton.setFocusable(false);
        gridConstraints.gridx = 3;
        gridLayout.setConstraints(divButton, gridConstraints);
        container.add(divButton);

        // button: sqrt
        sqrtButton = new JButton("sqrt");
        sqrtButton.addActionListener(this);
        sqrtButton.setFocusable(false);
        gridConstraints.gridx = 4;
        gridLayout.setConstraints(sqrtButton, gridConstraints);
        container.add(sqrtButton);

        // row 3
        gridConstraints.gridy = 3;

        // button: 7
        digitButtons[7] = new JButton("7");
        digitButtons[7].addActionListener(this);
        digitButtons[7].setFocusable(false);
        gridConstraints.gridx = 0;
        gridLayout.setConstraints(digitButtons[7], gridConstraints);
        container.add(digitButtons[7]);

        // button: 8
        digitButtons[8] = new JButton("8");
        digitButtons[8].addActionListener(this);
        digitButtons[8].setFocusable(false);
        gridConstraints.gridx = 1;
        gridLayout.setConstraints(digitButtons[8], gridConstraints);
        container.add(digitButtons[8]);

        // button: 9
        digitButtons[9] = new JButton("9");
        digitButtons[9].addActionListener(this);
        digitButtons[9].setFocusable(false);
        gridConstraints.gridx = 2;
        gridLayout.setConstraints(digitButtons[9], gridConstraints);
        container.add(digitButtons[9]);

        // button: *
        multButton = new JButton("*");
        multButton.addActionListener(this);
        multButton.setFocusable(false);
        gridConstraints.gridx = 3;
        gridLayout.setConstraints(multButton, gridConstraints);
        container.add(multButton);

        // button: x^2
        sqrButton = new JButton("x^2");
        sqrButton.addActionListener(this);
        sqrButton.setFocusable(false);
        gridConstraints.gridx = 4;
        gridLayout.setConstraints(sqrButton, gridConstraints);
        container.add(sqrButton);

        // row 4
        gridConstraints.gridy = 4;

        // button: 4
        digitButtons[4] = new JButton("4");
        digitButtons[4].addActionListener(this);
        digitButtons[4].setFocusable(false);
        gridConstraints.gridx = 0;
        gridLayout.setConstraints(digitButtons[4], gridConstraints);
        container.add(digitButtons[4]);

        // button: 5
        digitButtons[5] = new JButton("5");
        digitButtons[5].addActionListener(this);
        digitButtons[5].setFocusable(false);
        gridConstraints.gridx = 1;
        gridLayout.setConstraints(digitButtons[5], gridConstraints);
        container.add(digitButtons[5]);

        // button: 6
        digitButtons[6] = new JButton("6");
        digitButtons[6].addActionListener(this);
        digitButtons[6].setFocusable(false);
        gridConstraints.gridx = 2;
        gridLayout.setConstraints(digitButtons[6], gridConstraints);
        container.add(digitButtons[6]);

        // button: -
        minusButton = new JButton("-");
        minusButton.addActionListener(this);
        minusButton.setFocusable(false);
        gridConstraints.gridx = 3;
        gridLayout.setConstraints(minusButton, gridConstraints);
        container.add(minusButton);

        // button: +
        plusButton = new JButton("+");
        plusButton.addActionListener(this);
        plusButton.setFocusable(false);
        gridConstraints.gridx = 4;
        gridLayout.setConstraints(plusButton, gridConstraints);
        container.add(plusButton);

        // row 5
        gridConstraints.gridy = 5;

        // button: 1
        digitButtons[1] = new JButton("1");
        digitButtons[1].addActionListener(this);
        digitButtons[1].setFocusable(false);
        gridConstraints.gridx = 0;
        gridLayout.setConstraints(digitButtons[1], gridConstraints);
        container.add(digitButtons[1]);

        // button: 2
        digitButtons[2] = new JButton("2");
        digitButtons[2].addActionListener(this);
        digitButtons[2].setFocusable(false);
        gridConstraints.gridx = 1;
        gridLayout.setConstraints(digitButtons[2], gridConstraints);
        container.add(digitButtons[2]);

        // button: 3
        digitButtons[3] = new JButton("3");
        digitButtons[3].addActionListener(this);
        digitButtons[3].setFocusable(false);
        gridConstraints.gridx = 2;
        gridLayout.setConstraints(digitButtons[3], gridConstraints);
        container.add(digitButtons[3]);

        // button: =
        eqButton = new JButton("=");
        eqButton.addActionListener(this);
        eqButton.setFocusable(false);
        gridConstraints.gridx = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.gridheight = 2;
        gridLayout.setConstraints(eqButton, gridConstraints);
        container.add(eqButton);

        // row 6
        gridConstraints.gridy = 6;

        // button: 0
        digitButtons[0] = new JButton("0");
        digitButtons[0].addActionListener(this);
        digitButtons[0].setFocusable(false);
        gridConstraints.gridx = 0;
        gridConstraints.gridwidth = 2;
        gridConstraints.gridheight = 1;
        gridLayout.setConstraints(digitButtons[0], gridConstraints);
        container.add(digitButtons[0]);

        // button: ,
        commaButton = new JButton(",");
        commaButton.addActionListener(this);
        commaButton.setFocusable(false);
        gridConstraints.gridx = 2;
        gridConstraints.gridwidth = 1;
        gridConstraints.gridheight = 1;
        gridLayout.setConstraints(commaButton, gridConstraints);
        container.add(commaButton);



        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calc");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calc().init();
            }
        });
    }

}
