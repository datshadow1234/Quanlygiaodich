package Presentation.Model;

public class TBThanhTienViewModel extends Publisher {
    private double averageMoney;
    private double averageGold;

    public double getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(double averageMoney) {
        this.averageMoney = averageMoney;
        notifySubscriber();
    }

    public double getAverageGold() {
        return averageGold;
    }

    public void setAverageGold(double averageGold) {
        this.averageGold = averageGold;
        notifySubscriber();
    }
}
