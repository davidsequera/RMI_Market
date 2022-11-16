import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;


public class MarketServer extends UnicastRemoteObject implements HelloWorld {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static ArrayList<Item> items = new ArrayList<Item>();
    public static String header ="";
    public static String headerCSV= "";

    public MarketServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1515);
            MarketServer obj = new MarketServer();
            Naming.rebind("rmi://127.0.0.1:1515/HelloWorld", obj);
            addItems("products.csv");
        } catch (Exception ex) {
            System.out.println("[Server Exception]: " + ex.getMessage());
        }
    }

    @Override
    public String adquirir(int id) throws RemoteException {
        // contains
        int index = searchItem(id);
        if (index == -1) return "Item not found";
        Item item = items.get(index);
        removeItem(index);
        return "Item found\n"+header+'\n'+item.toString();
    }
    private void removeItem(int index) {
        items.remove(index);
        System.out.println("Item"+index+"removed");
    }
    private void updateItems(){
        try {
            String path = "src/main/resources/" + csv;

            FileWriter fileWriter = new FileWriter(path);
            fileWrite
            String[] record;
            // Read header
            if((record = csvReader.readNext()) != null) for(String r: record) {
                header += r + '\t';
                headerCSV += r + ", ";
            }
            // we are going to read data line by line
            while ((record = csvReader.readNext()) != null) {
                Item item = new Item(record);
                items.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int searchItem(int id){
        for (Item item : items) {
            if (item.id == id) {
                return items.indexOf(item);
            }
        }
        return -1;
    }
    @Override
    public String consultar() throws RemoteException {
        // list elements
        String list = header;
        for (Item item : items) {
            list += '\n'+item.toString();
        }
        return list;
    }


    public void addItem(Item item) {
        items.add(item);
    }
    // Add products from a csv file
    private static void addItems(String csv) {
        try {
            String path = "src/main/resources/" + csv;
            FileReader filereader = new FileReader(path);
            CSVReader csvReader = new CSVReader(filereader);
            String[] record;
            // Read header
            if((record = csvReader.readNext()) != null) for(String r: record) {
                header += r + '\t';
                headerCSV += r + ", ";
            }
            // we are going to read data line by line
            while ((record = csvReader.readNext()) != null) {
                Item item = new Item(record);
                items.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}