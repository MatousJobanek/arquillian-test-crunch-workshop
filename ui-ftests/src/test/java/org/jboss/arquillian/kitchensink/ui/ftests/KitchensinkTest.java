package org.jboss.arquillian.kitchensink.ui.ftests;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
public class KitchensinkTest {

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("../kitchensink/target/jboss-kitchensink.war"));
    }

    @Test
    @RunAsClient
    public void clientTest() {
        System.out.println("Hi there, I'm running on client!!!");
    }

    @Test
    public void serverTest() {
        System.err.println("Hi there, I'm running in container!!!");
    }
}
