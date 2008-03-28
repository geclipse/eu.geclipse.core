/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Jie Tao - a class for proxy setting
 *****************************************************************************/

package eu.geclipse.core.test.internal;


import org.eclipse.core.net.proxy.IProxyData;


/**This class builds a structure for proxy service to get the settings
 * @author tao-j
 *
 */
public class ProxyData implements IProxyData {

    private String type;
    private String host;
    private int port;
    private String user;
    private String password;
    private boolean requiresAuthentication;
/**
 * @param type
 * @param host
 * @param port
 * @param requiresAuthentication
 */

    public ProxyData(String type, String host, int port, boolean requiresAuthentication) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.requiresAuthentication = requiresAuthentication;
    }

    /**
     * @param type
     */
    public ProxyData(String type) {
        this.type = type;
    }

    public String getHost() {
        return this.host;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPort() {
        return this.port;
    }

    public String getType() {
        return this.type;
    }

    public String getUserId() {
        return this.user;
    }

    public void setHost(String host) {
        if (host.length() == 0)
            this.host = null;
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserid(String userid) {
        this.user = userid;
        this.requiresAuthentication = userid != null;
    }

    public boolean isRequiresAuthentication() {
        return this.requiresAuthentication;
    }

    public void disable() {
        this.host = null;
        this.port = -1;
        this.user = null;
        this.password = null;
        this.requiresAuthentication = false;
    }

}

