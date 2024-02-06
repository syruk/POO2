public class Person {
    private final String dni;
    private final Account[] accounts;
    private int numAccounts;
    private static final int maxAccountsAllowed = 3;
    private static final int defaultAccounts = 0;

    public Person(String dni) {
        this.dni = dni;
        this.accounts = new Account[maxAccountsAllowed];
        this.numAccounts = defaultAccounts;
    }

    public String getDni() {
        return dni;
    }
    public Account[] getAccounts() {
        return accounts;
    }
    public boolean addAccounts(Account account) {
        if (numAccounts < maxAccountsAllowed) {
            accounts[numAccounts] = account;
            numAccounts++;
            return true;
        }
        return false;
    }

    public boolean isSlowPayer() {
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getAvailableBalance() < 0) {
                return true;
            }
        }
        return false;
    }
}