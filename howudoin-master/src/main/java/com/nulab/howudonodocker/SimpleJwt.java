package com.nulab.howudonodocker;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SimpleJwt {

    private static final String SECRET_KEY = "nulab_labs"; // Weak secret, insecure for real-world use

    // Base64 URL-safe encoding
    private static String base64UrlEncode(String input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    // Base64 URL-safe decoding
    private static String base64UrlDecode(String input) {
        return new String(Base64.getUrlDecoder().decode(input), StandardCharsets.UTF_8);
    }

    // Create the JWT Token
    public static String createToken( String email, String password) {
        try {
            // 1. Create the Header JSON (alg = HS256, typ = JWT)
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String encodedHeader = base64UrlEncode(header);

            // 2. Create the Payload JSON (sub = username, iat = current timestamp)
            long iat = System.currentTimeMillis() / 1000;
            String payload = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"iat\":" + iat + "}";
            String encodedPayload = base64UrlEncode(payload);

            // 3. Create the Signature
            String toSign = encodedHeader + "." + encodedPayload;
            String signature = createSignature(toSign, SECRET_KEY);

            // 4. Return the final JWT (Header.Payload.Signature)
            return encodedHeader + "." + encodedPayload + "." + signature;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create the HMAC-SHA256 signature
    private static String createSignature(String data, String secret) throws NoSuchAlgorithmException {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Return the signature in Base64 URL-safe format
            return base64UrlEncode(new String(hash, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new NoSuchAlgorithmException("HMAC SHA256 algorithm not available");
        }
    }

    // Validate the token
    public static boolean validateToken(String token, String email, String password) {
        return  true; //for debugging
        /*String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false; // Invalid token format
        }

        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String receivedSignature = parts[2];

        // Recreate the data to sign (Header.Payload)
        String dataToSign = encodedHeader + "." + encodedPayload;

        try {
            // Recreate the signature using the same algorithm and compare
            String expectedSignature = createSignature(dataToSign, SECRET_KEY);

            if (!expectedSignature.equals(receivedSignature)) {
                return false; // Signature mismatch
            }

            // Decode the payload and check claims
            String payloadJson = base64UrlDecode(encodedPayload);
            Map<String, Object> payload = parseJson(payloadJson);

            // Check email and password match
            if (!email.equals(payload.get("email")) || !password.equals(payload.get("password"))) {
                return false;
            }

            // Check token expiration
            long iat = Long.parseLong(payload.get("iat").toString());
            long currentTimestamp = System.currentTimeMillis() / 1000;
            return (currentTimestamp - iat) <= 99999999; // Token must be within 1 hour,changed for development
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Algorithm not found for signature creation - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error parsing payload: " + e.getMessage());
            return false;
        }*/
    }

    // Decode and parse the token (just for demonstration, no real validation)
    public static String parseToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        String encodedPayload = parts[1];
        return base64UrlDecode(encodedPayload);
    }

    // Helper method to parse a JSON string into a Map
    private static Map<String, Object> parseJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }
    public static boolean validateToken(String token) {
        return  true; //for debugging
        /*String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false; // Invalid token format
        }

        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String receivedSignature = parts[2];

        // Recreate the data to sign (Header.Payload)
        String dataToSign = encodedHeader + "." + encodedPayload;

        try {
            // Recreate the signature using the same algorithm and compare
            String expectedSignature = createSignature(dataToSign, SECRET_KEY);

            // Compare the expected signature with the received signature
            return expectedSignature.equals(receivedSignature);
        } catch (NoSuchAlgorithmException e) {
            // Log the exception or handle it as needed
            System.err.println("Error: Algorithm not found for signature creation - " + e.getMessage());
            return false;
        }*/
    }

    public static boolean validateToken(String token, String email) {
        return  true; //for debugging
        /*String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false; // Invalid token format
        }

        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String receivedSignature = parts[2];

        // Recreate the data to sign (Header.Payload)
        String dataToSign = encodedHeader + "." + encodedPayload;

        try {
            // Recreate the signature using the same algorithm and compare
            String expectedSignature = createSignature(dataToSign, SECRET_KEY);

            if (!expectedSignature.equals(receivedSignature)) {
                return false; // Signature mismatch
            }

            // Decode the payload and check claims
            String payloadJson = base64UrlDecode(encodedPayload);
            System.out.println("Decoded Payload: " + payloadJson);  // Debugging line
            Map<String, Object> payload = parseJson(payloadJson);

            // Check email match
            String payloadEmail = (String) payload.get("email");
            System.out.println("Extracted Email: " + payloadEmail);  // Debugging line
            if (payloadEmail == null || !email.equals(payloadEmail)) {
                System.out.println("wrong email");
                return false;
            }

            // Check token expiration
            long iat = Long.parseLong(payload.get("iat").toString());
            long currentTimestamp = System.currentTimeMillis() / 1000;
            return (currentTimestamp - iat) <= 999999999; // Token must be within 1 hour, changed for testing
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Algorithm not found for signature creation - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error parsing payload: " + e.getMessage());
            return false;
        }*/
    }
}
