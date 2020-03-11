package com.ameet.codec.cipher;


import com.ameet.codec.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * test just PGP encryption
 */
public class PgPProviderTest {
    @Test
    public void testEncrOnly() throws IOException {
        PgPProvider provider = new PgPProvider();
        String fileName = "data/essay" + provider.extension();
        if (new File(fileName).exists()) {
            System.out.println(String.format(">>Deleting sample encrypt file:%s", new File(fileName).delete()));
        }
        FileOutputStream fs = new FileOutputStream(fileName);

        // Encrypt
        OutputStream out = provider.cipherStream(fs);
        out.write(TestUtil.MSG.getBytes());
        provider.terminate();
        System.out.println(">>> ENCRYPTION Done!");
    }

    @Test
    public void decipherStream() throws IOException {
        PgPProvider provider = new PgPProvider();
        String fileName = "data/essay" + provider.extension();

        FileInputStream fs = new FileInputStream(fileName);
        InputStream in = provider.decipherStream(fs);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int numberOfBytedRead;
        while ((numberOfBytedRead = in.read(b)) >= 0) {
            baos.write(b, 0, numberOfBytedRead);
        }
        String decrypted = new String(baos.toByteArray());
        Assert.assertEquals(TestUtil.MSG, decrypted);
    }
}
