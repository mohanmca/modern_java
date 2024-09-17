package com.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DecimalTool {
    public static void reverse(byte[] array) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            // Swap elements at left and right indices
            byte temp = array[left];
            array[left++] = array[right];
            array[right--] = temp;
        }
    }
    public static BigDecimal toDecimalV3(long mantissaLow, long mantissaHigh, short exponent, ByteOrder order) {
        // Allocate a ByteBuffer to store both mantissaHigh and mantissaLow
        ByteBuffer buffer = ByteBuffer.allocate(16); // 8 bytes for each long (total 16 bytes)

        // Set the byte order (endianness)
        buffer.order(order);
        int index = 0;

        // Insert mantissaHigh and mantissaLow at the specified index
        buffer.putLong(index, mantissaLow);
        buffer.putLong(index + 8, mantissaHigh);

        // Convert the buffer into a byte array
        byte[] mantissaBytes = buffer.array();
        reverse(mantissaBytes);
        // Convert the byte array into a BigInteger (positive number)
        BigInteger mantissa = new BigInteger(1, mantissaBytes);

        // Create a BigDecimal using mantissa and exponent
        BigDecimal result = new BigDecimal(mantissa, -exponent);

        return result;
    }

    public static BigDecimal toDecimalV2(long mantissaLow, long mantissaHigh, short exponent) {
        // Allocate a ByteBuffer to store both mantissaHigh and mantissaLow
        ByteBuffer buffer = ByteBuffer.allocate(16); // 8 bytes for each long (total 16 bytes)

        // Put mantissaHigh and mantissaLow into the buffer
        buffer.putLong(mantissaHigh);
        buffer.putLong(mantissaLow);


        // Convert the buffer into a byte array
        byte[] mantissaBytes = buffer.array();

        // Convert the byte array into a BigInteger
        BigInteger mantissa = new BigInteger(1, mantissaBytes); // The '1' signifies a positive number

        // Create a BigDecimal using mantissa and exponent
        BigDecimal result = new BigDecimal(mantissa, -exponent);

        return result;
    }

    public static BigDecimal toDecimal(long mantissaLow, long mantissaHigh, short exponent) {
        // Combine mantissaLow and mantissaHigh into a single BigInteger
        BigInteger high = BigInteger.valueOf(mantissaHigh);
        BigInteger low = BigInteger.valueOf(mantissaLow);

        // Shift the high part to the left to make space for the low part
        BigInteger mantissa = high.shiftLeft(64).add(low);

        // Create a BigDecimal using mantissa and exponent
        BigDecimal result = new BigDecimal(mantissa, -exponent);

        return result;
    }

    public static void main(String[] args) {
        // Example usage
        long mantissaLow = 1234567890123456789L;
        long mantissaHigh = 987654321098765432L;
        short exponent = -10;


//        System.out.println("Resulting BigDecimal: " + toDecimal(mantissaLow, mantissaHigh, exponent) + " - " + toDecimalV2(mantissaLow, mantissaHigh, exponent));
//        System.out.println("Resulting BigDecimal: " + toDecimal(0L, 0L, (short) 0)+ " - " + toDecimalV2(0L, 0L, (short) 0));
//        System.out.println("Resulting BigDecimal: " + toDecimal(1L, 0L, (short) 0)+ " - " + toDecimalV2(1L, 0L, (short) 0));
//        System.out.println("Resulting BigDecimal: " + toDecimal(1L, 1L, (short) 0)+ " - " + toDecimalV2(1L, 1L, (short) 0));

//        Resulting BigDecimal: 0.0047775600000000000 - 0.0047775600000000000
//        Resulting BigDecimal: 1.8446744073709551616 - 1.8446744073709551616
//        Resulting BigDecimal: 0.2162470396180559875 - 0.2162470396180559875
//        Resulting BigDecimal: 0.0100964800000000000 - 0.0100964800000000000
//        Resulting BigDecimal: 444290.6994770000000000000 - 444290.6994770000000000000

        System.out.println("Resulting BigDecimal: " + toDecimal(47775600000000000L, 0L, (short) -19).toPlainString() + " - " + toDecimalV2(47775600000000000L, 0L, (short) -19).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(0L, 1L, (short) -19).toPlainString() + " - " + toDecimalV2(0L, 1L, (short) -19).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(2162470396180559875L, 0L, (short) -19).toPlainString() + " - " + toDecimalV2(2162470396180559875L, 0L, (short) -19).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(100964800000000000L, 0L, (short) -19).toPlainString() + " - " + toDecimalV2(100964800000000000L, 0L, (short) -19).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(8684617054493286400L, 240850L, (short) -19).toPlainString() + " - " + toDecimalV2(8684617054493286400L, 240850L, (short) -19).toPlainString());

        System.out.println("\n\nResulting BigDecimal: " + toDecimal(47775600000000000L, 0L, (short) -19).toPlainString() + " - " + toDecimalV3(47775600000000000L, 0L, (short) -19, ByteOrder.LITTLE_ENDIAN).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(0L, 1L, (short) -19).toPlainString() + " - " + toDecimalV3(0L, 1L, (short) -19, ByteOrder.LITTLE_ENDIAN).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(2162470396180559875L, 0L, (short) -19).toPlainString() + " - " + toDecimalV3(2162470396180559875L, 0L, (short) -19, ByteOrder.LITTLE_ENDIAN).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(100964800000000000L, 0L, (short) -19).toPlainString() + " - " + toDecimalV3(100964800000000000L, 0L, (short) -19, ByteOrder.LITTLE_ENDIAN).toPlainString());
        System.out.println("Resulting BigDecimal: " + toDecimal(8684617054493286400L, 240850L, (short) -19).toPlainString() + " - " + toDecimalV3(8684617054493286400L, 240850L, (short) -19, ByteOrder.LITTLE_ENDIAN).toPlainString());
    }
}

