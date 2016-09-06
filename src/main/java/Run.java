import data.DataSet;
import featureeng.Autocorrelation;
import featureeng.BinaryClassification;
import featureeng.Entropy;
import featureeng.Kurtosis;
import featureeng.MovingAverage;
import featureeng.MovingMedian;
import featureeng.Percentile;
import featureeng.ProbabilityDistribution;
import featureeng.RemainingUsefulLife;
import featureeng.StandardDeviation;
import featureeng.ZScore;
import io.FileOperator;
import mlPlot.Chart;
import org.jfree.ui.RefineryUtilities;
import org.wso2.carbon.ml.core.exceptions.MLModelHandlerException;
import simulate.AUCTest;
import simulate.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by wso2123 on 8/23/16.
 */
public class Run {
    public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {

        /*
        //MLRandom Forest Classification
        //removed features  : 16, 18, 19, 6
        //trees : 10
        //iterations : 10
        //Accuracy : 98.71
        FileOperator fileOperator = new FileOperator();
        DataSet dataSet = fileOperator.openCSVFile("train1.csv");
        BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        ZScore.add(dataSet, "UnitNumber", features, true);
        MovingAverage.add(dataSet, "UnitNumber", features, 5);
        MovingMedian.add(dataSet, "UnitNumber", features, 5);
        StandardDeviation.add(dataSet, "UnitNumber", features, 5);
        MovingAverage.add(dataSet, "UnitNumber", features, 10);
        MovingMedian.add(dataSet, "UnitNumber", features, 10);
        StandardDeviation.add(dataSet, "UnitNumber", features,10);
        DataSet subset = dataSet.removeFromAbove("UnitNumber", 9);
        fileOperator.saveCSVFile("MLPM-BIN-0004.csv",subset);
        */



        /*
        BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");

        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};

        ZScore.add(dataSet, "UnitNumber", features, true);
        MovingAverage.add(dataSet, "UnitNumber", features, 5);
        MovingMedian.add(dataSet, "UnitNumber", features, 5);
        StandardDeviation.add(dataSet, "UnitNumber", features, 5);
        MovingAverage.add(dataSet, "UnitNumber", features, 10);
        MovingMedian.add(dataSet, "UnitNumber", features, 10);
        StandardDeviation.add(dataSet, "UnitNumber", features,10);
        Autocorrelation.add(dataSet, "UnitNumber", features, 5, 3);

        DataSet subset = dataSet.removeFromAbove("UnitNumber", 9);
        System.out.println(subset.getColumnCount());
        System.out.println(subset.getRowCount());
        */

        /*
        Accuracy 97
        BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");

        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};

        ZScore.add(dataSet, "UnitNumber", features, false);
        MovingAverage.add(dataSet, "UnitNumber", features, 5);
        MovingMedian.add(dataSet, "UnitNumber", features, 5);
        StandardDeviation.add(dataSet, "UnitNumber", features, 5);
        MovingAverage.add(dataSet, "UnitNumber", features, 10);
        MovingMedian.add(dataSet, "UnitNumber", features, 10);
        StandardDeviation.add(dataSet, "UnitNumber", features,10);
        Autocorrelation.add(dataSet, "UnitNumber", features, 5, 3);
        DataSet subset = dataSet.removeFromAbove("UnitNumber", 9);
        fileOperator.saveCSVFile("MLPM-BIN-00011.csv",subset);
        */
        /*
        //Autocorrelation measure test
        BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        ZScore.add(dataSet, "UnitNumber", features, true);
        ArrayList<String> autocorrelationData = Autocorrelation.add(dataSet, "UnitNumber", "Sensor2", 5, 2);
        System.out.println(Arrays.toString(autocorrelationData.toArray()));
        */

        //ProbabilityDistribution.add(dataSet, "UnitNumber", "Sensor2", 10);

        //BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");

        //String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};

        /*
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        ProbabilityDistribution.add(dataSet, "UnitNumber", features, 10);
        fileOperator.saveCSVFile("test.csv", dataSet);
        */

        /*
        //Sanjaya
        dataSet = fileOperator.openCSVFile("trainerData.csv");
        ArrayList<String> category = new ArrayList<String>();
        for(int i=0; i<768; i++){
            category.add("1");
        }

        dataSet.addFeature("cat");
        dataSet.addDataColumn("cat", category);

        String[] features = {"windspeed","temperature","solarflux","humidity"};

        MovingAverage.add(dataSet, "cat", features, 5);
        MovingMedian.add(dataSet, "cat", features, 5);
        StandardDeviation.add(dataSet, "cat", features, 5);

        DataSet subset = dataSet.removeFromAbove("cat", 4);
        fileOperator.saveCSVFile("san.csv", subset);

        FileOperator fileOperator = new FileOperator();
        DataSet dataSet = fileOperator.openCSVFile("testData.csv");
        ArrayList<String> category = new ArrayList<String>();
        for(int i=0; i<1056; i++){
            category.add("1");
        }
        dataSet.addFeature("cat");
        dataSet.addDataColumn("cat", category);

        String[] features = {"windspeed","temperature","solarflux","humidity"};

        MovingAverage.add(dataSet, "cat", features, 5);
        MovingMedian.add(dataSet, "cat", features, 5);
        StandardDeviation.add(dataSet, "cat", features, 5);

        DataSet subset = dataSet.removeFromAbove("cat", 4);
        fileOperator.saveCSVFile("san.csv", subset);
        */

        //29..28.29016
        /*
        BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        ZScore.add(dataSet, "UnitNumber", features, true);
        MovingAverage.add(dataSet, "UnitNumber", features, 5);
        MovingMedian.add(dataSet, "UnitNumber", features, 5);
        StandardDeviation.add(dataSet, "UnitNumber", features, 5);
        MovingAverage.add(dataSet, "UnitNumber", features, 10);
        MovingMedian.add(dataSet, "UnitNumber", features, 10);
        StandardDeviation.add(dataSet, "UnitNumber", features,10);
        Autocorrelation.add(dataSet, "UnitNumber", features, 5, 3);

        DataSet subset = dataSet.removeFromAbove("UnitNumber", 9);
        fileOperator.saveCSVFile("MLPM-BIN-00019.csv", subset);
        */

        //30.08.2016
        //Do not remove - Original implementation
        /*
        //Generating Training Data Set
        FileOperator fileOperator = new FileOperator();
        DataSet dataSet = fileOperator.openCSVFile("train1.csv");

        RemainingUsefulLife.add(dataSet, "UnitNumber", "Time");
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        ZScore.add(dataSet, "UnitNumber", features, true);
        MovingAverage.add(dataSet, "UnitNumber", features, 5);
        MovingMedian.add(dataSet, "UnitNumber", features, 5);
        StandardDeviation.add(dataSet, "UnitNumber", features, 5);
        MovingAverage.add(dataSet, "UnitNumber", features, 10);
        MovingMedian.add(dataSet, "UnitNumber", features, 10);
        StandardDeviation.add(dataSet, "UnitNumber", features,10);
        Autocorrelation.add(dataSet, "UnitNumber", features, 5, 3);
        Percentile.add(dataSet, "UnitNumber", features, 15, 0.90);
        DataSet subset = dataSet.removeFromAbove("UnitNumber", 14);
        fileOperator.saveCSVFile("MLPM-RUL-00026.csv", subset);
        */

        /*
        //Generating Testing Data Set
        FileOperator fileOperator = new FileOperator();
        DataSet testData = fileOperator.openCSVFile("testData.csv");
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        ZScore.add(testData, "UnitNumber", features, true);
        MovingAverage.add(testData, "UnitNumber", features, 5);
        MovingMedian.add(testData, "UnitNumber", features, 5);
        StandardDeviation.add(testData, "UnitNumber", features, 5);
        MovingAverage.add(testData, "UnitNumber", features, 10);
        MovingMedian.add(testData, "UnitNumber", features, 10);
        StandardDeviation.add(testData, "UnitNumber", features,10);
        Autocorrelation.add(testData, "UnitNumber", features, 5, 3);
        Percentile.add(testData, "UnitNumber", features, 10, 0.80);
        DataSet testSubset = testData.removeFromAbove("UnitNumber", 14);
        fileOperator.saveCSVFile("testData1.csv", testSubset);
        */


        //Testing Model
        //Test.randomForestRegression();
        //Test.ridgeRegression();


        //31.08.2016
        //Train Data
//        FileOperator fileOperator = new FileOperator();
//        DataSet dataSet = fileOperator.openCSVFile("train1.csv");
//        RemainingUsefulLife.add(dataSet, "UnitNumber", "Time");
//        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
//        MovingAverage.add(dataSet, "UnitNumber", features, 5);
//        StandardDeviation.add(dataSet, "UnitNumber", features, 10);
//        ProbabilityDistribution.add(dataSet, "UnitNumber", features, 250);
//        String[] features1 = {"prob_250_Sensor1", "prob_250_Sensor2","prob_250_Sensor3", "prob_250_Sensor4", "prob_250_Sensor5", "prob_250_Sensor6", "prob_250_Sensor7", "prob_250_Sensor8", "prob_250_Sensor9", "prob_250_Sensor10", "prob_250_Sensor11", "prob_250_Sensor12", "prob_250_Sensor13", "prob_250_Sensor14", "Sensor15", "Sensor16","prob_250_Sensor17", "prob_250_Sensor18", "prob_250_Sensor19", "prob_250_Sensor20", "prob_250_Sensor21"};
//        ZScore.add(dataSet, "UnitNumber", features1, 5, false);
//        fileOperator.saveCSVFile("MLPM-RUL-00064.csv", dataSet);


        //Test data set 1
        FileOperator fileOperator = new FileOperator();
        DataSet testData = fileOperator.openCSVFile("testData1In.csv");
        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        MovingAverage.add(testData, "UnitNumber", features, 5);
        StandardDeviation.add(testData, "UnitNumber", features, 10);
        ProbabilityDistribution.add(testData, "UnitNumber", features, 250);
        String[] features1 = {"prob_250_Sensor1", "prob_250_Sensor2","prob_250_Sensor3", "prob_250_Sensor4", "prob_250_Sensor5", "prob_250_Sensor6", "prob_250_Sensor7", "prob_250_Sensor8", "prob_250_Sensor9", "prob_250_Sensor10", "prob_250_Sensor11", "prob_250_Sensor12", "prob_250_Sensor13", "prob_250_Sensor14", "Sensor15", "Sensor16","prob_250_Sensor17", "prob_250_Sensor18", "prob_250_Sensor19", "prob_250_Sensor20", "prob_250_Sensor21"};
        ZScore.add(testData, "UnitNumber", features1, 5, false);
        fileOperator.saveCSVFile("RFR.Model.2016-09-06_11-20-32.csv", testData);


        //Test data set 2
//        FileOperator fileOperator = new FileOperator();
//        DataSet testData = fileOperator.openCSVFile("testData2In.csv");
//        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
//        ProbabilityDistribution.add(testData, "UnitNumber", features, 250);
//        String[] features1 = {"prob_250_Sensor1", "prob_250_Sensor2","prob_250_Sensor3", "prob_250_Sensor4", "prob_250_Sensor5", "prob_250_Sensor6", "prob_250_Sensor7", "prob_250_Sensor8", "prob_250_Sensor9", "prob_250_Sensor10", "prob_250_Sensor11", "prob_250_Sensor12", "prob_250_Sensor13", "prob_250_Sensor14", "Sensor15", "Sensor16","prob_250_Sensor17", "prob_250_Sensor18", "prob_250_Sensor19", "prob_250_Sensor20", "prob_250_Sensor21"};
//        ZScore.add(testData, "UnitNumber", features1, 5, false);
//        fileOperator.saveCSVFile("testData2.csv", testData);


        //RFR.Model.2016-09-01_11-23-47
        //Test.testModel1("RFR.Model.2016-09-06_11-20-32");
        //Test.test();


        //AUC Test
        //RandomForest.Model.2016-08-25_14-42-48
//        FileOperator fileOperator = new FileOperator();
//        DataSet dataSet = fileOperator.openCSVFile("train1.csv");
//        BinaryClassification.add(dataSet, "UnitNumber", 30, "1", "0");
//        String[] features = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
//        ZScore.add(dataSet, "UnitNumber", features, true);
//        MovingAverage.add(dataSet, "UnitNumber", features, 5);
//        MovingMedian.add(dataSet, "UnitNumber", features, 5);
//        StandardDeviation.add(dataSet, "UnitNumber", features, 5);
//        MovingAverage.add(dataSet, "UnitNumber", features, 10);
//        MovingMedian.add(dataSet, "UnitNumber", features, 10);
//        StandardDeviation.add(dataSet, "UnitNumber", features, 10);
//        Autocorrelation.add(dataSet, "UnitNumber", features, 5, 3);
//        DataSet subset = dataSet.removeFromAbove("UnitNumber", 9);
//
//        AUCTest.run("RandomForest.Model.2016-08-25_14-42-48", subset, 100, 100, "Binary-Classification");

    }
}
