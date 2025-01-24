package mu.utility.token;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import javax.net.ssl.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class MongoDBstats {

	public static void main(String[] args)
			throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		final String PUBLIC_KEY = "piqxhcox";
		// final String PRIVATE_KEY = "9e1cc855-0d5f-44e4-9d46-c8c19fe3f856";
		final String PRIVATE_KEY = "9e771448-04c4-4970-8f13-aa2a6727150c";
		final String GROUP_ID = "5dd553405538558b26492fb2";
		final String HOSTNAME_PORT = "herc-dev-shard-00-00.x5zfe.mongodb.net:27017";
		final String DATABASE_NAME = "engagement";
		String url = "https://cloud.mongodb.com/api/atlas/v1.0/groups";

//HttpClient client = HttpClient.newBuilder().build();

		String auth = PUBLIC_KEY + ":" + PRIVATE_KEY;
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

// Step 1: Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

// Step 2: Install the all-trusting trust manage
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

//Step 3: Create an all-trusting host name verifier
		HostnameVerifier allHostsValid = (hostname, session) -> true;

// Step 4: Configure HttpClient to use the custom SSLContext and HostnameVerifier
		HttpClient client = HttpClient.newBuilder().sslContext(sc).sslParameters(sc.getDefaultSSLParameters())
				.sslContext(sc).sslParameters(sc.getDefaultSSLParameters()).build();
// Step 5: Create and send the HTTP request

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
				.header("Authorization",encodedAuth).header("Content-Type", "application/json").header("Accept", "application/json") .GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			System.out.println("Group ID data: " + response.body());
		} else {
			System.err.println("Failed to fetch Group ID: " + response.statusCode() + " - " + response.body());
		}

		String url1 = String.format(
				"https://cloud.mongodb.com/api/atlas/v1.0/groups/%s/processes/%s/databases/%s/measurements?granularity=PT1M&period=PT1H",
				GROUP_ID, HOSTNAME_PORT, DATABASE_NAME);
		HttpClient client1 = HttpClient.newBuilder().build();
		String auth1 = PUBLIC_KEY + ":" + PRIVATE_KEY;
		String encodedAuth1 = Base64.getEncoder().encodeToString(auth1.getBytes());
		HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(url1))
				.header("Authorization", "Basic " + encodedAuth1).header("Content-Type", "application/json").GET()
				.build();
		HttpResponse<String> response1;
		try {
			response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				System.out.println("Metrics data: " + response1.body());
			} else {
				System.err.println("Failed to fetch metrics: " + response1.statusCode() + " - " + response.body());
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}