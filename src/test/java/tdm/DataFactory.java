package tdm;

import reporting.Log;
import steps.DataManagement_steps;

import java.util.HashMap;
import java.util.Map;


public class DataFactory {


    // The customer Name is obtained from the feature line and appended to form the data Ref.
    //This data Ref is used to get the corresponding data from the TestDataPayload to build the json
    // The dataTemplate is Used for templating
    public void buildCustomerPayload(String customer){
        DataManagement_steps data = new DataManagement_steps();
        String dataRefCustomer = customer+"CustomerPayload";
        Log.info("Data Reference Created  ==>"+dataRefCustomer);
        String customerPayload = data.buildCustomerAPIPayload("dataTemplate",dataRefCustomer,"dataPayload",null);
        Log.info("Customer Payload ==>"+customerPayload);
    }


    public void buildCustomerPayloadWithUpdates(String customer){
        DataManagement_steps data = new DataManagement_steps();
        String dataRefCustomer = customer+"CustomerPayload";
        Log.info("Data Reference Created  ==>"+dataRefCustomer);
        // Mention the data fields you want to update
        Map<String,String> updateValues = new HashMap<>();
        updateValues.put("Vintage","NA Map updated");
        updateValues.put("cust_score","NA Map Updated");
        String customerPayload = data.buildCustomerAPIPayloadWithUpdate("dataTemplate",dataRefCustomer,"dataPayload",updateValues);
        Log.info("Customer Payload ==>"+customerPayload);
    }
}
