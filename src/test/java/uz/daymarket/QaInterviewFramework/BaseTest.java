package uz.daymarket.QaInterviewFramework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.MDC;

import java.lang.reflect.Method;

public abstract class BaseTest {
    @BeforeEach
    void setUpLogs(TestInfo testInfo) {
        Class<?> clazz = getClass();

        String pkg = clazz.getPackageName();
        if (pkg.isEmpty()) pkg = "default";
        int lastDot = pkg.lastIndexOf('.');
        String shortpackage = (lastDot >= 0) ? pkg.substring(lastDot + 1) : pkg;

        String methodName = testInfo.getTestMethod()
                .map(Method::getName)
                .orElse("unknown");

        String displayName = testInfo.getDisplayName();
        String suffix = methodName;

        if (displayName != null) {
            String dn = displayName.trim();
            // Используем displayName, если он не пустой и не совпадает с именем метода
            if (!dn.isEmpty() && !dn.equals(methodName)) {
                suffix = dn;
            }
        }

        String testName = shortpackage + "." + clazz.getSimpleName() + "." + suffix;
        MDC.put("testName", testName);

//        String[] packageParts = this.getClass().getpackageName().split("\\.");
//        String shortpackage = packageParts.length >= 1
//                ? packageParts[packageParts.length-1]
//                : this.getClass().getpackageName();
//        String testName = shortpackage + "." + this.getClass().getSimpleName() + "."
//                + testInfo.getTestMethod().map(Method::getName).orElse("unknown");
//        MDC.put("testName", testName);
    }

    @AfterEach
    void clearMdc() {
        MDC.remove("testName");
    }
}
