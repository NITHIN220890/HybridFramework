package steps;

import reporting.Log;
import utilities.HelperClass;

import java.util.Map;

import static api.ApiUtils.buildPayloadUsingJSON;
import static api.ApiUtils.convertJSONFileToMap;

public class DataManagement_steps {


    public String buildCustomerAPIPayload(String templateSchema,String dataRef,String testDataJson, Map<String,String> updateAttMap){
        Log.info("==> Template to which the data would be Appended"+templateSchema);
        Log.info("==> Data Ref which is the set of data that gets picked from the complete payload"+dataRef);
        Log.info("==> Json File that contains the complete testdata including the dataRef"+testDataJson);
        Map envdata = convertJSONFileToMap("src/test/resources/payloads/dataEnvSpecificVariables.json");
        Map<String,String> envSpecificAttr = (Map) envdata.get(HelperClass.getEnv());
        Log.info("envData==>"+envSpecificAttr);
        Map staticData =convertJSONFileToMap("src/test/resources/payloads/"+testDataJson+".json");
        Map<String,String> staticAttr = (Map) staticData.get(dataRef);
        Log.info("static Attribute===>"+staticData);
        envSpecificAttr.putAll(staticAttr);
        Map<String,String> payload =envSpecificAttr;
        Log.info("Complete payload without UpdateMap Attribute==>  "+payload);
        String jsonPayload =buildPayloadUsingJSON("src/test/resources/payloads/"+templateSchema+".json",payload);
        return jsonPayload;
    }


    public String buildCustomerAPIPayloadWithUpdate(String templateSchema,String dataRef,String testDataJson, Map<String,String> updateAttMap){
        Log.info("==> Template to which the data would be Appended"+templateSchema);
        Log.info("==> Data Ref which is the set of data that gets picked from the complete payload"+dataRef);
        Log.info("==> Json File that contains the complete testdata including the dataRef"+testDataJson);
        Map envdata = convertJSONFileToMap("src/test/resources/payloads/dataEnvSpecificVariables.json");
        Map<String,String> envSpecificAttr = (Map) envdata.get(HelperClass.getEnv());
        Log.info("envData==>"+envSpecificAttr);
        Map staticData =convertJSONFileToMap("src/test/resources/payloads/"+testDataJson+".json");
        Map<String,String> staticAttr = (Map) staticData.get(dataRef);
        Log.info("static Attribute===>"+staticData);
        envSpecificAttr.putAll(staticAttr);
        Map<String,String> payload =envSpecificAttr;
        Log.info("Complete payload without UpdateMap Attribute==>  "+payload);
        if(updateAttMap !=null){
            payload.putAll(updateAttMap);
        }
        String jsonPayload =buildPayloadUsingJSON("src/test/resources/payloads/"+templateSchema+".json",payload);
        return jsonPayload;
    }


}

