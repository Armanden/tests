package com.qrreader;

import com.google.zxing.*;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main QR Reader Application with CLI interface
 */
public class QRReaderApplication {

    private static final Logger logger = LoggerFactory.getLogger(QRReaderApplication.class);
    private static final QRCodeScanner scanner = new QRCodeScanner();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     QR CODE READER & GENERATOR     ║");
        System.out.println("╚════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("\nEnter your choice (1-4): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        generateQRCode(scanner);
                        break;
                    case 2:
                        decodeQRCode(scanner);
                        break;
                    case 3:
                        displayQRCodeInfo(scanner);
                        break;
                    case 4:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n┌─────────────────────────┐");
        System.out.println("│ 1. Generate QR Code     │");
        System.out.println("│ 2. Decode QR Code       │");
        System.out.println("│ 3. QR Code Info         │");
        System.out.println("│ 4. Exit                 │");
        System.out.println("└─────────────────────────┘");
    }

    private static void generateQRCode(Scanner scanner) {
        System.out.print("\nEnter text to encode: ");
        String text = scanner.nextLine().trim();

        System.out.print("Enter output file path (e.g., qrcode.png): ");
        String filePath = scanner.nextLine().trim();

        System.out.print("Enter width (default 300): ");
        String widthInput = scanner.nextLine().trim();
        int width = widthInput.isEmpty() ? 300 : Integer.parseInt(widthInput);

        System.out.print("Enter height (default 300): ");
        String heightInput = scanner.nextLine().trim();
        int height = heightInput.isEmpty() ? 300 : Integer.parseInt(heightInput);

        try {
            QRCodeGenerator.generateQRCode(text, filePath, width, height);
            System.out.println("✅ QR Code generated successfully at: " + filePath);
            logger.info("QR Code generated: {} -> {}", text, filePath);
        } catch (WriterException e) {
            System.out.println("❌ Error encoding QR code: " + e.getMessage());
            logger.error("Encoding error", e);
        } catch (IOException e) {
            System.out.println("❌ Error writing file: " + e.getMessage());
            logger.error("IO error", e);
        }
    }

    private static void decodeQRCode(Scanner scanner) {
        System.out.print("\nEnter image file path: ");
        String imagePath = scanner.nextLine().trim();

        try {
            String decodedText = QRReaderApplication.scanner.decodeQRCode(imagePath);
            System.out.println("✅ Decoded text: " + decodedText);
            logger.info("QR Code decoded: {}", decodedText);
        } catch (IOException e) {
            System.out.println("❌ Error reading image: " + e.getMessage());
            logger.error("IO error", e);
        } catch (NotFoundException e) {
            System.out.println("❌ No QR code found in the image.");
            logger.warn("QR code not found");
        } catch (FormatException e) {
            System.out.println("❌ Invalid QR code format: " + e.getMessage());
            logger.error("Format error", e);
        } catch (ChecksumException e) {
            System.out.println("❌ QR code checksum validation failed: " + e.getMessage());
            logger.error("Checksum error", e);
        }
    }

    private static void displayQRCodeInfo(Scanner scanner) {
        System.out.print("\nEnter image file path: ");
        String imagePath = scanner.nextLine().trim();

        try {
            QRCodeScanner.QRCodeInfo info = QRReaderApplication.scanner.getQRCodeInfo(imagePath);
            if (info != null) {
                System.out.println("\n┌─── QR Code Information ───┐");
                System.out.println("│ Text: " + info.text);
                System.out.println("│ Format: " + info.format);
                System.out.println("│ Timestamp: " + info.timestamp);
                System.out.println("└────────────────────────────┘");
                logger.info("QR Code info retrieved: {}", info);
            } else {
                System.out.println("❌ No QR code found in the image.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading image: " + e.getMessage());
            logger.error("IO error", e);
        }
    }
}