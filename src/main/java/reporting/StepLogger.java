package reporting;

import com.aventstack.extentreports.ExtentTest;

public class StepLogger {

    public static void log(String message){
        ExtentTest step = ExtentManager.getStepNode();
        if(step!=null){
            step.info(message);
        }
    }

    public static void pass(String message){
        ExtentTest step = ExtentManager.getStepNode();
        if(step!=null){
            step.pass(message);
        }
    }

    public static void fail(String message){
        ExtentTest step = ExtentManager.getStepNode();
        if(step!=null){
            step.fail(message);
        }
    }
}
