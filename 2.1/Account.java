public class Account {
    private final String numAccount;
    private double availableBalance;

    public Account(String numAccount, double initialBalance) {
        this.numAccount = numAccount;
        this.availableBalance = initialBalance;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void receivePayment(double payment) {
        availableBalance += payment;
    }

    public void payBill(double payment) {
        availableBalance -= payment;
    }
}