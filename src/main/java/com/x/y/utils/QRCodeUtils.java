package com.x.y.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.OutputStream;
import java.util.Hashtable;

public class QRCodeUtils {
    private QRCodeUtils() {
    }

    public static void writeToStream(String content, OutputStream outputStream) throws Exception {
        int width = 300;
        int height = 300;
        String format = "png";
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, '1');
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
    }
}