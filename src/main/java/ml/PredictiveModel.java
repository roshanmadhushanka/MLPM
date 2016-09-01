package ml;

import org.wso2.carbon.ml.commons.domain.Feature;
import org.wso2.carbon.ml.commons.domain.MLModel;
import org.wso2.carbon.ml.core.exceptions.MLModelHandlerException;
import org.wso2.carbon.ml.core.impl.Predictor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wso2123 on 8/30/16.
 */
public class PredictiveModel {
    private String source;
    private MLModel mlModel;

    //Begin : Constructor
    public PredictiveModel(String source) throws URISyntaxException, IOException, ClassNotFoundException {
        this.source = source;
        URL resource = PredictiveModel.class.getClassLoader().getResource(source);
        String pathToDownloadedModel = new File(resource.toURI()).getAbsolutePath();
        this.mlModel  = deserializeMLModel(pathToDownloadedModel);
    }
    //End : Constructor

    //Begin : Getters
    public MLModel getMlModel(){
        return this.mlModel;
    }

    public ArrayList<String> getFeatures(){
        ArrayList<String> features = new ArrayList<String>();
        for(int i=0; i<mlModel.getFeatures().size(); i++){
            Feature feature = mlModel.getFeatures().get(i);
            features.add(feature.getName());
        }
        return features;
    }

    public Object predict(String[] featureValueArray) throws MLModelHandlerException {
        ArrayList<String[]> featureValuesList = new ArrayList<String[]>();
        featureValuesList.add(featureValueArray);
        Predictor predictor = new Predictor(0, mlModel, featureValuesList);
        List<?> predictions = predictor.predict();

        return predictions.get(0);
    }
    //End : Getters

    private MLModel deserializeMLModel(String pathToDownloadedModel) throws IOException, ClassNotFoundException, URISyntaxException {
        FileInputStream fileInputStream = new FileInputStream(pathToDownloadedModel);
        ObjectInputStream in = new ObjectInputStream(fileInputStream);
        MLModel mlModel = (MLModel) in.readObject();
        return mlModel;
    }


}
