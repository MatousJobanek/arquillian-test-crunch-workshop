package org.jboss.as.quickstarts.kitchensink.test;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;
import org.jboss.as.quickstarts.kitchensink.util.Resources;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
public class MemberRepositoryTest {

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(Member.class, MemberRegistration.class, Resources.class, MemberRepository.class)
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource("test-ds.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private MemberRegistration memberRegistration;

    @Inject
    private MemberRepository memberRepository;

    @Test
    public void registerMemberTest() throws Exception {
        Assert.assertNotNull(memberRegistration);
        Assert.assertNotNull(memberRepository);


        Member member = new Member();
        member.setName("John Doe");
        member.setEmail("john@doe.com");
        member.setPhoneNumber("1234567890");

        memberRegistration.register(member);

        Member found = memberRepository.findByEmail("john@doe.com");
        Assert.assertNotNull(found);
        Assert.assertEquals(member.getName(), found.getName());
    }
}
