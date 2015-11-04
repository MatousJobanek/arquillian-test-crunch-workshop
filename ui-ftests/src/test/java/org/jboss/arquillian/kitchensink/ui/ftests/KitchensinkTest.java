package org.jboss.arquillian.kitchensink.ui.ftests;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
@RunAsClient
public class KitchensinkTest {

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("../kitchensink/target/jboss-kitchensink.war"));
    }

    @ArquillianResource
    private URL url;

    @Drone
    private WebDriver browser;

    @Test
    public void uiTest() throws InterruptedException {
        browser.get(url.toString());
        Thread.sleep(5000);
        System.out.println("Hi there, I'm running on client!!!");
    }

}
