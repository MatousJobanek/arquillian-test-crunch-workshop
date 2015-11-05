package org.jboss.arquillian.kitchensink.ui.ftests;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
public class KitchensinkTest {

    @Deployment
    public static WebArchive deploy() {
        File[] droneDependencies = Maven
            .configureResolver()
            .loadPomFromFile("pom.xml")
            .resolve("org.jboss.arquillian.extension:arquillian-drone-webdriver-depchain:pom:2.0.0.Alpha5")
            .withTransitivity()
            .asFile();

        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("../kitchensink/target/jboss-kitchensink.war"))
            .addAsLibraries(droneDependencies);
    }

    @ArquillianResource
    private URL url;

    @Drone
    private WebDriver browser;

    @FindBy(id = "reg:name")
    private WebElement nameField;

    @FindBy(id = "reg:email")
    private WebElement emailField;

    @FindBy(id = "reg:phoneNumber")
    private WebElement phoneNumberField;

    @FindBy(id = "reg:register")
    private WebElement registerButton;

    @FindByJQuery("table.simpletablestyle > tbody > tr")
    private List<WebElement> tableRows;

    @Inject
    private MemberRepository memberRepository;

    @Test
    @InSequence(1)
    @RunAsClient
    public void registerMemberTest() {
        browser.get(url.toString());

        Graphene.waitModel().until().element(nameField).is().visible();

        nameField.sendKeys("My cool name");
        emailField.sendKeys("my@cool.email");
        phoneNumberField.sendKeys("1234567890");
        Graphene.guardHttp(registerButton).click();

        Assert.assertEquals(2, tableRows.size());
        WebElement addedRow = tableRows.get(1);

        List<WebElement> columns = addedRow.findElements(By.tagName("td"));
        Assert.assertEquals("1", columns.get(0).getText());
        Assert.assertEquals("My cool name", columns.get(1).getText());
        Assert.assertEquals("my@cool.email", columns.get(2).getText());
        Assert.assertEquals("1234567890", columns.get(3).getText());
    }

    @Test
    @InSequence(2)
    public void verifyRegistrationTest() {
        Assert.assertNotNull(memberRepository);

        Member found = memberRepository.findByEmail("my@cool.email");
        Assert.assertNotNull(found);
        Assert.assertEquals("My cool name", found.getName());
        Assert.assertEquals("1234567890", found.getPhoneNumber());
    }

}
