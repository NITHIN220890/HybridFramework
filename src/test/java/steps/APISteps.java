package steps;

import io.cucumber.java.en.Given;
import tdm.DataFactory;

public class APISteps {
    @Given("Validate that API payload is generated for the customer {string}")
    public void validateThatAPIPayloadIsGeneratedForTheCustomer(String customer) {
        DataFactory df = new DataFactory();
        df.buildCustomerPayloadWithUpdates(customer);
    }
}
