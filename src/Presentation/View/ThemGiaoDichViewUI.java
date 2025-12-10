package Presentation.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Business.Enity.Gold;
import Business.Enity.Money;
import Presentation.Controller.ThemGiaoDichController;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;

public class ThemGiaoDichViewUI extends JFrame implements Subscriber {
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMa, txtNgay, txtDonGia, txtSoLuong, txtLoaiChiTiet, txtTiGia;
    private JComboBox<String> cboLoaiGiaoDich, cboLoaiTien;
    private JButton btnThem;

    private GiaoDichViewModel viewModel;
    private ThemGiaoDichController controller;

    public ThemGiaoDichViewUI() {
        setTitle("Quản lý Giao dịch");
        setSize(950, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initForm();
        initTable();
        initButtons();
        initEvents();
    }

    private void initForm() {
        JPanel topPanel = new JPanel(new GridLayout(4, 4, 5, 5));

        txtMa = new JTextField();
        txtNgay = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date())); // default: hôm nay
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();
        txtLoaiChiTiet = new JTextField();
        txtTiGia = new JTextField();

        cboLoaiGiaoDich = new JComboBox<>(new String[]{"Vàng", "Tiền tệ"});
        cboLoaiTien = new JComboBox<>(new String[]{"VND", "USD", "EUR"});

        topPanel.add(new JLabel("Mã GD:"));      topPanel.add(txtMa);
        topPanel.add(new JLabel("Ngày:"));       topPanel.add(txtNgay);
        topPanel.add(new JLabel("Đơn giá:"));    topPanel.add(txtDonGia);
        topPanel.add(new JLabel("Số lượng:"));   topPanel.add(txtSoLuong);
        topPanel.add(new JLabel("Loại:"));       topPanel.add(txtLoaiChiTiet);
        topPanel.add(new JLabel("Tỉ giá:"));     topPanel.add(txtTiGia);
        topPanel.add(new JLabel("Loại GD:"));    topPanel.add(cboLoaiGiaoDich);
        topPanel.add(new JLabel("Loại tiền:"));  topPanel.add(cboLoaiTien);

        add(topPanel, BorderLayout.NORTH);
    }

    private void initTable() {
        String[] cols = {"STT", "Mã", "Ngày", "Loại GD", "Chi tiết", "SL", "Đơn giá", "Thành tiền"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initButtons() {
        JPanel bottomPanel = new JPanel();
        btnThem = new JButton("Thêm");
        bottomPanel.add(btnThem);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initEvents() {
        cboLoaiGiaoDich.addActionListener(e -> {
            boolean isMoney = "Tiền tệ".equals(cboLoaiGiaoDich.getSelectedItem());
            txtTiGia.setEnabled(isMoney);
            cboLoaiTien.setEnabled(isMoney);
        });

        btnThem.addActionListener(e -> xuLyThem());

        // kích trạng thái ban đầu
        cboLoaiGiaoDich.dispatchEvent(
            new java.awt.event.ActionEvent(cboLoaiGiaoDich, java.awt.event.ActionEvent.ACTION_PERFORMED, "init"));
    }

    private void xuLyThem() {
        try {
            String ma = txtMa.getText().trim();
            Date ngay = new SimpleDateFormat("dd/MM/yyyy").parse(txtNgay.getText().trim());
            double donGia = Double.parseDouble(txtDonGia.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            String loaiGD = (String) cboLoaiGiaoDich.getSelectedItem();

            if (ma.isEmpty() || txtLoaiChiTiet.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập đầy đủ mã và loại chi tiết.");
            }

            if ("Vàng".equals(loaiGD)) {
                String loaiVang = txtLoaiChiTiet.getText().trim();
                Gold gd = new Gold(ma, ngay, donGia, soLuong, loaiVang);
                controller.themGiaoDich(gd);
            } else {
                String loaiTien = (String) cboLoaiTien.getSelectedItem();
                double tiGia = Double.parseDouble(txtTiGia.getText().trim());
                Money gd = new Money(ma, ngay, donGia, soLuong, tiGia, loaiTien);
                controller.themGiaoDich(gd);
            }

            controller.capNhatViewModel();
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã thêm giao dịch!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtMa.setText("");
        txtNgay.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtDonGia.setText("");
        txtSoLuong.setText("");
        txtLoaiChiTiet.setText("");
        txtTiGia.setText("");
        cboLoaiGiaoDich.setSelectedIndex(0);
        cboLoaiTien.setSelectedIndex(0);

        cboLoaiGiaoDich.dispatchEvent(
            new java.awt.event.ActionEvent(cboLoaiGiaoDich, java.awt.event.ActionEvent.ACTION_PERFORMED, "reset"));
    }

    public void setViewModel(GiaoDichViewModel vm) {
        this.viewModel = vm;
        vm.registerSubscriber(this);
    }

    public void setController(ThemGiaoDichController controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        model.setRowCount(0);
        for (GiaoDichViewItem item : viewModel.listItem) {
            model.addRow(new Object[]{
                item.stt, item.maGiaoDich, item.ngayGiaoDich, item.loaiGiaoDich,
                item.loaiChiTiet, item.soLuong, item.donGia, item.thanhTien
            });
        }
    }
}
