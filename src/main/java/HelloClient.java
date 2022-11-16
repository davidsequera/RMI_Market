import java.rmi.Naming;

public class HelloClient {
    public static String port = "1515";
    public static String address = "127.0.0.1";

    public static void main(String[] args) {
        try {
            HelloWorld obj = (HelloWorld) Naming.lookup("//" + address+':'+port+ "/HelloWorld");
            System.out.println(obj.consultar());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}