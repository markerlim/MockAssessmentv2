package task01;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException{
        if(args.length == 0){
            System.out.println("Error no filename provided");
            System.exit(1);
        }

        String filename = args[0];
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("File does not exist");
        }
        Reader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        List<Application> listofapp = new ArrayList<>();
        Map<String, List<Application>> appbycat = new HashMap<>();
        List<String> category = new ArrayList<>();
        String input;
        int count = 0;
        while((input = br.readLine())!=null){
            if(count == 0){
                count++;
                continue;
            }
            String[] holder = input.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if(!category.contains(holder[1].toUpperCase().trim())){
                category.add(holder[1].toUpperCase().trim());
            }
            Application app = new Application(holder[0].trim(),holder[1].toUpperCase().trim(),Float.parseFloat(holder[2]));
            listofapp.add(app);
        }

        for(String cat : category){
            List<Application> holding = new ArrayList<>();
            for(Application app : listofapp){
                if(cat.equals(app.getCategory())){
                    holding.add(app);
                }
            }
            appbycat.put(cat,holding);
        }
        for(String cat : appbycat.keySet()){
            if(isNumber(cat)){
                continue;
            }
            System.out.println("Category: " +cat);
            String highestRated = "";
            float highRating = 0f;
            float lowRating =5f;
            float sumofRating = 0f;
            String lowestRated = "";
            float averageRating = 0f;
            int num = 0;
            int discarded = 0;

            for(Application app: appbycat.get(cat)){
                if(app.getRating().toString() == "NaN"){
                    discarded++;
                    continue;
                }
                if(app.getRating() > highRating){
                    highRating = app.getRating();
                    highestRated = app.getApp();
                }
                if(app.getRating() < lowRating){
                    lowRating = app.getRating();
                    lowestRated = app.getApp();
                }
                sumofRating += app.getRating();
                num++;
            }
            averageRating = sumofRating/num;
            System.out.println("Highest: " + highestRated);
            System.out.println("Lowest: "+ lowestRated);
            System.out.println("Average: " + averageRating);
            System.out.println("Count: " + num);
            System.out.println("Discarded: "+ discarded);
            System.out.println("\n");
        }
    }

    public static boolean isNumber(String rating){
        if (rating == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(rating);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
