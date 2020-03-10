package com.anthem.codec.cipher;


import com.anthem.codec.TestUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
}
