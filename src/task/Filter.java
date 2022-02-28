package task;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Eftim
 */
public class Filter {
        
        private JSONTokener tk;
        private JSONArray allReviews;
        private List<JSONObject> filtList;
        private StringBuilder sb;

    public List<JSONObject> getFiltList() {
        return filtList;
    }

    public StringBuilder getSb() {
        return sb;
    }
    
    public Filter(){
        
        try (InputStream stream = TaskJFrame.class.getResourceAsStream("/task/resources/reviews.json")){
            this.tk = new JSONTokener(stream);
            this.allReviews = new JSONArray(tk);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void filterMinRating(String value) {
        this.filtList = new ArrayList<>();
        int minRating = Integer.parseInt(value);
        for (Object obj : allReviews) {
            if(((JSONObject)obj).getInt("rating") >= minRating){
                filtList.add((JSONObject)obj);
            }
        }
    }

    public void dateOrder(String value) {
        String dateOrderStyle = value;
        if(dateOrderStyle.equals("Newest First")){
            Collections.sort(filtList, new dateComp());
        }else{
            Collections.sort(filtList, new dateComp().reversed());
        }
    }

    public void ratingOrder(String value) {
        String ratingOrderStyle = value;
        if(ratingOrderStyle.equals("Highest First")){
            Collections.sort(filtList, new ratingComp());
        }else{
            Collections.sort(filtList, new ratingComp().reversed());
        }
    }

    public void textOrder(String value) {
        String textOrderStyle = value;
        if(textOrderStyle.equals("Yes")){
            Collections.sort(filtList, new textComp());
        }
    }
    
    public void textShown(){
        this.sb = new StringBuilder();
        for (JSONObject obj : filtList) {
            sb.append(obj.toString(5)).append("\n");
        }
    }
    
    static class dateComp implements Comparator<JSONObject>{

        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            LocalDateTime ldt1 = LocalDateTime.parse(o1.getString("reviewCreatedOnDate"), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            LocalDateTime ldt2 = LocalDateTime.parse(o2.getString("reviewCreatedOnDate"), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return ldt2.compareTo(ldt1);
        }
        
    }
    
    static class ratingComp implements Comparator<JSONObject>{

        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            return o2.getInt("rating") - o1.getInt("rating");
        }
        
    }
    
    static class textComp implements Comparator<JSONObject>{

        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            return o2.getString("reviewText").length() - o1.getString("reviewText").length();
        }
        
    }
}
