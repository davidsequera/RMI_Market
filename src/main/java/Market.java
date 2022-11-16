import java.rmi.*;

public interface Market extends Remote {
    String adquirir(int id) throws RemoteException;
    String consultar() throws RemoteException;
}