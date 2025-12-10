package Presentation.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension; 
import java.awt.Font; 
import Presentation.Model.TBThanhTienViewModel;

public class TrungBinhThanhTienUI extends JDialog implements Subscriber {
    private final TBThanhTienViewModel viewModel;
    private JLabel moneyLabel;
    private JLabel goldLabel;

    public TrungBinhThanhTienUI(java.awt.Frame parent, TBThanhTienViewModel viewModel) {
        super(parent, "Trung Bình Thành Tiền Từng Loại", true);
        this.viewModel = viewModel;
        this.viewModel.registerSubscriber(this);

       
        moneyLabel = new JLabel("Trung bình thành tiền Tiền tệ: Đang tính...");
        goldLabel = new JLabel("Trung bình thành tiền Vàng: Đang tính...");

        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16); 
        moneyLabel.setFont(labelFont);
        goldLabel.setFont(labelFont);


        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goldLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); 
        
    
        moneyLabel.setAlignmentX(CENTER_ALIGNMENT);
        goldLabel.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(moneyLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        panel.add(goldLabel);

        this.add(panel, BorderLayout.CENTER);
        
        this.setPreferredSize(new Dimension(400, 150)); 
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    

    @Override
    public void update() {
        moneyLabel.setText("Trung bình thành tiền Tiền tệ: " + String.format("%.2f", viewModel.getAverageMoney()));
        goldLabel.setText("Trung bình thành tiền Vàng: " + String.format("%.2f", viewModel.getAverageGold()));
    }

    public void showView() {
        setVisible(true);
    }
}