package mu.utility.service.impl;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import mu.utility.token.Constants;
import mu.utility.service.IP2LocationService;
import mu.utility.service.MongoService;
import mu.utility.service.impl.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class IP2LocationServiceimpl implements IP2LocationService {
	private static final Logger LOGGER = Logger.getLogger(IP2LocationServiceimpl.class.getName());

	@Override
	public String getGeolocation(String ipAddress) {

		// Disable SSL certificate validation
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}

		String apiUrl = String.format(Constants.API_URL, Constants.API_KEY, ipAddress);
		StringBuilder jsonResponse = new StringBuilder();

		try {
			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(Constants.GET);
			connection.setRequestProperty("User-Agent", Constants.USER_AGENT);

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
					while (scanner.hasNextLine()) {
						jsonResponse.append(scanner.nextLine());
					}
				}
			} else {
				jsonResponse.append("Error: ").append(responseCode);
			}
		} catch (IOException e) {
			jsonResponse.append("Error: ").append(e.getMessage());
			System.out.println(e.getMessage());
		}
		return jsonResponse.toString();
	}

}
