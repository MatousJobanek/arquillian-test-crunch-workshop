package org.jboss.arquillian.kitchensink.ui.ftests;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
public class KitchensinkTest {

    @Test
    public void simpleTest(){
        System.out.println("Hi there, I'm running!!!");
    }
}
