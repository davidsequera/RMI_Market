import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static String port = "1515";
    public static String address = "127.0.0.1";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Market obj = (Market) Naming.lookup("//" + address+':'+port+ "/Market");
            while (true) {
                System.out.println("\n\n1. Consultar\n2. Adquirir\n3. Salir");
                int option = sc.nextInt();
                    if (option == 1){
                        System.out.println(obj.consultar());
                        continue;
                    }
                    if (option == 2){
                        System.out.println("Ingrese el id del producto que quiere comprar");
                        int id = sc.nextInt();
                        System.out.println(obj.adquirir(id));
                        continue;
                    }
                    if (option == 3){
                        break;
                    }
                    System.out.println("Opcion invalida");
                }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}