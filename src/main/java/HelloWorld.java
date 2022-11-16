import java.rmi.*;

public interface HelloWorld extends Remote {
    public String adquirir() throws RemoteException;
    public String consultar() throws RemoteException;
}