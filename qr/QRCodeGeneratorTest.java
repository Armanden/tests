package com.qrreader;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit tests for QRCodeGenerator
 */
public class QRCodeGeneratorTest {

    private static final String TEST_OUTPUT_DIR = "test_qrcodes/";
    private QRCodeScanner scanner;

    @Before
    public void setUp() {
        scanner = new QRCodeScanner();
        new File(TEST_OUTPUT_DIR).mkdirs();
    }

    @After
    public void tearDown() {
        // Clean up test files
        File dir = new File(TEST_OUTPUT_DIR);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            dir.delete();
        }
    }

    @Test
    public void testGenerateQRCode() throws WriterException, IOException {
        String testText = "Hello World";
        String filePath = TEST_OUTPUT_DIR + "test_qrcode.png";

        QRCodeGenerator.generateQRCode(testText, filePath);

        File generatedFile = new File(filePath);
        assertTrue("QR code file should exist", generatedFile.exists());
        assertTrue("QR code file should have content", generatedFile.length() > 0);
    }

    @Test
    public void testGenerateAndDecodeQRCode() throws WriterException, IOException, Exception {
        String testText = "Test123";
        String filePath = TEST_OUTPUT_DIR + "test_encode_decode.png";

        QRCodeGenerator.generateQRCode(testText, filePath);
        String decodedText = scanner.decodeQRCode(filePath);

        assertEquals("Decoded text should match original", testText, decodedText);
    }

    @Test
    public void testGenerateQRCodeWithCustomDimensions() throws WriterException, IOException {
        String testText = "Custom Size";
        String filePath = TEST_OUTPUT_DIR + "test_custom_size.png";

        QRCodeGenerator.generateQRCode(testText, filePath, 500, 500);

        File generatedFile = new File(filePath);
        assertTrue("Custom size QR code should exist", generatedFile.exists());
    }

    @Test
    public void testGenerateQRCodeWithErrorCorrection() throws WriterException, IOException {
        String testText = "Error Correction Test";
        String filePath = TEST_OUTPUT_DIR + "test_error_correction.png";

        QRCodeGenerator.generateQRCodeWithErrorCorrection(testText, filePath,
                ErrorCorrectionLevel.H);

        File generatedFile = new File(filePath);
        assertTrue("Error correction QR code should exist", generatedFile.exists());
    }

    @Test
    public void testSpecialCharactersInQRCode() throws WriterException, IOException, Exception {
        String testText = "Special: !@#$%^&*() 中文 العربية";
        String filePath = TEST_OUTPUT_DIR + "test_special_chars.png";

        QRCodeGenerator.generateQRCode(testText, filePath);
        String decodedText = scanner.decodeQRCode(filePath);

        assertEquals("Should handle special characters", testText, decodedText);
    }
}