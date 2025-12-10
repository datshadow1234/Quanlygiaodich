package Business.Control;

import javax.swing.SwingUtilities;

import Presentation.Controller.ThemGiaoDichController;
import Presentation.Model.GiaoDichViewModel;
import Presentation.View.ThemGiaoDichViewUI;

/**
 * UseCase Form: Mở form 'Thêm giao dịch' và gắn ViewModel + Controller.
 */
public class OpenThemGiaoDichFormUseCase {
    public void open(ThemGiaoDichController controller, GiaoDichViewModel viewModel) {
        SwingUtilities.invokeLater(() -> {
            ThemGiaoDichViewUI ui = new ThemGiaoDichViewUI();
            ui.setViewModel(viewModel);
            ui.setController(controller);
            ui.setLocationRelativeTo(null);
            ui.setVisible(true);
        });
    }
}
