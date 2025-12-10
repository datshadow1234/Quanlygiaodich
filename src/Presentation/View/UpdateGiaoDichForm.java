package Presentation.View;

import Presentation.Model.GiaoDichViewItem;


import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class UpdateGiaoDichForm extends JDialog {
    private JTextField txtMa, txtNgay, txtDonGia, txtSoLuong;
    private JTextField txtLoaiVang, txtLoaiTien, txtTiGia;
    private JComboBox<String> cboLoaiGiaoDich;
    private JPanel panelExtraFields;
    private Consumer<GiaoDichViewItem> onUpdate;

    public UpdateGiaoDichForm(JFrame parent, GiaoDichViewItem item, Consumer<GiaoDichViewItem> onUpdate) {
        super(parent, "Cập nhật giao dịch", true);
        this.onUpdate = onUpdate;

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panelMain = new JPanel(new GridLayout(6, 2, 5, 5));

        txtMa = new JTextField(item.maGiaoDich);
        txtMa.setEditable(false);
        txtNgay = new JTextField(item.ngayGiaoDich);
        txtDonGia = new JTextField(item.donGia);
        txtSoLuong = new JTextField(item.soLuong);

        cboLoaiGiaoDich = new JComboBox<>(new String[]{"gold", "money"});
        cboLoaiGiaoDich.setSelectedItem(item.loaiGiaoDich);

        panelMain.add(new JLabel("Mã GD:")); panelMain.add(txtMa);
        panelMain.add(new JLabel("Ngày GD (dd/MM/yyyy):")); panelMain.add(txtNgay);
        panelMain.add(new JLabel("Đơn giá:")); panelMain.add(txtDonGia);
        panelMain.add(new JLabel("Số lượng:")); panelMain.add(txtSoLuong);
        panelMain.add(new JLabel("Loại giao dịch:")); panelMain.add(cboLoaiGiaoDich);

        add(panelMain, BorderLayout.NORTH);

        // Extra field panel
        panelExtraFields = new JPanel(new GridLayout(2, 2, 5, 5));
        txtLoaiVang = new JTextField(item.loaiVang);
        txtLoaiTien = new JTextField(item.loaiTien);
        txtTiGia = new JTextField(item.tiGia);

        add(panelExtraFields, BorderLayout.CENTER);

        cboLoaiGiaoDich.addActionListener(e -> updateExtraFields());

        JPanel panelButtons = new JPanel();
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        panelButtons.add(btnSave);
        panelButtons.add(btnCancel);
        add(panelButtons, BorderLayout.SOUTH);

        updateExtraFields(); // Khởi tạo đúng field phụ

        btnSave.addActionListener(e -> {
            GiaoDichViewItem newItem = new GiaoDichViewItem();
            newItem.maGiaoDich = item.maGiaoDich;

            // Nếu người dùng không nhập gì, dùng lại dữ liệu cũ
            newItem.ngayGiaoDich = txtNgay.getText().trim().isEmpty() ? item.ngayGiaoDich : txtNgay.getText().trim();
            newItem.donGia = txtDonGia.getText().trim().isEmpty() ? item.donGia : txtDonGia.getText().trim();
            newItem.soLuong = txtSoLuong.getText().trim().isEmpty() ? item.soLuong : txtSoLuong.getText().trim();
            newItem.loaiGiaoDich = cboLoaiGiaoDich.getSelectedItem().toString(); // luôn giữ nguyên vì không cho sửa loại

            if ("gold".equalsIgnoreCase(item.loaiGiaoDich)) {
                newItem.loaiVang = txtLoaiVang.getText().trim().isEmpty() ? item.loaiVang : txtLoaiVang.getText().trim();
                newItem.loaiTien = null;
                newItem.tiGia = "0";
            } else {
                newItem.loaiTien = txtLoaiTien.getText().trim().isEmpty() ? item.loaiTien : txtLoaiTien.getText().trim();
                newItem.tiGia = txtTiGia.getText().trim().isEmpty() ? item.tiGia : txtTiGia.getText().trim();
                newItem.loaiVang = null;
            }

            if (onUpdate != null) onUpdate.accept(newItem);
            dispose();
        });


        btnCancel.addActionListener(e -> dispose());

    }

    private void updateExtraFields() {
        panelExtraFields.removeAll();
        String selected = (String) cboLoaiGiaoDich.getSelectedItem();

        if ("gold".equalsIgnoreCase(selected)) {
            panelExtraFields.add(new JLabel("Loại vàng:"));
            panelExtraFields.add(txtLoaiVang);
        } else if ("money".equalsIgnoreCase(selected)) {
            panelExtraFields.add(new JLabel("Loại tiền:"));
            panelExtraFields.add(txtLoaiTien);
            panelExtraFields.add(new JLabel("Tỉ giá:"));
            panelExtraFields.add(txtTiGia);
        }

        panelExtraFields.revalidate();
        panelExtraFields.repaint();
    }
}
