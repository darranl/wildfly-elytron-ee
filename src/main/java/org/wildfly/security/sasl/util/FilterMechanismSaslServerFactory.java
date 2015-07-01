/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.security.sasl.util;

import static org.wildfly.security._private.ElytronMessages.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;

/**
 * A {@link SaslServerFactory} which filters available mechanisms (either inclusively or exclusively) from a delegate
 * {@code SaslServerFactory}.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class FilterMechanismSaslServerFactory extends AbstractDelegatingSaslServerFactory {
    private final boolean include;
    private final Set<String> mechanisms;

    /**
     * Construct a new instance.
     *
     * @param delegate the factory to delegate to
     * @param include {@code true} to only include the given mechanisms, {@code false} to exclude them
     * @param mechanisms the mechanisms to include or exclude
     */
    public FilterMechanismSaslServerFactory(final SaslServerFactory delegate, boolean include, String... mechanisms) {
        super(delegate);
        if (mechanisms == null) {
            throw log.nullParameter("mechanisms");
        }
        this.include = include;
        final HashSet<String> set = new HashSet<String>(mechanisms.length);
        Collections.addAll(set, mechanisms);
        this.mechanisms = set;
    }

    /**
     * Construct a new instance.
     *
     * @param delegate the factory to delegate to
     * @param include {@code true} to only include the given mechanisms, {@code false} to exclude them
     * @param mechanisms the mechanisms to include or exclude
     */
    public FilterMechanismSaslServerFactory(final SaslServerFactory delegate, boolean include, Collection<String> mechanisms) {
        super(delegate);
        if (mechanisms == null) {
            throw log.nullParameter("mechanisms");
        }
        this.include = include;
        this.mechanisms = new HashSet<String>(mechanisms);
    }

    public SaslServer createSaslServer(final String mechanism, final String protocol, final String serverName, final Map<String, ?> props, final CallbackHandler cbh) throws SaslException {
        return this.mechanisms.contains(mechanism) != include ? null : delegate.createSaslServer(mechanism, protocol, serverName, props, cbh);
    }

    public String[] getMechanismNames(final Map<String, ?> props) {
        final String[] names = delegate.getMechanismNames(props);
        final ArrayList<String> list = new ArrayList<>(names.length);
        for (String name : names) {
            if (mechanisms.contains(name) == include) {
                list.add(name);
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
