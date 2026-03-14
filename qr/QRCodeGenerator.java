package com.qrreader;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * QR Code Generator class to create and save QR codes
 */
public class QRCodeGenerator {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    private static final String DEFAULT_FORMAT = "png";

    /**
     * Generate a QR code from text and save it to a file
     *
     * @param text        The text to encode
     * @param filePath    Path where to save the QR code image
     * @param width       Width of the QR code image
     * @param height      Height of the QR code image
     * @throws WriterException if encoding fails
     * @throws IOException     if file writing fails
     */
    public static void generateQRCode(String text, String filePath, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 2);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, Path.of(filePath));
    }

    /**
     * Generate a QR code from text with default dimensions
     *
     * @param text     The text to encode
     * @param filePath Path where to save the QR code image
     * @throws WriterException if encoding fails
     * @throws IOException     if file writing fails
     */
    public static void generateQRCode(String text, String filePath)
            throws WriterException, IOException {
        generateQRCode(text, filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Generate QR code with custom error correction level
     *
     * @param text     The text to encode
     * @param filePath Path where to save the QR code image
     * @param level    Error correction level (L, M, Q, H)
     * @throws WriterException if encoding fails
     * @throws IOException     if file writing fails
     */
    public static void generateQRCodeWithErrorCorrection(String text, String filePath,
                                                         ErrorCorrectionLevel level)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, level);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,
                DEFAULT_WIDTH, DEFAULT_HEIGHT, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, Path.of(filePath));
    }
}