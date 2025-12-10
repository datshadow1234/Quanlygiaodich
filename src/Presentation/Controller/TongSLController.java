package Presentation.Controller;

import Business.Control.TinhTongSoLuongUseCase;
import Presentation.Model.TongSoLuongViewModel;
import Presentation.View.TongSoLuongUI;

import java.awt.Frame;

public class TongSLController {
    private final TinhTongSoLuongUseCase useCase;
    private final TongSoLuongViewModel viewModel;
    private final Frame owner;

    public TongSLController(Frame owner, TinhTongSoLuongUseCase useCase, TongSoLuongViewModel viewModel) {
        this.owner = owner;
        this.useCase = useCase;
        this.viewModel = viewModel;
    }

    public void showDialog() {
        TongSoLuongUI view = new TongSoLuongUI(owner, viewModel);
        
        // Calculate all quantities and update the ViewModel
        calculateAllQuantities();

        view.showView();
    }

    private void calculateAllQuantities() {
        int totalMoney = useCase.execute("Money");
        int totalGold = useCase.execute("Gold");
        
        viewModel.setTotalMoneyQuantity(totalMoney);
        viewModel.setTotalGoldQuantity(totalGold);
    }
}
