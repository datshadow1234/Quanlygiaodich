package Presentation.View;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TempJPanelInGD extends JPanel {
    JButton btnThem, btnXoa, btnSua, btnTinhTongSLTungLoai, btnTinhTrungBinhThanhTien, btnInGDTheoDonGia;

    public TempJPanelInGD() {
        setLayout(new FlowLayout());
        btnThem = new JButton("Them");
        btnXoa = new JButton("Xoa");
       
        btnTinhTongSLTungLoai = new JButton("Tinh Tong SL Theo Tung Loai");
        btnTinhTrungBinhThanhTien = new JButton("Tinh TB thanh tien");
        btnInGDTheoDonGia = new JButton("In Giao dich theo don gia");

        add(btnThem);
        add(btnXoa);
       
        add(btnTinhTongSLTungLoai);
        add(btnTinhTrungBinhThanhTien);
        add(btnInGDTheoDonGia);
    }

    // View chỉ expose addListener(), không xử lý nghiệp vụ
    public void addBtnLocDonGiaListener(ActionListener listener) {
        btnInGDTheoDonGia.addActionListener(listener);
    }
    public void addBtnTinhTongSLTungLoaiListener(ActionListener listener) {
        btnTinhTongSLTungLoai.addActionListener(listener);
    }
    public void addBtnTinhTrungBinhThanhTienListener(ActionListener listener) {
        btnTinhTrungBinhThanhTien.addActionListener(listener);
    }
    public void addBtnThemListener(ActionListener listener) {
        btnThem.addActionListener(listener);
    }
    public void addBtnSuaListener(ActionListener listener) {
        btnSua.addActionListener(listener);
    }
    
    public void addBtnXoaListener(ActionListener listener) {
        btnXoa.addActionListener(listener);
    }
}
