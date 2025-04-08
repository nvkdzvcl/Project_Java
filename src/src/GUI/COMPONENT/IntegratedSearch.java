package GUI.COMPONENT;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class IntegratedSearch extends JPanel {

    public JComboBox<String> cbxChoose;
    public JButton btnReset;
    public JTextField txtSearchForm;

    private void initComponent(String string[]){

        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 10));
        searchPanel.setBorder(new EmptyBorder(18, 15, 18, 15));
        searchPanel.setBackground(Color.WHITE);

        cbxChoose = new JComboBox();
        cbxChoose.setModel(new DefaultComboBoxModel<>(string));
        cbxChoose.setPreferredSize(new Dimension(140, 0));
        cbxChoose.setFocusable(false);
        searchPanel.add(cbxChoose, BorderLayout.WEST);

        txtSearchForm = new JTextField();
        searchPanel.add(txtSearchForm);

        btnReset = new JButton("Làm mới");
        btnReset.setIcon(new ImageIcon());
        btnReset.setPreferredSize(new Dimension(125, 0));
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearchForm.setText("");
                cbxChoose.setSelectedIndex(0);
            }
        });
        searchPanel.add(btnReset, BorderLayout.EAST);
        this.add(searchPanel);
    }

    public IntegratedSearch(String string[]){
        initComponent(string);
    }
}
