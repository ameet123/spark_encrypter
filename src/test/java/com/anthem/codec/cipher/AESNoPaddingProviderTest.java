package com.anthem.codec.cipher;

import com.anthem.codec.TestUtil;
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

        // Encrypt
        OutputStream out = provider.cipherStream(fs);
        out.write(TestUtil.MSG.getBytes());
        provider.terminate();
        System.out.println(">>> ENCRYPTION Done!");
        //Decrypt

    }
}
