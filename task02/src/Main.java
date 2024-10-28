import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException {
        int port = 3000;
        String host = "localhost";
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        Socket conn = new Socket(host, port);
        Socket socket2 = new Socket(host, port);
        InputStream is = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buffer = new byte[1024];
        int bytesRead;
        String oneItem = "";
        String holder = "";
        System.out.println("Reading");
        while (true) {
            bytesRead = bis.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead);
            holder += receivedData;
            if(bytesRead < 1024){
                break;
            }
        }
        Functions function = new Functions();
        List<String> listofobjects = new ArrayList<>();
        for(String s : holder.split("prod_end")){
            s = s.replaceAll("prod_start", "").trim();
            if(s.contains("prod_list")){
                String[] tmp = s.split("prod_list");
                function.Particulars(tmp[0]);
                s = tmp[1].trim();
            }
            listofobjects.add(s);
            System.out.println("===========\n" + s);
        }
        List<Product> listofpdts = new ArrayList<>();

        for(String s : listofobjects){
            String[] s2 = s.split("\n");
            if (s2.length >= 4 && !s2[0].isEmpty() && s2[0].contains("prod_id:")) {
                try {
                    int id = Integer.parseInt(s2[0].replaceAll("prod_id:", "").trim());
                    String title = s2[1].replaceAll("title:", "").trim();
                    Float price = Float.parseFloat(s2[2].replaceAll("price:", "").trim());
                    Float rating = Float.parseFloat(s2[3].replaceAll("rating:", "").trim());
                    Product product = new Product(id, title, price, rating);
                    listofpdts.add(product);
                } catch (NumberFormatException e) {

                }
            }
        }
        listofpdts = function.Sort(listofpdts);

        String listofid = "";
        Float budgetInt = function.getBudget();
        int countItem = 0;
        System.out.println("calculating:" + budgetInt);
        while(budgetInt > 0){
            if(countItem == function.getItem_count()){
                listofid = listofid.replaceFirst(",", "");
                break;
            }
            float price = listofpdts.get(countItem).getPrice();
            if(price > budgetInt){
                countItem++;
                continue;
            } else {
                listofid += "," + listofpdts.get(countItem).getId();
                budgetInt -= price;
                countItem++;
            }

        }
                
        Console cons = System.console();

        String name = cons.readLine(">Name: ");
        String email = cons.readLine(">Email: ");
        Float spent = function.getBudget() - budgetInt;

        OutputStream os = conn.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        String line1 = "request_id: "+function.getRequest_id()+"\n";
        String line2 = "name: "+name+"\n";
        String line3 = "email: "+email+"\n";
        String line4 = "items: "+listofid+"\n";
        String line5 = "spent: "+spent.toString()+"\n";
        String line6 = "remaining: "+budgetInt.toString()+"\n";
        String line7 = "client_end\n";

        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
        System.out.println(line4);
        System.out.println(line5);
        System.out.println(line6);
        System.out.println(line7);

        bos.write(line1.getBytes()); 
        bos.write(line2.getBytes()); 
        bos.write(line3.getBytes()); 
        bos.write(line4.getBytes()); 
        bos.write(line5.getBytes()); 
        bos.write(line6.getBytes()); 
        bos.write(line7.getBytes()); 
        bos.flush();

        StringBuilder holder2 = new StringBuilder();
        while (true) {
            bytesRead = bis.read(buffer);
            if (bytesRead == -1) break; // End of stream
            String receivedData = new String(buffer, 0, bytesRead);
            holder2.append(receivedData);
            if (bytesRead < 1024) {
                break;
            }
        }
        System.out.println(holder2);
    }
}
