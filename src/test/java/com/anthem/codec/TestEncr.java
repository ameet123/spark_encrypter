package com.anthem.codec;

import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;

public class TestEncr {

    @Test
    public void testEncr() throws InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        final String MSG = "    Looking back on a childhood filled with events and memories, I find it rather " +
                "difficult to pick one that leaves me with the fabled warm and fuzzy feelings. As the daughter of an " +
                "Air Force major, I had the pleasure of traveling across America in many moving trips. I have visited" +
                " the monstrous trees of the Sequoia National Forest, stood on the edge of the Grand Canyon and have " +
                "jumped on the beds at Caesar's Palace in Lake Tahoe.\n" +
                "\n" +
                "    The day I picked my dog up from the pound was one of the happiest days of both of our lives. I " +
                "had gone to the pound just a week earlier with the idea that I would just look at a puppy. Of " +
                "course, you can no more just look at those squiggling little faces so filled with hope and joy than " +
                "you can stop the sun from setting in the evening. I knew within minutes of walking in the door that " +
                "I would get a puppy but it wasn't until I saw him that I knew I had found my puppy.\n" +
                "\n" +
                "    Looking for houses was supposed to be a fun and exciting process. Unfortunately, none of the " +
                "ones that we saw seemed to match the specifications that we had established. They were too small, " +
                "too impersonal, too close to the neighbors. After days of finding nothing even close, we began to " +
                "wonder: was there really a perfect house out there for us?\n" +
                "\n";
        String fileName = "Encrypted.txt";
        if (new File(fileName).exists()) {
            System.out.println(String.format(">>Deleting sample encrypt file:%s", new File(fileName).delete()));
        }
        FileOutputStream fs = new FileOutputStream(fileName);

        EncryptionCodec outCodec = new EncryptionCodec();
        Key k = outCodec.getSecretKey();
        Cipher aes2 = outCodec.getAes();
        CompressionOutputStream out = outCodec.createOutputStream(fs);
        out.write(MSG.getBytes());
        out.flush();
        out.close();

        System.out.println(">>> ENCRYPTION Done!");
        aes2.init(Cipher.DECRYPT_MODE, k, outCodec.getIvspec());

        FileInputStream fis = new FileInputStream(fileName);
        CipherInputStream in = new CipherInputStream(fis, aes2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int numberOfBytedRead;
        while ((numberOfBytedRead = in.read(b)) >= 0) {
            baos.write(b, 0, numberOfBytedRead);
        }
        String decrypted = new String(baos.toByteArray());
        System.out.println(decrypted);
        Assert.assertEquals(MSG, decrypted);
    }
}
