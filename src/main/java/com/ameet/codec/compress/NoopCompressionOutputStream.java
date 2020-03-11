package com.ameet.codec.compress;

import com.ameet.codec.cipher.EncryptDecrypt;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * this is the no-compression mechanism for plugging into codec option in Spark DataFrame write operation
 */
public class NoopCompressionOutputStream extends CompressionOutputStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoopCompressionOutputStream.class);
    private EncryptDecrypt encryptDecrypt;

    public NoopCompressionOutputStream(OutputStream out, EncryptDecrypt encryptDecrypt) {
        super(out);
        this.encryptDecrypt = encryptDecrypt;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    @Override
    public void finish() throws IOException {
        LOGGER.info(">>Finishing encryption thr' child mechanism.");
        encryptDecrypt.terminate();
    }

    @Override
    public void resetState() throws IOException {
        LOGGER.info(">>ResetState called in encrypter");
    }

    @Override
    public void close() throws IOException {
        super.close();
        encryptDecrypt.terminate();
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        encryptDecrypt.flush();
    }
}
