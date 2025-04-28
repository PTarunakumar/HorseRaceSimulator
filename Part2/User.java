import java.util.LinkedList;
import java.util.List;

public class User {
    private int initialMoney;
    private int money;

    private List<Integer> betAmountHistory;
    private List<Boolean> betOutcomeHistory;
    private List<Integer> moneyHistory;
    public User(int money) {
        this.initialMoney = money;
        this.betAmountHistory = new LinkedList<>();
        this.betOutcomeHistory = new LinkedList<>();
        this.moneyHistory = new LinkedList<>();
        this.money = money;
    }

    public int getInitialMoney() {
        return initialMoney;
    }

    public void updateOutcome(int betAmount, boolean betOutcome, int money)
    {
        betAmountHistory.add(betAmount);
        betOutcomeHistory.add(betOutcome);
        moneyHistory.add(money);
    }

    public List<Integer> getBetAmountHistory() {
        return betAmountHistory;
    }
    public List<Boolean> getBetOutcomeHistory() {
        return betOutcomeHistory;
    }
    public List<Integer> getMoneyHistory() {
        return moneyHistory;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}

