package mu.utility.service.impl;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import javax.net.ssl.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
 
public class MongoDBMetrics {
 
    // Replace these with your actual values
    private static final String PUBLIC_KEY = "qkwodutl";
    private static final String PRIVATE_KEY = "9e1cc855-0d5f-44e4-9d46-c8c19fe3f856";
    private static final String GROUP_ID = "5dce74a779358e1df062c352";
    private static final String HOSTNAME_PORT = "mongodb+srv://HercDevAdmin:KrRqR7yhSQdskRIG@herc-dev-x5zfe.mongodb.net/test?retryWrites=true&w=majority&serverSelectionTimeoutMS=37000";
    private static final String DATABASE_NAME = "engagement";
 
    public static void main(String[] args) {
        try {
            fetchDatabaseMetrics();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    public static void fetchDatabaseMetrics() throws IOException, InterruptedException {
    	 disableSSLCertificateChecking();
    	
        String url = String.format(
                "https://cloud.mongodb.com/api/atlas/v1.0/groups/%s/processes/%s/databases/%s/measurements?granularity=PT1M&period=PT1H",
                GROUP_ID, HOSTNAME_PORT, DATABASE_NAME);
 
        HttpClient client = HttpClient.newBuilder().build();
 
        String auth = PUBLIC_KEY + ":" + PRIVATE_KEY;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
 
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/json")
                .GET()
                .build();
 
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
 
        if (response.statusCode() == 200) {
            System.out.println("Metrics data: " + response.body());
        } else {
            System.err.println("Failed to fetch metrics: " + response.statusCode() + " - " + response.body());
        }
    }
    public static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}