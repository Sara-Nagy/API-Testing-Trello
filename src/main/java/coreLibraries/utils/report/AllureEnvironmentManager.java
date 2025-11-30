package coreLibraries.utils.report;

import com.google.common.collect.ImmutableMap;
import coreLibraries.utils.logs.LogsManager;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static coreLibraries.utils.dataReader.PropertyReader.getProperty;

public class AllureEnvironmentManager {
    public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getProperty("os.name"))
                        .put("Java version:", getProperty("java.runtime.version"))
                       // .put("Browser", getProperty("browserType"))
                       // .put("Execution Type", getProperty("executionType"))
                      //  .put("URL", getProperty("baseUrlWeb"))
                        .build(), String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator
        );
        LogsManager.info("Allure environment variables set.");
        AllureBinaryManager.downloadAndExtract();
    }
}

