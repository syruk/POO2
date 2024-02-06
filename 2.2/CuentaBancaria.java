import java.util.Arrays;

public class CuentaBancaria {
    private final String iban;
    private final String name;
    private final String surname;
    private double balance;
    private static final int maxBankOverdraft = -50;
    private static final int contactWithTreasure = 3000;


    private double[] movements;
    private int numMovements;


    public CuentaBancaria(String iban, String name, String surname) {
        this.iban = iban;
        this.name = name;
        this.surname = surname;
        this.balance = 0;
        this.movements = new double[1];
    }

    public String getIban() {
        return iban;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getBalance() {
        return balance;
    }

    public double[] getMovements() {
        return movements;
    }
    private void ensureCapacity() {
        if (numMovements == movements.length) {
            movements = Arrays.copyOf(movements, movements.length + 1);
        }
    }

    public void addMovement(double amount) {
        ensureCapacity();
        if (numMovements < 100) {
            movements[numMovements++] = amount;
        } else {
            System.out.println("AVISO: Límite de movimientos alcanzado. No se pueden realizar más movimientos.");
        }
    }


    public void bankDeposit(double deposit) {
        if (deposit > 0) {
            balance += deposit;
            addMovement(deposit);
            notifyTreasury(deposit);
        }
    }

    public boolean bankWithdrawal(double withdrawal) {
        if (withdrawal > 0 && balance >= maxBankOverdraft) {
            if (balance-withdrawal > maxBankOverdraft){
                balance -= withdrawal;
                addMovement(-withdrawal);
                negativeBalance();
                return true;
            }
        }
        return false;
    }

    public void negativeBalance(){
        if (balance < 0){
            System.out.println("AVISO: Saldo Negativo");
        }
    }
    public void notifyTreasury(double deposit){
        if (deposit > contactWithTreasure){
            System.out.println("AVISO: Notificar a Hacienda");
        }
    }
    public String toString(){
        return  "Nombre titular = " + name +
                "\nApellidos titular = " + surname +
                "\nIBAN = " + iban +
                "\nSaldo = " + balance;
    }

}