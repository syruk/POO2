import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
public class AccountMain {
    static Scanner scanner = new Scanner(System.in);
    private static final int defaultSizeInPersonArray = 10;
    private static final int defaultPeopleInArray = 0;

    public static void main(String[] args) {
        program();
    }
    private static void showMenu(){
        System.out.println("\nMenú:");
        System.out.println("1. Instanciar objetos de tipo Persona");
        System.out.println("2. Instanciar objetos de tipo Cuenta y asociarlos a una persona");
        System.out.println("3. Mostrar datos de una persona (por su DNI)");
        System.out.println("4. Recibir la nómina mensual de una persona (por DNI y número de cuenta)");
        System.out.println("5. Hacer un pago (por DNI y número de cuenta)");
        System.out.println("6. Realizar transferencia entre cuentas");
        System.out.println("7. Imprimir las personas morosas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    private static void program(){
        Person[] people = new Person[defaultSizeInPersonArray];
        int numPeople = defaultPeopleInArray;
        int option;
        do {
            showMenu();
            option = validNumValue();

            switch (option) {
                case 1:
                    people[numPeople] = instantiateAnObjectOfTypePerson();
                    numPeople++;
                    break;
                case 2:
                    instantiateObjectsOfTypeAccount(people,numPeople);
                    break;
                case 3:
                    showPersonalData(people,numPeople);
                    break;

                case 4:
                    showHowEnterPayroll(people,numPeople);
                    break;

                case 5:
                    showHowDoAPayment(people,numPeople);
                    break;

                case 6:
                    showHowMakeTransfer(people,numPeople);
                    break;

                case 7:
                    System.out.println("Personas morosas:");
                    printSlowPayerPeople(people, numPeople);
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
    private static Person instantiateAnObjectOfTypePerson(){
        System.out.print("Ingrese el DNI de la persona: ");
        String dni = enterDNI();
        System.out.println("Persona creada exitosamente.");
        return new Person(dni);
    }
    private static void instantiateObjectsOfTypeAccount(Person[] people, int numPeople){
        System.out.print("Ingrese el DNI de la persona a la que desea asociar la cuenta: ");
        String dni = enterDNI();
        Person person = searchPersonByDni(people, dni, numPeople);
        if (person != null) {
            Account account = createNewAccount();
            if (person.addAccounts(account)){
                System.out.println("Cuenta asociada correctamente.");
            } else {
                System.out.println("Este DNI ya tiene el máximo de cuentas permitidas.");
            }
        } else {
            System.out.println("No se encontró ninguna persona con ese DNI.");
        }
    }
    private static void showPersonalData(Person[] people, int numPeople){
        System.out.print("Ingrese el DNI de la persona: ");
        String dni = enterDNI();
        Person person = searchPersonByDni(people, dni, numPeople);
        if (person != null) {
            showPersonalData(person);
        } else {
            System.out.println("No se encontró ninguna persona con ese DNI.");
        }
    }

    private static void showHowEnterPayroll(Person[] people, int numPeople){
        System.out.print("Ingrese el DNI de la persona: ");
        String dni = enterDNI();
        Person person = searchPersonByDni(people, dni, numPeople);
        if (person != null) {
            System.out.print("Ingrese el número de cuenta: ");
            String numAccount = scanner.next();
            System.out.print("Ingrese el monto de la nómina: ");
            double amountPayroll = validNumValue();
            receivePayroll(people, dni, numAccount, amountPayroll, numPeople);
        } else {
            System.out.println("No se encontró ninguna persona con ese DNI.");
        }
    }

    private static void showHowDoAPayment(Person[] people, int numPeople){
        System.out.print("Ingrese el DNI del pagador: ");
        String dni = enterDNI();
        Person person = searchPersonByDni(people, dni, numPeople);
        if (person != null) {
            System.out.print("Ingrese el número de cuenta del pagador: ");
            String numAccount = scanner.next();
            Account account = searchAccountByNum(person.getAccounts(), numAccount);
            if (account != null) {
                System.out.print("Ingrese la cantidad a pagar: ");
                double paymentAmount = validNumValue();
                doPayment(person, account, paymentAmount);
            } else {
                System.out.println("La cuenta del pagador no existe.");
            }
        } else {
            System.out.println("No se encontró a un pagador con ese DNI.");
        }
    }

    private static void showHowMakeTransfer(Person[] people, int numPeople){
        System.out.print("Ingrese el DNI del remitente: ");
        String dniSender = enterDNI();
        Person sender = searchPersonByDni(people, dniSender, numPeople);
        if (sender != null) {
            System.out.print("Ingrese el número de cuenta del remitente: ");
            String numAccountSender = scanner.next();
            Account senderAccount = searchAccountByNum(sender.getAccounts(), numAccountSender);
            if (senderAccount != null) {
                System.out.print("Ingrese el DNI del destinatario: ");
                String dniReceiver = enterDNI();
                Person receiver = searchPersonByDni(people, dniReceiver, numPeople);
                if (receiver != null) {
                    System.out.print("Ingrese el número de cuenta del destinatario: ");
                    String numAccountReceiver = scanner.next();
                    Account receiverAccount = searchAccountByNum(receiver.getAccounts(), numAccountReceiver);
                    if (receiverAccount != null) {
                        System.out.print("Ingrese el monto de la transferencia: ");
                        double paymentAmount = validNumValue();
                        makeTransfer(sender, senderAccount, receiver, receiverAccount, paymentAmount);
                    } else {
                        System.out.println("La cuenta del destinatario no existe.");
                    }
                } else {
                    System.out.println("No se encontró al destinatario con ese DNI.");
                }
            } else {
                System.out.println("La cuenta del remitente no existe.");
            }
        } else {
            System.out.println("No se encontró al remitente con ese DNI.");
        }
    }

    private static Account createNewAccount(){
        String numAccount = generateRandomAccount();
        System.out.println("Su numero de cuenta es " + numAccount);
        System.out.print("Ingrese el saldo inicial de la cuenta: ");
        double initialBalance = validNumValue();
        return new Account(numAccount, initialBalance);
    }

    private static void showPersonalData(Person person) {
        System.out.println("Datos de la persona:");
        System.out.println("DNI: " + person.getDni());
        System.out.println("Cuentas asociadas:");
        for (int i = 0; i < person.getAccounts().length; i++) {
            if (person.getAccounts()[i] != null) {
                System.out.println("Número de cuenta: " + person.getAccounts()[i].getNumAccount());
                System.out.println("Saldo disponible: " + person.getAccounts()[i].getAvailableBalance());
            }
        }
    }

    private static Person searchPersonByDni(Person[] people, String dni, int numPeople) {
        for (int i = 0; i < numPeople; i++) {
            if (people[i].getDni().equals(dni)) {
                return people[i];
            }
        }
        return null;
    }

    private static void receivePayroll(Person[] people, String dni, String numAccount, double payrollAmount, int numPeople) {
        Person persona = searchPersonByDni(people, dni, numPeople);
        if (persona != null) {
            Account account = searchAccountByNum(persona.getAccounts(), numAccount);
            if (account != null) {
                account.receivePayment(payrollAmount);
                System.out.println("Nómina recibida correctamente en la cuenta " + numAccount + ". Nuevo saldo: " + account.getAvailableBalance());
            } else {
                System.out.println("La cuenta especificada no pertenece a la persona.");
            }
        } else {
            System.out.println("No se encontró ninguna persona con ese DNI.");
        }
    }

    private static void doPayment(Person receiver, Account receiverAccount, double paymentAmount) {
        receiverAccount.payBill(paymentAmount);
        System.out.println("Pago recibido realizado correctamente por la cuenta " + receiverAccount.getNumAccount() +
                " del pagador " + receiver.getDni() + ". Nuevo saldo: " + receiverAccount.getAvailableBalance());
    }


    private static void makeTransfer(Person sender, Account senderAccount,
                                     Person receiver, Account receiverAccount, double amountTransfer) {
        if (senderAccount.getAvailableBalance() >= amountTransfer) {
            senderAccount.payBill(amountTransfer);
            receiverAccount.receivePayment(amountTransfer);
            System.out.println("Transferencia exitosa de " + amountTransfer + " desde la cuenta " +
                    senderAccount.getNumAccount() + " del remitente " + sender.getDni() +
                    " a la cuenta " + receiverAccount.getNumAccount() + " del receptor " +
                    receiver.getDni());
        } else {
            System.out.println("Saldo insuficiente en la cuenta del remitente.");
        }
    }

    private static Account searchAccountByNum(Account[] accounts, String numAccount) {
        for (Account account : accounts) {
            if (account != null && account.getNumAccount().equals(numAccount)) {
                return account;
            }
        }
        return null;
    }

    private static void printSlowPayerPeople(Person[] people, int numPeople) {
        boolean thereAreSlowPayer = false;
        for (int i = 0; i < numPeople; i++) {
            if (people[i].isSlowPayer()) {
                thereAreSlowPayer = true;
                System.out.println("DNI: " + people[i].getDni());
                System.out.println("Cuentas morosas:");
                for (int j = 0; j < people[i].getAccounts().length; j++) {
                    if (people[i].getAccounts()[j] != null && people[i].getAccounts()[j].getAvailableBalance() < 0) {
                        System.out.println("Número de cuenta: " + people[i].getAccounts()[j].getNumAccount());
                        System.out.println("Saldo disponible: " + people[i].getAccounts()[j].getAvailableBalance());
                    }
                }
            }
        }
        if (!thereAreSlowPayer) {
            System.out.println("No hay people morosas.");
        }
    }

    private static boolean validateDNI(String dni) {
        boolean isValid = false;
        if (dni.length() == 9) {
            String numbers = dni.substring(0, 8);
            char letter = Character.toUpperCase(dni.charAt(8));
            try {
                int num = Integer.parseInt(numbers);
                char calculatedLetter = calculateDniLetter(num);
                isValid = (letter == calculatedLetter);
            } catch (NumberFormatException ignored) {
            }
        }
        return !isValid;
    }

    private static char calculateDniLetter(int num) {
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letters.charAt(num % 23);
    }

    private static String enterDNI(){
        String dniPerson = scanner.next().toUpperCase();
        while (validateDNI(dniPerson)) {
            System.out.print("DNI inválido. Por favor, ingrese un DNI válido: ");
            dniPerson = scanner.next().toUpperCase();
        }
        return dniPerson;
    }

    private static String generateRandomAccount() {
        StringBuilder account = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            account.append(random.nextInt(10));
        }
        return "ES" + account;
    }

    public static int validNumValue() {
        Scanner scanner = new Scanner(System.in);
        boolean numeroValido = false;
        int number = 0;
        while (!numeroValido) {
            try {
                number = scanner.nextInt();
                if (number < 0){
                    System.out.println("Por favor, escriba un número de preguntas correcto.");
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
}