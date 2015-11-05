package org.jboss.arquillian.volkswagen.extension;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
public class VolkswagenObserver {

    public void setAsPassed(@Observes TestResult testResult){
        System.out.println("Volkswagen extension activated!");
        testResult.setThrowable(null);
    }
}
