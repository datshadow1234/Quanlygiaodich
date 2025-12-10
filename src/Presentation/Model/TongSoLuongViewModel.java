package Presentation.Model;

public class TongSoLuongViewModel extends Publisher {
    private int totalMoneyQuantity;
    private int totalGoldQuantity;

    public int getTotalMoneyQuantity() {
        return totalMoneyQuantity;
    }

    public void setTotalMoneyQuantity(int totalMoneyQuantity) {
        this.totalMoneyQuantity = totalMoneyQuantity;
        notifySubscriber();
    }

    public int getTotalGoldQuantity() {
        return totalGoldQuantity;
    }

    public void setTotalGoldQuantity(int totalGoldQuantity) {
        this.totalGoldQuantity = totalGoldQuantity;
        notifySubscriber();
    }
}
