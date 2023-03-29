import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int genNumber = 1;
        double trainConst = 0.5;
        String trainPath = "train.txt";
        String testPath = "test.txt";
        float accurateGuesses = 0;

        int hashMapTemp = 0;

        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

        System.out.println("Podaj ilosc generacji");
        genNumber = scanner.nextInt();

        System.out.println("Podaj stałą uczenia ");
        trainConst = scanner.nextDouble();

        System.out.println("Podaj ścieżkę do pliku treningowego");
        //trainPath = Paths.get("train.txt");

        System.out.println("Podaj ścieżkę do pliku testowego");
        //testPath = Paths.get("test.txt");

        List<String[]> trainList = new ArrayList<String[]>();
        List<String[]> testList = new ArrayList<String[]>();
        ArrayList<Data> trainListData = new ArrayList<Data>();
        ArrayList<Data> testListData = new ArrayList<Data>();

        ArrayList trainListName = new ArrayList<>();
        ArrayList testListName = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainPath)));
        String line = null;
//Read data from files
        while ((line = reader.readLine()) != null) {
            trainList.add(line.split(","));
        }
        reader.close();

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(testPath)));
        line = null;

        while ((line = reader2.readLine()) != null) {
            testList.add(line.split(","));
        }
        reader2.close();
        //Read test data to list
        for (int i = 0; i < testList.size(); i++) {
            Data data = new Data();
            data.addResult(testList.get(i)[testList.get(i).length-1]);

            for(int j = 0; j < testList.get(i).length - 1; j++){
                data.addNum(Double.parseDouble(testList.get(i)[j]));

            }
            testListData.add(data);
        }
        //Read train data to list
        for (int i = 0; i < trainList.size(); i++) {
            Data data = new Data();
            data.addResult(trainList.get(i)[trainList.get(i).length-1]);
            if(!hashMap.containsKey(trainList.get(i)[trainList.get(i).length-1])){
                hashMap.put(trainList.get(i)[trainList.get(i).length-1], hashMapTemp);
                hashMapTemp++;
            }
            for(int j = 0; j < trainList.get(i).length - 1; j++){
                data.addNum(Double.parseDouble(trainList.get(i)[j]));
            }
            trainListData.add(data);
        }
        double[] weights = new double[testListData.get(0).getNumList().size()];
        //System.out.println(weights.toString());
        int border = 0;

        //Training perceptron
        for(int i=0; i<genNumber;i++) {
            Collections.shuffle(trainListData);
            accurateGuesses = 0;
            for (int l = 0; l < trainListData.size() ; l++) {
                double tempNum = 0;
                int flag;
                ArrayList<Double> tempList = trainListData.get(l).getNumList();
                //System.out.println(tempList.toString());
                for (int j = 0; j < tempList.size(); j++) {
                    tempNum = tempNum + weights[j] * tempList.get(j);
                }

                if (!((tempNum >= border && hashMap.get(trainListData.get(l).getResult()) == 1) || (tempNum < border && hashMap.get(trainListData.get(l).getResult()) == 0))) {
                    if (tempNum >= border)
                        flag = 1;
                    else
                        flag = 0;
                    for (int k = 0; k < weights.length; k++) {
                        weights[k] = weights[k] + (hashMap.get(trainListData.get(l).getResult()) - flag) * trainConst * tempList.get(k);
                    }
                }
                /**
                if(tempNum>=border)
                    flag = 1;
                else
                    flag = 0;
                if(flag != hashMap.get(trainListData.get(l).getResult()))

                for(int test =0; test< weights.length;test++){
                    System.out.print(weights[test]+",");
                }
                System.out.println("");
                 **/
            }

            for(int j=0;j<testListData.size();j++) {
                double tempNum = 0;

                ArrayList<Double> tempList = testListData.get(j).getNumList();
                for (int k = 0; k < tempList.size(); k++) {
                    tempNum = tempNum + weights[k] * tempList.get(k);
                }
               // System.out.println(tempNum+", " + hashMap.get(testListData.get(j).getResult()));


                if (((tempNum >= border && hashMap.get(testListData.get(j).getResult()) == 1) || (tempNum < border && hashMap.get(testListData.get(j).getResult()) == 0))) {
                    accurateGuesses++;
                }
            }
            //System.out.println(weights.toString());
            System.out.println((accurateGuesses/testListData.size())*100);

        }

/**
            for(int j=0;j<testListData.size();j++) {
                double tempNum = 0;

                ArrayList<double> tempList = trainListData.get(j).getNumList();
                for (int k = 0; k < tempList.size(); k++) {
                    tempNum = tempNum + weights[k] * tempList.get(j);
                }

                if (((tempNum >= border && hashMap.get(trainListData.get(j).getResult()) == 1) || (tempNum < border && hashMap.get(trainListData.get(i).getResult()) == 0))) {
                    accurateGuesses++;
                }
            }
            **/


    }
}