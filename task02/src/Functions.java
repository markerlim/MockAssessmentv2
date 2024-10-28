import java.util.Comparator;
import java.util.List;

public class Functions {
    private String request_id;
    private int item_count;
    private Float budget;

    public List<Product> Sort(List<Product> listofpdts){
        List<Product> result = listofpdts.stream()
        .sorted(Comparator.comparing(Product::getRating).reversed()
            .thenComparing(Product::getPrice).reversed())
        .toList();
        return result;
    }

    public void Particulars(String line){
        String[] holder = line.split("\n");
        this.request_id = holder[0].replaceAll("request_id:", "").trim();
        this.item_count = Integer.parseInt(holder[1].replaceAll("item_count:", "").trim());
        this.budget = Float.parseFloat(holder[2].replaceAll("budget:", "").trim());
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    
}

