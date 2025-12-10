package Presentation.OpenEditGDForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Business.Control.OpenFormEditGD.UpdateGiaoDichUseCase;
import Persistence.OpenFormEditGD.UpdateGiaoDichDAO;
import Persistence.OpenFormEditGD.UpdateGiaoDichGateway;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;
import Presentation.View.Subscriber;

import java.awt.*;

public class OpenEditGDFormView extends JFrame implements Subscriber {

    // Trường cơ bản
    private JTextField txtMaGD;
    private JTextField txtNgay;
    private JTextField txtDonGia;
    private JTextField txtSoLuong;
    private JComboBox<String> cboLoaiGD;

    // Panel và trường bổ sung
    private JPanel pnlExtraFields;
    private JTextField txtLoaiVang;
    private JTextField txtLoaiTien;
    private JTextField txtTiGia;

    // Nút
    private JButton btnSave;
    private JButton btnCancel;
    private OpenEditGDFormModel formmodel;
    private GiaoDichViewModel viewModel;

    public OpenEditGDFormView() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    /** Tạo các control UI */
    private void initializeComponents() {
        txtMaGD = new JTextField(20);
        txtNgay = new JTextField(20);
        txtDonGia = new JTextField(20);
        txtSoLuong = new JTextField(20);

        cboLoaiGD = new JComboBox<>(new String[]{"", "gold", "money"});

        txtLoaiVang = new JTextField(20);
        txtLoaiTien = new JTextField(20);
        txtTiGia = new JTextField(20);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        pnlExtraFields = new JPanel(new GridBagLayout());
    }

    /** Bố trí layout form */
    private void setupLayout() {
        setTitle("Sửa Giao Dịch");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Hàng 1
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã GD:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMaGD, gbc);

        // Hàng 2
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Ngày:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNgay, gbc);

        // Hàng 3
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDonGia, gbc);

        // Hàng 4
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSoLuong, gbc);

        // Hàng 5
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Loại GD:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cboLoaiGD, gbc);

        // Hàng 6 - panel bổ sung
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(pnlExtraFields, gbc);

        // Panel nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /** Gán sự kiện */
    private void setupEventHandlers() {
        cboLoaiGD.addActionListener(e -> updateExtraFields());
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> saveTransaction());
    }

    /** Hiển thị panel bổ sung theo loại GD */
    private void updateExtraFields() {
        pnlExtraFields.removeAll();
        String selected = (String) cboLoaiGD.getSelectedItem();

        GridBagConstraints gbcExtra = new GridBagConstraints();
        gbcExtra.insets = new Insets(5, 5, 5, 5);
        gbcExtra.anchor = GridBagConstraints.WEST;

        if ("gold".equalsIgnoreCase(selected)) {
            gbcExtra.gridx = 0; gbcExtra.gridy = 0;
            pnlExtraFields.add(new JLabel("Loại vàng:"), gbcExtra);
            gbcExtra.gridx = 1;
            pnlExtraFields.add(txtLoaiVang, gbcExtra);
        } else if ("money".equalsIgnoreCase(selected)) {
            gbcExtra.gridx = 0; gbcExtra.gridy = 0;
            pnlExtraFields.add(new JLabel("Loại tiền:"), gbcExtra);
            gbcExtra.gridx = 1;
            pnlExtraFields.add(txtLoaiTien, gbcExtra);

            gbcExtra.gridx = 0; gbcExtra.gridy = 1;
            pnlExtraFields.add(new JLabel("Tỉ giá:"), gbcExtra);
            gbcExtra.gridx = 1;
            pnlExtraFields.add(txtTiGia, gbcExtra);
        }

        pnlExtraFields.revalidate();
        pnlExtraFields.repaint();
    }

    /** Nạp dữ liệu từ model vào form */
    private void loadData() {
        cboLoaiGD.removeAllItems();

        if (formmodel != null && formmodel.listItem != null) {
            for (GiaoDichTypeItem type : formmodel.listItem) {
                cboLoaiGD.addItem(type.name);
            }
        }
    }

    /** Lưu dữ liệu giao dịch */
    private void saveTransaction() {
    GiaoDichViewItem newItem = new GiaoDichViewItem();

    // ✅ Lấy dữ liệu từ form, đảm bảo không null
    newItem.maGiaoDich = txtMaGD.getText() != null ? txtMaGD.getText().trim() : "";
    newItem.ngayGiaoDich = txtNgay.getText() != null ? txtNgay.getText().trim() : "";
    newItem.donGia = txtDonGia.getText() != null ? txtDonGia.getText().trim() : "0";
    newItem.soLuong = txtSoLuong.getText() != null ? txtSoLuong.getText().trim() : "0";
    newItem.loaiGiaoDich = cboLoaiGD.getSelectedItem() != null ? cboLoaiGD.getSelectedItem().toString().trim() : "";

    // Panel bổ sung
    newItem.loaiTien = txtLoaiTien.getText() != null ? txtLoaiTien.getText().trim() : "";
    newItem.loaiVang = txtLoaiVang.getText() != null ? txtLoaiVang.getText().trim() : "";
    newItem.tiGia = txtTiGia.getText() != null && !txtTiGia.getText().isBlank() ? txtTiGia.getText().trim() : "0";

    // Kiểm tra dữ liệu cơ bản
    if (newItem.maGiaoDich.isEmpty() ||
        newItem.ngayGiaoDich.isEmpty() ||
        newItem.donGia.isEmpty() ||
        newItem.soLuong.isEmpty() ||
        newItem.loaiGiaoDich.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin cơ bản");
        return;
    }

    // Debug: kiểm tra dữ liệu trước khi gọi controller
    System.out.println(">>> [DEBUG] newItem.loaiTien = " + newItem.loaiTien);
    System.out.println(">>> [DEBUG] newItem.loaiVang = " + newItem.loaiVang);
    System.out.println(">>> [DEBUG] newItem.tiGia = " + newItem.tiGia);

    // Khởi tạo use case và controller
    UpdateGiaoDichGateway updateGateway = new UpdateGiaoDichDAO();
    UpdateGiaoDichUseCase updateUseCase = new UpdateGiaoDichUseCase(updateGateway);
    UpdateGiaoDichController controller = new UpdateGiaoDichController(updateUseCase, viewModel);

    boolean success = controller.execute(newItem);

    if (success) {
        JOptionPane.showMessageDialog(this, "Lưu giao dịch thành công!");
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Lưu giao dịch thất bại!");
        dispose();
    }
}

    /** Xóa trắng form */
    private void clearForm() {
        txtMaGD.setText("");
        txtNgay.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        cboLoaiGD.setSelectedIndex(0);
        txtLoaiVang.setText("");
        txtLoaiTien.setText("");
        txtTiGia.setText("");
        pnlExtraFields.removeAll();
        pnlExtraFields.revalidate();
        pnlExtraFields.repaint();
    }

    /** Gán model và đăng ký subscriber */
    public void setModel(OpenEditGDFormModel model) {
        this.formmodel = model;
        model.registerSubscriber(this);
    }
    public GiaoDichViewModel getModel() {
        return this.viewModel;
    }
    public void setModelupdate(GiaoDichViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /** Callback khi model thay đổi */
    @Override
    public void update() {
    loadData(); // nạp list loại GD vào combobox

    if (formmodel != null && formmodel.item != null) {
        txtMaGD.setText(formmodel.item.maGiaoDich);
        txtNgay.setText(formmodel.item.ngayGiaoDich);
        txtDonGia.setText(formmodel.item.donGia);
        txtSoLuong.setText(formmodel.item.soLuong);

        cboLoaiGD.setSelectedItem(formmodel.item.loaiGiaoDich);
        updateExtraFields();

        if ("gold".equalsIgnoreCase(formmodel.item.loaiGiaoDich)) {
            txtLoaiVang.setText(formmodel.item.loaiVang);
        } else if ("money".equalsIgnoreCase(formmodel.item.loaiGiaoDich)) {
            txtLoaiTien.setText(formmodel.item.loaiTien);
            txtTiGia.setText(formmodel.item.tiGia);
        }
    }

    this.setVisible(true);
}
}
