package org.jboss.arquillian.volkswagen.extension;

import org.jboss.arquillian.core.spi.LoadableExtension;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
public class VolkswagenExtension implements LoadableExtension {

    public void register(ExtensionBuilder extensionBuilder) {
        extensionBuilder.observer(VolkswagenObserver.class);
    }
}
