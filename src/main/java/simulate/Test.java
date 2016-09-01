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

/**
 * Created by wso2123 on 8/30/16.
 */
public class Test {
    public static void testModel(String modelName) throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {
        System.out.println("Testing Model : " + modelName);
        System.out.println("---------------------------------------------------------------------");

        FileOperator fileOperator = new FileOperator();
        PredictiveModel predictiveModel = new PredictiveModel(modelName);

        DataSet testDataset = fileOperator.openCSVFile("testData1.csv");
        DataSet resultset = fileOperator.openCSVFile("result.csv");

        Filter.removeUnwantedFeatures(testDataset, predictiveModel);
        ArrayList<String[]> dataRows = Filter.remainingLifeFilter(testDataset, "UnitNumber");

        ArrayList<Double> predicted = new ArrayList<Double>();
        ArrayList<Double> actual = new ArrayList<Double>();
        for(String str: resultset.getDataColumn("actual")){
            actual.add(Double.parseDouble(str));
        }


        for(String[] row: dataRows){
            predicted.add(Double.parseDouble(String.valueOf(predictiveModel.predict(row))));
        }

        System.out.println("Actual\tPredicted");
        for(int i=0; i<actual.size(); i++){
            System.out.println(actual.get(i) + "  \t" + predicted.get(i));
        }

        System.out.println("---------------------------------------------------------------------");
        DecimalFormat f = new DecimalFormat("##.000000");
        System.out.println("Root Mean Squared Error           : " + f.format(Error.rootMeanSquaredError(actual, predicted)));
        System.out.println("Mean Absolute Percentage Error    : " + f.format(Error.meanAbsolutePercentError(actual, predicted)) + " %");
        System.out.println("Root Mean Square Percentage Error : " + f.format(Error.rootMeanSquarePercentageError(actual, predicted)) + " %");
        System.out.println("---------------------------------------------------------------------");

        Chart chart = new Chart("Test", "Actual vs Predicted", "Machine", "RUL");
        chart.plot2SeriesChart(actual, "Actual", predicted, "Predicted");

    }
    public static void test() throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {
        FileOperator fileOperator = new FileOperator();
        PredictiveModel predictiveModel = new PredictiveModel("ma.Model.2016-08-31_10-53-56");
        DataSet testDataset = fileOperator.openCSVFile("san.csv");

        ArrayList<String[]> dataRows = testDataset.getDataRows();
        ArrayList<String> predicted = new ArrayList<String>();

        for(String[] row: dataRows){
            predicted.add(String.valueOf(predictiveModel.predict(row)));
        }

        for(String s: predicted){
            System.out.println(s);
        }
    }
}
