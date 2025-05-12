package reporting;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class ExtentReportListener implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::onTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        ExtentManager.startScenario(event.getTestCase().getName());
    }

    private void onTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getKeyword() + step.getStep().getText();
            ExtentManager.startStep(stepText);
        }
    }

    private void onTestStepFinished(TestStepFinished event) {
        ExtentTest step = ExtentManager.getStepNode();
        if (step != null) {
            if (event.getResult().getStatus().is(Status.FAILED)) {
                step.fail(event.getResult().getError());
            } else {
              //  step.pass("Step Passed");
            }
        }
    }

    private void onTestCaseFinished(TestCaseFinished event) {
        ExtentManager.endScenario();
    }
}