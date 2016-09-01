package data;

import ml.PredictiveModel;
import org.wso2.carbon.ml.commons.domain.Feature;
import org.wso2.carbon.ml.commons.domain.MLModel;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/30/16.
 */
public class Filter {
    public static ArrayList<String[]> remainingLifeFilter(DataSet dataSet, String categoryFeature){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);
        ArrayList<String[]> dataRows = new ArrayList<String[]>();

        for(int i=1; i<breakingPoints.size(); i++){
           dataRows.add(dataSet.getDataRow(breakingPoints.get(i)-1));
        }
        return dataRows;
    }

    public static void removeUnwantedFeatures(DataSet dataSet, PredictiveModel mlModel){
        ArrayList<String> modelFeatures = new ArrayList<String>();
        for(Feature feature: mlModel.getMlModel().getFeatures()){
            modelFeatures.add(feature.getName());
        }
        //Clone features to avoid concurrent modification errors
        ArrayList<String> datasetFeatures = (ArrayList<String>) dataSet.getFeatures().clone();

        for(String feature: datasetFeatures){
            if(!modelFeatures.contains(feature)){
                dataSet.removeDataColumn(feature);
            }
        }
    }

}
