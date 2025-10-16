package uz.daymarket.QaInterviewFramework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.MDC;

public abstract class BaseTest {
    @BeforeEach
    void setUpLogs(TestInfo testInfo) {
        String[] packageParts = this.getClass().getPackageName().split("\\.");
        String shortPackage = packageParts.length >= 1
                ? packageParts[packageParts.length-1]
                : this.getClass().getPackageName();
        String testName = shortPackage + "." + this.getClass().getSimpleName() + "."
                + testInfo.getTestMethod().map(m -> m.getName()).orElse("unknown");
        MDC.put("testName", testName);
    }
}
