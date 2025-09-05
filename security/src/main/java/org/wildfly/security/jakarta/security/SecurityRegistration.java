/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.security.jakarta.security;

/**
 * Utility to enable registration for Jakarta Security.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class SecurityRegistration {

    /**
     * Perform any static registration for Jakarta Security.
     *
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public static boolean register() {
        return true;
    }

}
