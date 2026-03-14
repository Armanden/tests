package com.qrreader;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.IOException;

/**
 * Example usage of QR Code Generator and Scanner
 */
public class UsageExamples {

    public static void main(String[] args) throws WriterException, IOException, Exception {

        // Example 1: Simple QR Code Generation
        System.out.println("Example 1: Generate Simple QR Code");
        QRCodeGenerator.generateQRCode("Hello World", "qrcode_hello.png");
        System.out.println("✅ Generated: qrcode_hello.png\n");

        // Example 2: QR Code with Custom Dimensions
        System.out.println("Example 2: Generate QR Code with Custom Dimensions");
        QRCodeGenerator.generateQRCode("https://github.com", "qrcode_github.png", 400, 400);
        System.out.println("✅ Generated: qrcode_github.png (400x400)\n");

        // Example 3: QR Code with High Error Correction
        System.out.println("Example 3: QR Code with High Error Correction");
        QRCodeGenerator.generateQRCodeWithErrorCorrection(
                "Important Data", "qrcode_secure.png", ErrorCorrectionLevel.H);
        System.out.println("✅ Generated: qrcode_secure.png\n");

        // Example 4: Decode QR Code
        System.out.println("Example 4: Decode QR Code");
        QRCodeScanner scanner = new QRCodeScanner();
        String decoded = scanner.decodeQRCode("qrcode_hello.png");
        System.out.println("✅ Decoded: " + decoded + "\n");

        // Example 5: Get QR Code Information
        System.out.println("Example 5: Get QR Code Information");
        QRCodeScanner.QRCodeInfo info = scanner.getQRCodeInfo("qrcode_github.png");
        if (info != null) {
            System.out.println("✅ QR Code Info:");
            System.out.println("   Text: " + info.text);
            System.out.println("   Format: " + info.format);
            System.out.println("   Timestamp: " + info.timestamp + "\n");
        }

        // Example 6: Generate QR Code with Contact Information (vCard)
        System.out.println("Example 6: Generate QR Code with Contact Information");
        String vcard = "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:John Doe\n" +
                "TEL:+1234567890\n" +
                "EMAIL:john@example.com\n" +
                "END:VCARD";
        QRCodeGenerator.generateQRCode(vcard, "qrcode_contact.png");
        System.out.println("✅ Generated: qrcode_contact.png\n");

        // Example 7: Generate QR Code with WiFi Information
        System.out.println("Example 7: Generate QR Code with WiFi Information");
        String wifiString = "WIFI:T:WPA;S:NetworkName;P:Password;;";
        QRCodeGenerator.generateQRCode(wifiString, "qrcode_wifi.png");
        System.out.println("✅ Generated: qrcode_wifi.png\n");
    }
}