package simulate;

import data.DataSet;
import data.Filter;
import evaluate.Error;
import io.FileOperator;
import ml.PredictiveModel;
import mlPlot.Chart;
import org.wso2.carbon.ml.core.exceptions.MLModelHandlerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/30/16.
 */
public class Test {
    public static void testModel1(String modelName) throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {
        System.out.println("Testing Model : " + modelName);
        System.out.println("---------------------------------------------------------------------");

        FileOperator fileOperator = new FileOperator();
        PredictiveModel predictiveModel = new PredictiveModel(modelName);

        //DataSet testDataset = fileOperator.openCSVFile("testData1.csv");
        DataSet testDataset = fileOperator.openCSVFile(modelName + ".csv");
        DataSet resultset = fileOperator.openCSVFile("result1.csv");

        //Filter.removeUnwantedFeatures(testDataset, predictiveModel);
        ArrayList<String[]> dataRows = Filter.remainingLifeFilter(testDataset, "UnitNumber");


        ArrayList<Double> predicted = new ArrayList<Double>();
        ArrayList<Double> actual = new ArrayList<Double>();
        ArrayList<Double> machineNumber = new ArrayList<Double>();
        for(String str: resultset.getDataColumn("actual")){
            actual.add(Double.parseDouble(str));
        }


        for(String[] row: dataRows){
            String[] tmp = new String[row.length-1];
            for(int i=1; i<row.length; i++){
                tmp[i-1] = row[i];
            }
            predicted.add(Double.parseDouble(String.valueOf(predictiveModel.predict(tmp))));
        }

        System.out.println("Actual\tPredicted");
        for(int i=0; i<actual.size(); i++){
            machineNumber.add((double)i);
            System.out.println(actual.get(i) + "  \t" + predicted.get(i));
        }

        System.out.println("---------------------------------------------------------------------");
        DecimalFormat f = new DecimalFormat("##.000000");
        System.out.println("Root Mean Squared Error           : " + f.format(Error.rootMeanSquaredError(actual, predicted)));
        System.out.println("Mean Absolute Percentage Error    : " + f.format(Error.meanAbsolutePercentError(actual, predicted)) + " %");
        System.out.println("Root Mean Square Percentage Error : " + f.format(Error.rootMeanSquarePercentageError(actual, predicted)) + " %");
        System.out.println("Mean Absolute Error               : " + f.format(Error.meanAbsoluteError(actual, predicted)));
        System.out.println("---------------------------------------------------------------------");

        Chart chart = new Chart("Test", "Actual vs Predicted", "Machine", "RUL");
        chart.plot2SeriesChart(machineNumber, actual, "Actual", machineNumber, predicted, "Predicted");

    }

    public static void testModel2(String modelName) throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {
        System.out.println("Testing Model : " + modelName);
        System.out.println("---------------------------------------------------------------------");

        FileOperator fileOperator = new FileOperator();
        PredictiveModel predictiveModel = new PredictiveModel(modelName);

        DataSet testDataset = fileOperator.openCSVFile("testData2.csv");
        DataSet resultset = fileOperator.openCSVFile("result2.csv");

        //Filter.removeUnwantedFeatures(testDataset, predictiveModel);
        ArrayList<String[]> dataRows = Filter.remainingLifeFilter(testDataset, "UnitNumber");

        ArrayList<Double> predicted = new ArrayList<Double>();
        ArrayList<Double> actual = new ArrayList<Double>();
        ArrayList<Double> machineNumber = new ArrayList<Double>();
        for(String str: resultset.getDataColumn("actual")){
            actual.add(Double.parseDouble(str));
        }


        for(String[] row: dataRows){
            predicted.add(Double.parseDouble(String.valueOf(predictiveModel.predict(row))));
        }

        System.out.println("Actual\tPredicted");
        for(int i=0; i<actual.size(); i++){
            machineNumber.add((double)i);
            System.out.println(actual.get(i) + "  \t" + predicted.get(i));
        }

        System.out.println("---------------------------------------------------------------------");
        DecimalFormat f = new DecimalFormat("##.000000");
        System.out.println("Root Mean Squared Error           : " + f.format(Error.rootMeanSquaredError(actual, predicted)));
        System.out.println("Mean Absolute Percentage Error    : " + f.format(Error.meanAbsolutePercentError(actual, predicted)) + " %");
        System.out.println("Root Mean Square Percentage Error : " + f.format(Error.rootMeanSquarePercentageError(actual, predicted)) + " %");
        System.out.println("Mean Absolute Error               : " + f.format(Error.meanAbsoluteError(actual, predicted)));
        System.out.println("---------------------------------------------------------------------");

        Chart chart = new Chart("Test", "Actual vs Predicted", "Machine", "RUL");
        chart.plot2SeriesChart(machineNumber, actual, "Actual", machineNumber, predicted, "Predicted");

    }


    /*
    public static void runClassificationRemainingTime(String modelName) throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {
        System.out.println("Testing Model : " + modelName);
        System.out.println("---------------------------------------------------------------------");

        FileOperator fileOperator = new FileOperator();
        PredictiveModel predictiveModel = new PredictiveModel(modelName);

        DataSet testDataset = fileOperator.openCSVFile("testData1.csv");
        ArrayList<String[]> dataRows = Filter.remainingLifeFilter(testDataset, "UnitNumber");

        for(String[] row: dataRows){
            String stat =  "0";
            int count = Integer.parseInt(row[1]);
            while(!stat.equals("1")){
                row[1] = String.valueOf(count);
                stat = String.valueOf(predictiveModel.predict(row));
                System.out.println(stat);
                count++;
            }
            System.out.println(count);
        }
    }
    */
}
