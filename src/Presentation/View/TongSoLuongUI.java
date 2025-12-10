package Presentation.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for padding
import javax.swing.event.AncestorListener;

import java.awt.BorderLayout;
import java.awt.Dimension; // Import for Dimension
import java.awt.Font; // Import for Font
import Presentation.Model.TongSoLuongViewModel;

public class TongSoLuongUI extends JDialog implements Subscriber {
    private final TongSoLuongViewModel viewModel;
    private JLabel moneyLabel;
    private JLabel goldLabel;

    public TongSoLuongUI(java.awt.Frame parent, TongSoLuongViewModel viewModel) {
        super(parent, "Tổng Số Lượng Từng Loại", true);
        this.viewModel = viewModel;
        this.viewModel.registerSubscriber(this);

        // Initialize labels
        moneyLabel = new JLabel("Tổng số lượng Tiền tệ: Đang tính...");
        goldLabel = new JLabel("Tổng số lượng Vàng: Đang tính...");

        // Set font for labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16); // A common, clean font
        moneyLabel.setFont(labelFont);
        goldLabel.setFont(labelFont);

        // Center align text in labels
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goldLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Arrange labels vertically
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding (top, left, bottom, right)
        
        // Center components within the BoxLayout
        moneyLabel.setAlignmentX(CENTER_ALIGNMENT);
        goldLabel.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(moneyLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some vertical space between labels
        panel.add(goldLabel);

        this.add(panel, BorderLayout.CENTER);
        
        // Set preferred size for the dialog
        this.setPreferredSize(new Dimension(400, 150)); // Wider and a bit taller
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    public void addMoneyButtonListener(AncestorListener listener) {
        // This method is no longer needed as buttons are removed
    }

    public void addGoldButtonListener(AncestorListener listener) {
        // This method is no longer needed as buttons are removed
    }

    @Override
    public void update() {
        // Update labels with data from ViewModel
        moneyLabel.setText("Tổng số lượng Tiền tệ: " + viewModel.getTotalMoneyQuantity());
        goldLabel.setText("Tổng số lượng Vàng: " + viewModel.getTotalGoldQuantity());
    }

    public void showView() {
        setVisible(true);
    }
}