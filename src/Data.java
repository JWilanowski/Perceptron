import java.util.ArrayList;

public class Data {
    private ArrayList<Double> numList = new ArrayList<Double>();
    private String result;

    public void addNum(double d){
        numList.add(d);
    }
    public  void addResult(String s){
        result = s;
    }
    public ArrayList<Double> getNumList(){
        return numList;
    }
    public String getResult(){
        return result;
    }
}
