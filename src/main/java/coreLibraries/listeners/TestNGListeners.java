package coreLibraries.listeners;


import coreLibraries.utils.FileUtils;
import coreLibraries.utils.dataReader.PropertyReader;
import coreLibraries.utils.logs.LogsManager;
import coreLibraries.utils.report.AllureAttachmentManager;
import coreLibraries.utils.report.AllureConstants;
import coreLibraries.utils.report.AllureEnvironmentManager;
import coreLibraries.utils.report.AllureReportGenerator;

import org.testng.*;

import java.io.File;

public class TestNGListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {
    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("General Framework Suite");
    }

    public void onExecutionStart() {
        LogsManager.info("Test Execution started");
        cleanTestOutputDirectories();
        LogsManager.info("Directories cleaned");
                PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished");
    }


    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {

            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        AllureAttachmentManager.attachLogs();
    }


    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " failed");

    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " skipped");
    }


    // cleaning and creating dirs (logs, screenshots, recordings,allure-results)
    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.forceDelete(new File(LogsManager.LOGS_PATH + "logs.log"));
    }


}