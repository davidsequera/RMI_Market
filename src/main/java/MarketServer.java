import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serial;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;


public class MarketServer extends UnicastRemoteObject implements Market {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public static ArrayList<Item> items = new ArrayList<>();
    public static String header ="";
    public static String headerCSV= "";
    public static String file = "src/main/resources/products.csv";


    public MarketServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1515);
            MarketServer obj = new MarketServer();
            Naming.rebind("rmi://127.0.0.1:1515/Market", obj);
            addItems();
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
        updateItems();
        return "Item found\n"+header+'\n'+item.toString();
    }
    private void removeItem(int index) {
        Item i = items.get(index);
        items.remove(index);
        System.out.println("Item"+i.id+"removed");
    }
    private void updateItems(){
        try {
            String path = "src/main/resources/" + "products.csv";
            FileWriter fileWriter = new FileWriter(path);
            CSVWriter writer = new CSVWriter(fileWriter, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(headerCSV.split(", "));
            for (Item item : items) {
                writer.writeNext(item.toCSV().split(", "));
            }
            writer.close();
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
    private static void addItems() {
        try {
            String path = file;
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