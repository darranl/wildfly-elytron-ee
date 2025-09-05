/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.security.jakarta.auth;

/**
 * Utility to enable registration for Jakarta Authentication.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class AuthenticationRegistration {

    /**
     * Perform any static registration for Jakarta Authentication.
     *
     * @return {@code true} if successful, {@code false} otherwise.
     */
    public static boolean register() {
        return true;
    }

}
