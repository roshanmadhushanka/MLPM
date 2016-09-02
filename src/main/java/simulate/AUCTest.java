package simulate;

import data.DataSet;
import ml.ConfusionMatrix;
import ml.PredictiveModel;
import mlMath.MLRandom;
import org.wso2.carbon.ml.core.exceptions.MLModelHandlerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/31/16.
 */
public class AUCTest {
    public static void run(String modelName, DataSet dataSet, int confusionMatrixSize, int numberOfMatrices, String predictingFeature) throws ClassNotFoundException, IOException, URISyntaxException, MLModelHandlerException {
        int max = dataSet.getRowCount();
        int min = 0;

        //Generate test case logic
        MLRandom mlRandom = new MLRandom();
        ArrayList<ArrayList<Integer>> testCases = mlRandom.nonRepeatingRandomList(min, max, confusionMatrixSize, numberOfMatrices);

        //Store actual values
        ArrayList<String> actualValues = dataSet.getDataColumn(predictingFeature);

        //Refine data set to generate test cases
        dataSet.removeDataColumn(predictingFeature);

        //Load model
        PredictiveModel predictiveModel = new PredictiveModel(modelName);

        for(ArrayList<Integer> testCase: testCases){
            ArrayList<String> actual = new ArrayList<String>();
            ArrayList<String> predict = new ArrayList<String>();
            ConfusionMatrix confusionMatrix = new ConfusionMatrix();

            for(int row: testCase){
                String[] dataRow = dataSet.getDataRow(row);
                actual.add(actualValues.get(row));
                predict.add(String.valueOf(predictiveModel.predict(dataRow)));
            }

            confusionMatrix.generate(actual, predict);
            confusionMatrix.displayMatrix();
        }
    }


}
