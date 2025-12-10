package Presentation.View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Presentation.Controller.InGiaoDichController;
import Presentation.Controller.XoaGiaoDichController;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;
import Presentation.OpenEditGDForm.LoadGiaoDichInFormController;
import Presentation.OpenEditGDForm.OpenEditGDFormController;
import Persistence.OpenFormEditGD.OpenEditGiaoDichFormGateway;
import Business.Control.OpenFormEditGD.LoadGiaoDichInFormUC;
import Business.Control.OpenFormEditGD.OpenEditGiaoDichFormUC;
import Presentation.OpenEditGDForm.OpenEditGDFormView;
import Presentation.OpenEditGDForm.OpenEditGDFormModel;
import Persistence.OpenFormEditGD.LoadGiaoDichInFormDAO;
import Persistence.OpenFormEditGD.LoadGiaoDichInFormGateway;
import Persistence.OpenFormEditGD.OpenEditGiaoDichFormDAO;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;



public class InGiaoDichUI extends JFrame implements Subscriber{
    private JScrollPane scrollPane;
    private JTable table;
    private TempJPanelInGD panel;
    private JButton btnEdit;
    private DefaultTableModel model;
    private GiaoDichViewModel giaoDichViewModel;
    private XoaGiaoDichController xoaGiaoDichController;
    private InGiaoDichController inGiaoDichController;
    private String maGiaoDich;
    private Consumer<GiaoDichViewItem> onUpdate;
    public InGiaoDichUI(){
        setTitle("App quan ly giao dich");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.scrollPane =  new JScrollPane();
         String[] columns = {"STT","Mã Giao Dịch", "Ngày Giao Dịch", "Đơn Giá", "Số Lượng", "Loại giao dịch","Thành tiền"};
        this.model = new DefaultTableModel(columns, 0);
        this.table = new JTable(model);
        this.panel = new TempJPanelInGD();
        this.panel.addBtnXoaListener(e -> {
            int selectRow = table.getSelectedRow();
            if(selectRow == -1){
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
                return;
            }
            maGiaoDich = table.getValueAt(selectRow, 1).toString();
            //JOptionPane.showMessageDialog(this, layMaGiaoDich);
            int result = JOptionPane.showConfirmDialog(
                null,
                "Ban co muon xoa giao dich nay?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
            );
        
            if (result == JOptionPane.YES_OPTION){
                this.xoaGiaoDichController.run();
                this.inGiaoDichController.run();
                JOptionPane.showMessageDialog(this, "Xóa thành công");
            }
        });
        this.scrollPane.setViewportView(this.table);
        add(this.scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        setVisible(true);


        JPanel top = new JPanel(new BorderLayout(5,5));
        btnEdit = new JButton("Edit");
        top.add(btnEdit, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenEditGDFormView();
                // Handle add button click
            }   
            private void OpenEditGDFormView(){
                int selectedRow = table.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn giao dịch để sửa");
                    return;
                }

                String maGD = table.getModel().getValueAt(selectedRow, 1).toString();

                // 1. Tạo form và model
                OpenEditGDFormModel formmodel = new OpenEditGDFormModel();
                OpenEditGDFormView view = new OpenEditGDFormView();
                view.setModel(formmodel);
                view.setModelupdate(giaoDichViewModel);

                // 2. Mở form (khởi tạo dữ liệu cơ bản nếu cần)
                OpenEditGiaoDichFormGateway gateway = new OpenEditGiaoDichFormDAO();
                OpenEditGiaoDichFormUC uc = new OpenEditGiaoDichFormUC(gateway);
                OpenEditGDFormController controller = new OpenEditGDFormController(formmodel, uc);
                controller.execute();

                // 3. Load dữ liệu chi tiết vào model
                LoadGiaoDichInFormGateway loadGateway = new LoadGiaoDichInFormDAO();
                LoadGiaoDichInFormUC editUC = new LoadGiaoDichInFormUC(loadGateway);
                LoadGiaoDichInFormController editController = new LoadGiaoDichInFormController(formmodel, editUC);
                editController.execute(maGD);
            }
                });
    }
    public void setViewModel(GiaoDichViewModel giaoDichViewModel){
        this.giaoDichViewModel = giaoDichViewModel;
        this.giaoDichViewModel.registerSubscriber(this);
       
   }
   
       private void showList(GiaoDichViewModel viewModel) {
        model.setRowCount(0);
        for (GiaoDichViewItem item : viewModel.listItem) {
            Object[] row = {
            	item.stt,
                item.maGiaoDich,
                item.ngayGiaoDich,
                item.donGia,
                item.soLuong,
                item.loaiGiaoDich,
                item.thanhTien
            };
            model.addRow(row);
        }
    }
    public void setOnUpdate(Consumer<GiaoDichViewItem> onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public void update(){
        showList(giaoDichViewModel);
    }
    public String getMaGiaoDich(){
        return maGiaoDich;
    }
    public void setController(XoaGiaoDichController xoaGiaoDichController, InGiaoDichController inGiaoDichController){
        this.xoaGiaoDichController = xoaGiaoDichController;
        this.inGiaoDichController = inGiaoDichController;
    }
    public void addLocDonGiaListener(ActionListener listener) {
        this.panel.addBtnLocDonGiaListener(listener);
    }

    public JTable getTable() {
        return this.table;
    }
    
    public TempJPanelInGD getPanel() {
        return this.panel;
    }
                
    public void addBtnThemListener(ActionListener listener) {
        this.panel.addBtnThemListener(listener);
    }
    public void addTinhTongSLTungLoaiListener(ActionListener listener) {
        this.panel.addBtnTinhTongSLTungLoaiListener(listener);
    }
    public void addTinhTrungBinhThanhTienListener(ActionListener listener) {
        this.panel.addBtnTinhTrungBinhThanhTienListener(listener);
    }
    
    public void addBtnXoaListener(ActionListener listener) {
        this.panel.addBtnXoaListener(listener);
    }
}
