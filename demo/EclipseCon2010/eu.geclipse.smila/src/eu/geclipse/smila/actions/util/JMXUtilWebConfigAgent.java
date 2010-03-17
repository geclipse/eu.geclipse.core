/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * 
 * Contributors:
 *    Harald Kornmayer - NEC Laboratories Europe
 *    
 *****************************************************************************/
package eu.geclipse.smila.actions.util;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXUtilWebConfigAgent {

	public void invoke(String endpoint, String objectName, String argument) throws JMXUtilException {

		JMXServiceURL url;
		try {
			url = new JMXServiceURL(endpoint);
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			// Construct the ObjectName for the Hello MBean
			//
			ObjectName mbeanName = new ObjectName(
					objectName);

			// Create a dedicated proxy for the MBean instead of
			// going directly through the MBean server connection
			//

			WebConfigAgent mbeanProxy = JMX.newMBeanProxy(mbsc,
					mbeanName, WebConfigAgent.class, true);

			// Invoke mbeanName

			mbeanProxy.setWebCrawlerSeed( argument );
			jmxc.close() ; 
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new JMXUtilException() ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JMXUtilException() ; 
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JMXUtilException() ; 
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JMXUtilException() ; 
		}
	}
}
