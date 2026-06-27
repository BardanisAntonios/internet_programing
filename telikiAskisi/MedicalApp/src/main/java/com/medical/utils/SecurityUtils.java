package com.medical.utils;

import java.security.MessageDigest;
import java.util.regex.Pattern;

// Βοηθητική κλάση για λειτουργίες ασφαλείας
public class SecurityUtils {

    // 1. Password Hashing (SHA-256)
    // Μετατρέπει τον κωδικό σε μη αναγνώσιμη μορφή για ασφαλή αποθήκευση
    public static String hashPassword(String password) {
        // Ασφάλεια: Αν το password είναι null (π.χ. από ZAP attack), επιστρέφουμε null
        if (password == null) {
            return null;
        }

        try {
            // Χρήση του αλγορίθμου SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes("UTF-8"));
            
            // Μετατροπή των bytes σε δεκαεξαδική μορφή (Hex String)
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Σφάλμα κατά την κρυπτογράφηση", e);
        }
    }

    // 2. Input Sanitization (Προστασία από XSS)
    // Καθαρίζει τα δεδομένα εισόδου από επικίνδυνους χαρακτήρες HTML/Script
    public static String sanitize(String input) {
        if (input == null) return null;
        
        // Αντικατάσταση χαρακτήρων με HTML Entities
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;")
                    .replace("/", "&#x2F;");
    }

    // 3. Έλεγχος Ισχυρού Κωδικού (Password Policy)
    // Ελέγχει αν ο κωδικός έχει τουλάχιστον 8 χαρακτήρες, γράμματα και αριθμούς
    public static boolean isStrongPassword(String password) {
        if (password == null) return false;
        
        // Regex: Τουλάχιστον 1 γράμμα, 1 αριθμός, συνολικά 8+ χαρακτήρες
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }
}