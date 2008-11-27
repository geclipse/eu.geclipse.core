//
// typica - A client library for Amazon Web Services
// Copyright (C) 2007 Xerox Corporation
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.xerox.amazonws.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.typica.jaxb.Response;
import com.xerox.amazonws.typica.sqs2.jaxb.Error;
import com.xerox.amazonws.typica.sqs2.jaxb.ErrorResponse;

/**
 * This class provides an interface with the Amazon SQS service. It provides high level
 * methods for listing and creating message queues.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class AWSQueryConnection extends AWSConnection {
	private static final Log log = LogFactory.getLog(AWSQueryConnection.class);

	// this is the number of automatic retries
	private int maxRetries = 5;
	private String userAgent = "typica/";
	private int sigVersion = 1;
	private HttpClient hc = null;
	private int maxConnections = 100;
	private String proxyHost = null;
	private int proxyPort;
	private String proxyUser;
	private String proxyPassword;
	private String proxyDomain;	// for ntlm authentication

    /**
	 * Initializes the queue service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from SQS.
     * @param server Which host to connect to.  Usually, this will be s3.amazonaws.com
     * @param port Which port to use.
     */
    public AWSQueryConnection(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server, int port) {
		super(awsAccessId, awsSecretKey, isSecure, server, port);
		String version = "?";
		try {
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("version.properties"));
			version = props.getProperty("version");
		} catch (Exception ex) { }
		userAgent = userAgent + version + " ("+ System.getProperty("os.arch") + "; " + System.getProperty("os.name") + ")";
    }

	/**
	 * This method returns the number of connections that can be open at once.
	 *
	 * @return the number of connections
	 */
	public int getMaxConnections() {
		return maxConnections;
	}

	/**
	 * This method sets the number of connections that can be open at once.
	 *
	 * @param connections the number of connections
	 */
	public void setMaxConnections(int connections) {
		maxConnections = connections;
		hc = null;
	}

	/**
	 * This method returns the number of times to retry when a recoverable error occurs.
	 *
	 * @return the number of times to retry on recoverable error
	 */
	public int getMaxRetries() {
		return maxRetries;
	}

	/**
	 * This method sets the number of times to retry when a recoverable error occurs.
	 *
	 * @param retries the number of times to retry on recoverable error
	 */
	public void setMaxRetries(int retries) {
		maxRetries = retries;
	}

	/**
	 * This method returns the signature version
	 *
	 * @return the version
	 */
	public int getSignatureVersion() {
		return sigVersion;
	}

	/**
	 * This method sets the proxy host and port
	 *
	 * @param host the proxy host
	 * @param port the proxy port
	 */
	public void setProxyValues(String host, int port) {
		this.proxyHost = host;
		this.proxyPort = port;
		hc = null;
	}

	/**
	 * This method sets the proxy host, port, user and password (for authenticating proxies)
	 *
	 * @param host the proxy host
	 * @param port the proxy port
	 * @param user the proxy user
	 * @param password the proxy password
	 */
	public void setProxyValues(String host, int port, String user, String password) {
		this.proxyHost = host;
		this.proxyPort = port;
		this.proxyUser = user;
		this.proxyPassword = password;
		hc = null;
	}

	/**
	 * This method sets the proxy host, port, user, password and domain (for NTLM authentication)
	 *
	 * @param host the proxy host
	 * @param port the proxy port
	 * @param user the proxy user
	 * @param password the proxy password
	 * @param domain the proxy domain
	 */
	public void setProxyValues(String host, int port, String user, String password, String domain) {
		this.proxyHost = host;
		this.proxyPort = port;
		this.proxyUser = user;
		this.proxyPassword = password;
		this.proxyDomain = domain;
		hc = null;
	}

	/**
	 * This method indicates the system properties should be used for proxy settings. These
	 * properties are http.proxyHost, http.proxyPort, http.proxyUser and http.proxyPassword
	 */
	public void useSystemProxy() {
		this.proxyHost = System.getProperty("http.proxyHost");
		if (this.proxyHost != null && this.proxyHost.trim().equals("")) {
			proxyHost = null;
		}
		this.proxyPort = getPort();
		try {
			this.proxyPort = Integer.parseInt(System.getProperty("http.proxyPort"));
		} catch (NumberFormatException ex) {
			/* use default */
		}
		this.proxyUser = System.getProperty("http.proxyUser");
		this.proxyPassword = System.getProperty("http.proxyPassword");
		this.proxyDomain = System.getProperty("http.proxyDomain");
		hc = null;
	}

	/**
	 * This method returns the map of headers for this connection
	 *
	 * @return map of headers (modifiable) 
	 */
	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	/**
	 * This method sets the signature version used to sign requests (0 or 1).
	 * NOTE: This value defaults to 1, so passing 0 is the most likely use case.
	 *
	 * @param version signature version
	 */
	public void setSignatureVersion(int version) {
		if (version != 0 && version != 1) {
			throw new IllegalArgumentException("Only signature versions 0 and 1 supported");
		}
		sigVersion = version;
	}

	protected HttpClient getHttpClient() {
		if (hc == null) {
			configureHttpClient();
		}
		return hc;
	}

	public void setHttpClient(HttpClient hc) {
		this.hc = hc;
	}

    /**
     * Make a http request and process the response. This method also performs automatic retries.
	 *
     * @param method The HTTP method to use (GET, POST, DELETE, etc)
     * @param action the name of the action for this query request
     * @param params map of request params
     * @param respType the class that represents the desired/expected return type
     */
	protected <T> T makeRequest(HttpMethodBase method, String action, Map<String, String> params, Class<T> respType)
		throws HttpException, IOException, JAXBException {

		// add auth params, and protocol specific headers
		Map<String, String> qParams = new HashMap<String, String>(params);
		qParams.put("Action", action);
		qParams.put("AWSAccessKeyId", getAwsAccessKeyId());
		qParams.put("SignatureVersion", ""+sigVersion);
		qParams.put("Timestamp", httpDate());
        if (headers != null) {
            for (Iterator<String> i = headers.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                for (Iterator<String> j = headers.get(key).iterator(); j.hasNext(); ) {
					qParams.put(key, j.next());
                }
            }
        }
		// sort params by key
		ArrayList<String> keys = new ArrayList<String>(qParams.keySet());
		Collator stringCollator = Collator.getInstance();
		stringCollator.setStrength(Collator.PRIMARY);
		Collections.sort(keys, stringCollator);

		// build param string
		StringBuilder resource = new StringBuilder();
		if (sigVersion == 0) {	// ensure Action, Timestamp come first!
			resource.append(qParams.get("Action"));
			resource.append(qParams.get("Timestamp"));
		}
		else {
			for (String key : keys) {
				resource.append(key);
				resource.append(qParams.get(key));
			}
		}

		// calculate signature
        String encoded = urlencode(encode(getSecretAccessKey(), resource.toString(), false));

		// build param string, encoding values and adding request signature
		resource = new StringBuilder();
		for (String key : keys) {
			resource.append("&");
			resource.append(key);
			resource.append("=");
			resource.append(urlencode(qParams.get(key)));
		}
		resource.setCharAt(0, '?');	// set first param delimeter
		resource.append("&Signature=");
		resource.append(encoded);

		// finally, build request object
        URL url = makeURL(resource.toString());
		method.setURI(new URI(url.toString(), true));
		method.setRequestHeader(new Header("User-Agent", userAgent));
		if (sigVersion == 0) {
			method.setRequestHeader(new Header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
		}
		Object response = null;
		boolean done = false;
		int retries = 0;
		boolean doRetry = false;
		String errorMsg = "";
		do {
			int responseCode = 600;	// default to high value, so we don't think it is valid
			try {
				responseCode = getHttpClient().executeMethod(method);
			} catch (SocketException ex) {
				// these can generally be retried. Treat it like a 500 error
				doRetry = true;
				errorMsg = ex.getMessage();
			}
			// 100's are these are handled by httpclient
			if (responseCode < 300) {
				// 200's : parse normal response into requested object
				if (respType != null) {
					InputStream iStr = method.getResponseBodyAsStream();
					response = JAXBuddy.deserializeXMLStream(respType, iStr);
				}
				done = true;
			}
			else if (responseCode < 400) {
				// 300's : what to do?
				throw new HttpException("redirect error : "+responseCode);
			}
			else if (responseCode < 500) {
				// 400's : parse client error message
				String body = getStringFromStream(method.getResponseBodyAsStream());
				throw new HttpException("Client error : "+getErrorDetails(body));
			}
			else if (responseCode < 600) {
				// 500's : retry...
				doRetry = true;
				String body = getStringFromStream(method.getResponseBodyAsStream());
				errorMsg = getErrorDetails(body);
			}
			if (doRetry) {
				retries++;
				if (retries > maxRetries) {
					throw new HttpException("Number of retries exceeded : "+action+", "+errorMsg);
				}
				doRetry = false;
				try { Thread.sleep((int)Math.pow(2.0, retries)*1000); } catch (InterruptedException ex) {}
			}
		} while (!done);
		return (T)response;
	}

	private void configureHttpClient() {
		MultiThreadedHttpConnectionManager connMgr = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams connParams = connMgr.getParams();
		connParams.setMaxTotalConnections(maxConnections);
		connParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, maxConnections);
		connMgr.setParams(connParams);
		hc = new HttpClient(connMgr);
// NOTE: These didn't seem to help in my initial testing
//			hc.getParams().setParameter("http.tcp.nodelay", true);
//			hc.getParams().setParameter("http.connection.stalecheck", false); 
		if (proxyHost != null) {
			HostConfiguration hostConfig = new HostConfiguration();
			hostConfig.setProxy(proxyHost, proxyPort);
			hc.setHostConfiguration(hostConfig);
			log.info("Proxy Host set to "+proxyHost+":"+proxyPort);
			if (proxyUser != null && !proxyUser.trim().equals("")) {
				if (proxyDomain != null) {
					hc.getState().setProxyCredentials(new AuthScope(proxyHost, proxyPort),
							new NTCredentials(proxyUser, proxyPassword, proxyHost, proxyDomain));
				}
				else {
					hc.getState().setProxyCredentials(new AuthScope(proxyHost, proxyPort),
							new UsernamePasswordCredentials(proxyUser, proxyPassword));
				}
			}
		}
	}

	private String getStringFromStream(InputStream iStr) throws IOException {
		InputStreamReader rdr = new InputStreamReader(iStr, "UTF-8");
		StringWriter wtr = new StringWriter();
		char [] buf = new char[1024];
		int bytes;
		while ((bytes = rdr.read(buf)) > -1) {
			if (bytes > 0) {
				wtr.write(buf, 0, bytes);
			}
		}
		iStr.close();
		return wtr.toString();
	}

	private String getErrorDetails(String errorResponse) throws JAXBException {
		ByteArrayInputStream bais = new ByteArrayInputStream(errorResponse.getBytes());
		if (errorResponse.indexOf("<ErrorResponse") > -1) {
			try {
				ErrorResponse resp = JAXBuddy.deserializeXMLStream(ErrorResponse.class, bais);
				Error err = resp.getErrors().get(0);
				return "("+err.getCode()+") "+err.getMessage();
			} catch (UnmarshalException ex) {
				bais = new ByteArrayInputStream(errorResponse.getBytes());
				com.xerox.amazonws.typica.jaxb.ErrorResponse resp = JAXBuddy.deserializeXMLStream(com.xerox.amazonws.typica.jaxb.ErrorResponse.class, bais);
				com.xerox.amazonws.typica.jaxb.Error err = resp.getErrors().get(0);
				return "("+err.getCode()+") "+err.getMessage();
			}
		}
		else {
			Response resp = JAXBuddy.deserializeXMLStream(Response.class, bais);
			return resp.getErrors().getError().getMessage();
		}
	}

    /**
     * Generate an rfc822 date for use in the Date HTTP header.
     */
    private static String httpDate() {
        final String DateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat format = new SimpleDateFormat( DateFormat, Locale.US );
        format.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
        return format.format( new Date() );
    }
}
