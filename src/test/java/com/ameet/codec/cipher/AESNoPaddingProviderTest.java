package com.ameet.codec.cipher;

import com.ameet.codec.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;


public class AESNoPaddingProviderTest {

    @Test
    public void cipherStream() throws IOException {
        AESNoPaddingProvider provider = new AESNoPaddingProvider();
        String fileName = "data/essay_aes" + provider.extension();
        if (new File(fileName).exists()) {
            System.out.println(String.format(">>Deleting sample encrypt file:%s", new File(fileName).delete()));
        }
        FileOutputStream fs = new FileOutputStream(fileName);

        OutputStream out = provider.cipherStream(fs);
        out.write(TestUtil.MSG.getBytes());
        provider.terminate();
        System.out.println(">>> ENCRYPTION Done!");
    }

    @Test
    public void decipherStream() throws IOException {
        AESNoPaddingProvider provider = new AESNoPaddingProvider();
        String fileName = "data/essay_aes" + provider.extension();

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
