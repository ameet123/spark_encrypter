package com.anthem.codec.cipher;

import com.anthem.codec.config.EncrConstants;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.callbacks.KeyringConfigCallbacks;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.InMemoryKeyring;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

/**
 * PgP Encryption provider based on {@link org.bouncycastle.jce.provider.BouncyCastleProvider}
 */
public class PgPProvider implements EncryptDecrypt {
    private static final Logger LOGGER = LoggerFactory.getLogger(PgPProvider.class);
    private String PUB_KEY_STR;
    private OutputStream outputStream;

    public PgPProvider() {
        LOGGER.info(">> Recipient public key from file:{}", EncrConstants.pubKey);
        try {
            PUB_KEY_STR =
                    new String(Files.readAllBytes(Paths.get(getClass().getResource(EncrConstants.pubKey).toURI())));
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(">>Cannot read public key:{}", EncrConstants.pubKey);
            throw new RuntimeException("No Public key", e);
        }
    }

    private InMemoryKeyring keyring() throws IOException, PGPException {
        InMemoryKeyring keyring =
                KeyringConfigs.forGpgExportedKeys(KeyringConfigCallbacks.withPassword(EncrConstants.password));
        keyring.addPublicKey(PUB_KEY_STR.getBytes());
        return keyring;
    }

    @Override
    public OutputStream cipherStream(OutputStream out) {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
        outputStream = null;
        try {
            outputStream = BouncyGPG
                    .encryptToStream()
                    .withConfig(keyring())
                    .withStrongAlgorithms()
                    .toRecipient(EncrConstants.recipient)
                    .andDoNotSign()
                    .armorAsciiOutput()
                    .andWriteTo(bufferedOutputStream);
        } catch (PGPException | SignatureException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            LOGGER.error("ERR: Pgp encryption of stream", e);
            throw new RuntimeException(e);
        }
        return outputStream;
    }

    @Override
    public void terminate() {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error("ERR:Closing output stream", e);
        }
    }

    @Override
    public void flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("ERR:Flushing output stream", e);
        }
    }

    @Override
    public String extension() {
        return EncrConstants.extension;
    }
}
