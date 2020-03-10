package com.anthem.codec.cipher;

import java.io.OutputStream;

/**
 * an interface that mandates function necessary for encryption.
 * It extracts the logic of pure encryption out of compression and other housekeeping
 */
public interface EncryptDecrypt {
    /**
     * encrypt the stream
     *
     * @param out streamed passed by upstream component
     * @return encrypted stream
     */
    OutputStream cipherStream(OutputStream out);

    /**
     * any end of stream processing required, such as flush and close, can be performed here.
     */
    void terminate();

    /**
     * call flush on cipher stream
     */
    void flush();

    /**
     * file extension
     *
     * @return String extension for file
     */
    String extension();
}
