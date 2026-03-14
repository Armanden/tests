package com.qrreader;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * QR Code Scanner class to decode and read QR codes from images
 */
public class QRCodeScanner {

    private final MultiFormatReader multiFormatReader;
    private final QRCodeReader qrCodeReader;

    public QRCodeScanner() {
        this.multiFormatReader = new MultiFormatReader();
        this.qrCodeReader = new QRCodeReader();
        configureReader();
    }

    /**
     * Configure the reader with hints for better decoding
     */
    private void configureReader() {
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        multiFormatReader.setHints(hints);
    }

    /**
     * Decode a QR code from an image file
     *
     * @param imagePath Path to the image file containing the QR code
     * @return The decoded text from the QR code
     * @throws IOException        if the image cannot be read
     * @throws NotFoundException  if no QR code is found
     * @throws FormatException    if the QR code format is invalid
     * @throws ChecksumException  if checksum validation fails
     */
    public String decodeQRCode(String imagePath)
            throws IOException, NotFoundException, FormatException, ChecksumException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        return decodeFromBufferedImage(bufferedImage);
    }

    /**
     * Decode a QR code from a BufferedImage
     *
     * @param image The BufferedImage containing the QR code
     * @return The decoded text from the QR code
     * @throws NotFoundException if no QR code is found
     * @throws FormatException   if the QR code format is invalid
     * @throws ChecksumException if checksum validation fails
     */
    public String decodeFromBufferedImage(BufferedImage image)
            throws NotFoundException, FormatException, ChecksumException {
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);

        Result result = multiFormatReader.decode(bitmap);
        return result.getText();
    }

    /**
     * Decode multiple codes from an image (if present)
     *
     * @param imagePath Path to the image file
     * @return List of decoded texts
     * @throws IOException if the image cannot be read
     */
    public List<String> decodeMultipleCodes(String imagePath) throws IOException {
        List<String> results = new ArrayList<>();
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));

        // Try standard decoding
        try {
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            Result result = multiFormatReader.decode(bitmap);
            results.add(result.getText());
        } catch (NotFoundException | FormatException | ChecksumException e) {
            // Silently continue if no code found
        }

        return results;
    }

    /**
     * Get detailed information about a QR code
     *
     * @param imagePath Path to the image file
     * @return QRCodeInfo object with detailed information
     * @throws IOException if the image cannot be read
     */
    public QRCodeInfo getQRCodeInfo(String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);

        try {
            Result result = multiFormatReader.decode(bitmap);
            return new QRCodeInfo(
                    result.getText(),
                    result.getBarcodeFormat().toString(),
                    result.getTimestamp()
            );
        } catch (NotFoundException | FormatException | ChecksumException e) {
            return null;
        }
    }

    /**
     * Inner class to hold QR Code information
     */
    public static class QRCodeInfo {
        public final String text;
        public final String format;
        public final long timestamp;

        public QRCodeInfo(String text, String format, long timestamp) {
            this.text = text;
            this.format = format;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "QRCodeInfo{" +
                    "text='" + text + '\'' +
                    ", format='" + format + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }
}