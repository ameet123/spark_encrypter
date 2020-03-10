package com.anthem.codec;

import com.anthem.codec.cipher.EncryptDecrypt;
import com.anthem.codec.cipher.KeyCipherProvider;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.*;

/**
 * test the base Codec class
 */
public class TestEncr {

    @Test
    public void testEncrOnlyWithPgP() throws IOException {
        EncryptionCodec outCodec = new EncryptionCodec();
        EncryptDecrypt provider = outCodec.getProvider();
        String fileName = "data/essay_from_codec" + provider.extension();
        if (new File(fileName).exists()) {
            System.out.println(String.format(">>Deleting sample encrypt file:%s", new File(fileName).delete()));
        }
        FileOutputStream fs = new FileOutputStream(fileName);

        // Encrypt
        CompressionOutputStream out = outCodec.createOutputStream(fs);
        out.write(TestUtil.MSG.getBytes());
        out.flush();
        out.close();

        System.out.println(">>> ENCRYPTION Done!");

        // decrypt
//        provider.decryptInit();
//        FileInputStream fis = new FileInputStream(fileName);
//        CipherInputStream in = new CipherInputStream(fis, aes2);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        byte[] b = new byte[1024];
//        int numberOfBytedRead;
//        while ((numberOfBytedRead = in.read(b)) >= 0) {
//            baos.write(b, 0, numberOfBytedRead);
//        }
//        String decrypted = new String(baos.toByteArray());
//        System.out.println(decrypted);
//        Assert.assertEquals(TestUtil.MSG, decrypted);
    }

    @Test
    public void testEncrSparkFile() throws IOException {
//        String fileName = "test_encr.gpg";
//        String decryptedPlainFile = "test_plain.csv";
//        EncryptionCodec outCodec = new EncryptionCodec();
//        KeyCipherProvider provider = outCodec.getProvider();
//        Cipher aes2 = provider.getCipher();
//        // decrypt
//        provider.decryptInit();
//        FileInputStream fis = new FileInputStream(fileName);
//        CipherInputStream in = new CipherInputStream(fis, aes2);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] b = new byte[1024];
//        int numberOfBytedRead;
//        while ((numberOfBytedRead = in.read(b)) >= 0) {
//            baos.write(b, 0, numberOfBytedRead);
//        }
//        try (OutputStream outputStream = new FileOutputStream(decryptedPlainFile)) {
//            baos.writeTo(outputStream);
//        }
    }
}
