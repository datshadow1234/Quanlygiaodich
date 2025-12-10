package Presentation.View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import Presentation.Model.*;

public class GiaoDichTheoDonUI extends JFrame implements Subscriber {
    private JTable table;
    private DefaultTableModel model;
    private GiaoDichViewModel viewModel;
    private JPanel panelButtons;

    public GiaoDichTheoDonUI() {
        setTitle("Giao dịch > 1 tỷ");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] cols = {"STT", "Mã GD", "Ngày", "Đơn giá", "Số lượng","Loại Giao Dịch", "Thành tiền"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Tạo panel chỉ có nút thoát
        panelButtons = new JPanel(new FlowLayout());
        JButton btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(e -> dispose());
        panelButtons.add(btnThoat);
        add(panelButtons, BorderLayout.SOUTH);
    }

    public void setViewModel(GiaoDichViewModel vm) {
        this.viewModel = vm;
        vm.registerSubscriber(this);
    }

    private void renderTable() {
        model.setRowCount(0);
        for (GiaoDichViewItem item : viewModel.listItem) { //lấy dữ liệu từ model
            Object[] row = { item.stt, item.maGiaoDich, item.ngayGiaoDich, item.donGia, item.soLuong, item.loaiGiaoDich, item.thanhTien };
            model.addRow(row);
        }
    }

    @Override
    public void update() {
        renderTable();
    }
}
