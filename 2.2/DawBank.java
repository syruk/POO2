import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DawBank {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CuentaBancaria account = createClient();
        program(account);
    }
    private static CuentaBancaria createClient(){
        System.out.println("Bienvenido a DAWBANK, por favor, introduzca sus datos para que podamos crear una cuenta.\n" +
                "Nombre Titular: ");
        String name = scanner.nextLine();
        System.out.println("Apellidos Titular: ");
        String surname = scanner.nextLine();
        String iban = generateRandomAccount();
        return new CuentaBancaria(iban, name, surname);
    }

    private static void showMenu(){
        System.out.println("\nMenú:");
        System.out.println("1. Datos de la cuenta. Mostrará el IBAN, el titular y el saldo.");
        System.out.println("2. IBAN. Mostrará el IBAN.");
        System.out.println("3. Titular. Mostrará el titular.");
        System.out.println("4. Saldo. Mostrará el saldo disponible.");
        System.out.println("5. Ingreso. Pedirá la cantidad a ingresar y realizará el ingreso si es posible.");
        System.out.println("6. Retirada. Pedirá la cantidad a retirar y realizará la retirada si es posible.");
        System.out.println("7. Movimientos. Mostrará una lista con el historial de movimientos.");
        System.out.println("0. Salir. Termina el programa.");
        System.out.print("Seleccione una opción: ");
    }
    private static void program(CuentaBancaria account){
        double option;
        do {
            showMenu();
            option = validNumValue();
            switch ((int) option) {
                case 1:
                    System.out.println(account);
                    break;
                case 2:
                    System.out.println(account.getIban());
                    break;
                case 3:
                    System.out.println(account.getName() + " " + account.getSurname());
                    break;
                case 4:
                    System.out.println(account.getBalance());
                    break;
                case 5:
                    System.out.println("Introduzca el depósito");
                    double deposit = validNumValue();
                    account.bankDeposit(deposit);
                    break;
                case 6:
                    System.out.println("Introduzca la cantidad a retirar");
                    double withdrawal = validNumValue();
                    if (account.bankWithdrawal(withdrawal)){
                        System.out.println("Operación realizada");
                    } else {
                        System.out.println("No es posible hacer la operación, descubierto demasiado elevado");
                    }
                    account.bankWithdrawal(withdrawal);
                    break;
                case 7:
                    System.out.println(Arrays.toString(account.getMovements()));
                    break;
                case 0:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (option != 0);
        scanner.close();
    }
    public static double validNumValue() {
        Scanner scanner = new Scanner(System.in);
        boolean numeroValido = false;
        double number = 0;
        while (!numeroValido) {
            try {
                number = scanner.nextInt();
                if (number < 0){
                    System.out.println("Por favor, escriba un número positivo.");
                }else{
                    numeroValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero.");
                scanner.next();
            }
        }
        return number;
    }
    private static String generateRandomAccount() {
        StringBuilder account = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            account.append(random.nextInt(10));
        }
        return "ES" + account;
    }
}