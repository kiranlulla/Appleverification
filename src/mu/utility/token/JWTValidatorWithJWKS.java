package mu.utility.token;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.InputStreamReader;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class JWTValidatorWithJWKS {

    // Validate JWT using JWKS URL
    public static boolean validateJWT(String token, String jwksUrl) {
        try {
            // Parse the JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Extract the Key ID (kid) from the JWT header
            String kid = signedJWT.getHeader().getKeyID();

            // Fetch the JWKS from the URL
            JWKSet jwkSet = JWKSet.load(new URL(jwksUrl));

            // Find the JWK that matches the kid
            JWK jwk = jwkSet.getKeyByKeyId(kid);
            if (jwk == null) {
                System.out.println("No matching JWK found for kid: " + kid);
                return false;
            }

            // Convert the JWK to an RSA Public Key
            RSAPublicKey publicKey = jwk.toRSAKey().toRSAPublicKey();

            // Verify the JWT signature
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            if (!signedJWT.verify(verifier)) {
                System.out.println("Invalid signature!");
                return false;
            }

            // Validate claims (e.g., expiration, audience, issuer)
            if (signedJWT.getJWTClaimsSet().getExpirationTime().before(new java.util.Date())) {
                System.out.println("Token has expired!");
                return false;
            }

            // (Optional) Validate additional claims
            String audience = signedJWT.getJWTClaimsSet().getAudience().get(0);
            String issuer = signedJWT.getJWTClaimsSet().getIssuer();

            if (!"expected-audience".equals(audience)) {
                System.out.println("Invalid audience!");
                return false;
            }

            if (!"expected-issuer".equals(issuer)) {
                System.out.println("Invalid issuer!");
                return false;
            }

            System.out.println("Token is valid!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // Example JWT (replace with actual token)
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImtleUlEIn0.eyJpc3MiOiJleHBlY3RlZC1pc3N1ZXIiLCJhdWQiOiJleHBlY3RlZC1hdWRpZW5jZSIsImV4cCI6MTcyMDA1NjAwMH0.signature";

        // JWKS URL (replace with actual URL from your identity provider)
        String jwksUrl = "https://login.manutd.com/.well-known/openid-configuration/jwks";

        // Validate the JWT
        boolean isValid = validateJWT(token, jwksUrl);
        System.out.println("Token validation result: " + isValid);
    }
}
