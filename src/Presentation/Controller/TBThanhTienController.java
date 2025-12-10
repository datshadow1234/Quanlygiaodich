package Presentation.Controller;

import Business.Control.TrungBinhThanhTienUseCase;
import Business.Enity.Gold;
import Business.Enity.Money;
import Presentation.Model.TBThanhTienViewModel;
import Presentation.View.TrungBinhThanhTienUI;

import java.awt.Frame;

public class TBThanhTienController {
    private final TrungBinhThanhTienUseCase useCase;
    private final TBThanhTienViewModel viewModel;
    private final Frame owner;

    public TBThanhTienController(Frame owner, TrungBinhThanhTienUseCase useCase, TBThanhTienViewModel viewModel) {
        this.owner = owner;
        this.useCase = useCase;
        this.viewModel = viewModel;
    }

    public void showDialog() {
        TrungBinhThanhTienUI view = new TrungBinhThanhTienUI(owner, viewModel);
        
        // Calculate both averages and update the ViewModel
        calculateAllAverages();

        view.showView();
    }

    private void calculateAllAverages() {
        double averageMoney = useCase.execute(Money.class);
        double averageGold = useCase.execute(Gold.class);
        
        viewModel.setAverageMoney(averageMoney);
        viewModel.setAverageGold(averageGold);
    }
}