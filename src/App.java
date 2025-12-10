import javax.swing.JOptionPane;
import javax.swing.JTable;
import Presentation.View.ThemGiaoDichViewUI;
import Presentation.View.UpdateGiaoDichForm;
import Persistence.InGiaoDichDAO;
import Persistence.MockInGiaoDichDAO;
import Persistence.XoaGiaoDichDAO;
import Persistence.XoaGiaoDichDAOGateWay;
import Persistence.GiaoDichDAOGateWay;
import Persistence.GiaoDichWriterDAOGateway;
import Presentation.Controller.*;
import Business.Control.*;
import Presentation.Model.GiaoDichViewItem;
import Presentation.Model.GiaoDichViewModel;
import Presentation.View.GiaoDichTheoDonUI;
import Presentation.View.InGiaoDichUI;
import Presentation.Model.TBThanhTienViewModel;
import Presentation.Model.TongSoLuongViewModel;
import Presentation.Model.XoaGDViewModel;

public class App {
    public static void main(String[] args) {
        try {
            // Khởi tạo UI chính
            InGiaoDichDAO inGiaoDichDAO = new InGiaoDichDAO();
            XoaGiaoDichDAOGateWay xoaGiaoDichDAO = new XoaGiaoDichDAO();
            GiaoDichViewModel giaoDichViewModel = new GiaoDichViewModel();
            XoaGDViewModel xoaGDViewModel = new XoaGDViewModel();
            InGiaoDichUC inGiaoDichUC = new InGiaoDichUC(inGiaoDichDAO);
            XoaGiaoDichUC xoaGiaoDichUC = new XoaGiaoDichUC(xoaGiaoDichDAO);
            InGiaoDichUI inGiaoDichUI = new InGiaoDichUI();
            InGiaoDichController inGiaoDichController = new InGiaoDichController(inGiaoDichUC, giaoDichViewModel);
            XoaGiaoDichController xoaGiaoDichController = new XoaGiaoDichController(inGiaoDichUI, xoaGiaoDichUC, xoaGDViewModel);
            inGiaoDichUI.setViewModel(giaoDichViewModel);
            inGiaoDichUI.setController(xoaGiaoDichController, inGiaoDichController);
            inGiaoDichController.run();

            //giaoDichUI.setVisible(true)


            // Khởi tạo controller cho các nút tính toán
            
            // Gắn sự kiện cho nút "Tính Trung Bình Thành Tiền"
            inGiaoDichUI.addTinhTrungBinhThanhTienListener(e -> {
                // 1. Create the ViewModel
                Presentation.Model.TBThanhTienViewModel viewModel = new Presentation.Model.TBThanhTienViewModel();

                // 2. Create the UseCase (Business Model)
                TrungBinhThanhTienUseCase useCase = new TrungBinhThanhTienUseCase(inGiaoDichDAO);
                
                // 3. Create the Controller and pass it the owner frame, use case, and view model
                TBThanhTienController controller = new TBThanhTienController(inGiaoDichUI, useCase, viewModel);
                
                // 4. The controller will create the View and show it
                controller.showDialog();
            });

            // Gắn sự kiện cho nút "Tính Tổng Số Lượng Từng Loại"
            inGiaoDichUI.addTinhTongSLTungLoaiListener(e -> {
                // 1. Create the ViewModel
                Presentation.Model.TongSoLuongViewModel viewModel = new Presentation.Model.TongSoLuongViewModel();

                // 2. Create the UseCase (Business Model)
                Business.Control.TinhTongSoLuongUseCase useCase = new Business.Control.TinhTongSoLuongUseCase(inGiaoDichDAO);
                
                // 3. Create the Controller and pass it the owner frame, use case, and view model
                TongSLController controller = new TongSLController(inGiaoDichUI, useCase, viewModel);
                
                // 4. The controller will create the View and show it
                controller.showDialog();
            });
            
            // Gắn sự kiện nút Xóa
    
            
            // Gắn sự kiện lọc giao dịch > 1 tỷ
            GiaoDichDAOGateWay finalDAO = inGiaoDichDAO; // biến final để dùng trong lambda
            inGiaoDichUI.addLocDonGiaListener(e -> {
                GiaoDichViewModel viewModel1Ty = new GiaoDichViewModel();
                GiaoDichTheoDonUseCase useCase1ty = new GiaoDichTheoDonUseCase(finalDAO); // dùng lại DAO
                GiaoDichTheoDonController controller1Ty = new GiaoDichTheoDonController(viewModel1Ty, useCase1ty);
                
                GiaoDichTheoDonUI locUI = new GiaoDichTheoDonUI();
                locUI.setViewModel(viewModel1Ty);
                controller1Ty.fetchGiaoDichLonHon1Ty();
                locUI.setVisible(true);
                
});
             // Gắn sự kiện Them
             inGiaoDichUI.addBtnThemListener(et -> {
                GiaoDichViewModel vm = new GiaoDichViewModel();
                GiaoDichDAOGateWay dao = new InGiaoDichDAO();
    
                LuuGiaoDichUseCase useCase = new LuuGiaoDichUseCase(dao);
                ThemGiaoDichController controller = new ThemGiaoDichController(vm, useCase);
    
                new OpenThemGiaoDichFormUseCase().open(controller, vm);
                controller.capNhatViewModel();
            });
              
           
                
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi khởi động ứng dụng: " + ex.getMessage());
        }
    }
}
