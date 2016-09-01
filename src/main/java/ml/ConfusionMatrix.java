package ml;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/31/16.
 */
public class ConfusionMatrix {
    private int tn; //True Negative
    private int tp; //True Positive
    private int fn; //False Negative
    private int fp; //False Positive

    //Begin : Constructor
    public ConfusionMatrix(){
        this.tn = 0;
        this.tp = 0;
        this.fn = 0;
        this.fp = 0;
    }

    public ConfusionMatrix(int tn, int tp, int fn, int fp) {
        this.tn = tn;
        this.tp = tp;
        this.fn = fn;
        this.fp = fp;
    }
    //End : Constructor

    //Begin : Getters
    public double getSensitivity(){
        return tp * 1.0 / (tp + fn);
    }

    public double getTruePositiveRate(){
        return getSensitivity();
    }

    public double getFalsePositiveRate(){
        return fp * 1.0 / (fp + tn);
    }

    public double getSpecificity(){
        return tn * 1.0 / (tn + fp);
    }

    public double getPrecision(){
        return tp * 1.0 / (tp + fp);
    }

    public double getMCC(){
        return 1.0 * ((tn*tp) - (fn*fp)) / Math.sqrt(1.0*(tp+fp) * (fp+tn) * (tn+fn) * (fn+tp));
    }

    public double getF1Score(){
        return 2.0 * getSensitivity() * getPrecision() / (getSensitivity() + getPrecision());
    }
    //End : Getters

    //Begin : Setters
    public void incrementTN(){
        this.tn += 1;
    }

    public void incrementTP(){
        this.tp += 1;
    }

    public void incrementFN(){
        this.fn += 1;
    }

    public void incrementFP(){
        this.fp += 1;
    }

    public void generate(ArrayList<String> actual, ArrayList<String> predicted){
        for(int i=0; i<actual.size(); i++){
            if(actual.get(i).equals("0") && predicted.get(i).equals("0")){
                incrementTN();
            }else if(actual.get(i).equals("0") && predicted.get(i).equals("1")){
                incrementFN();
            }else if(actual.get(i).equals("1") && predicted.get(i).equals("1")){
                incrementTP();
            }else if(actual.get(i).equals("1") && predicted.get(i).equals("0")){
                incrementFP();
            }
        }
    }
    //End : Setters

    public void displayMatrix(){
        System.out.println("--------------------------");
        System.out.println("TP : " + tp);
        System.out.println("TN : " + tn);
        System.out.println("FP : " + fp);
        System.out.println("FN : " + fn);
        System.out.println("--------------------------");
    }
    public void displaySummary() {
        System.out.println("Sensitivity\t" + getSensitivity());
        System.out.println("Specificity\t" + getSpecificity());
        System.out.println("Precision\t" + getPrecision());
        System.out.println("MCC\t\t\t" + getMCC());
        System.out.println("F1 Score\t" + getF1Score());
    }
}
