package com.ameet.codec.cipher;

import com.ameet.codec.config.EncrConstants;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.callbacks.KeyringConfigCallbacks;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.InMemoryKeyring;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

/**
 * PgP Encryption provider based on {@link org.bouncycastle.jce.provider.BouncyCastleProvider}
 */
public class PgPProvider implements EncryptDecrypt {
    private static final Logger LOGGER = LoggerFactory.getLogger(PgPProvider.class);
    private String PUB_KEY_STR, PRIV_KEY_STR;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedOutputStream bufferedOutputStream;
    public PgPProvider() {
        LOGGER.info(">> Recipient public key from file:{}", EncrConstants.pubKey);
//        PUB_KEY_STR = EncUtil.fileToString(EncrConstants.pubKey);
        PUB_KEY_STR = EncrConstants.P_KEY;
        LOGGER.info(">> Recipient PRIVATE key from file:{}", EncrConstants.privKey);
//        PRIV_KEY_STR = EncUtil.fileToString(EncrConstants.privKey);
        PRIV_KEY_STR = EncrConstants.PR_KEY;
        BouncyGPG.registerProvider();
    }

    private InMemoryKeyring keyring() throws IOException, PGPException {

        InMemoryKeyring keyring =
                KeyringConfigs.forGpgExportedKeys(KeyringConfigCallbacks.withPassword(EncrConstants.password));
        keyring.addPublicKey(PUB_KEY_STR.getBytes());
        keyring.addSecretKey(PRIV_KEY_STR.getBytes());
        return keyring;
    }

    @Override
    public OutputStream cipherStream(OutputStream out) {
          bufferedOutputStream = new BufferedOutputStream(out);
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
    public InputStream decipherStream(InputStream in) {
        try {
            if (EncrConstants.isSignRequired) {
                inputStream = BouncyGPG
                        .decryptAndVerifyStream()
                        .withConfig(keyring())
                        .andValidateSomeoneSigned()
                        .fromEncryptedInputStream(in);
            } else {
                inputStream = BouncyGPG
                        .decryptAndVerifyStream()
                        .withConfig(keyring())
                        .andIgnoreSignatures()
                        .fromEncryptedInputStream(in);
            }
        } catch (IOException | NoSuchProviderException | PGPException e) {
            LOGGER.error("ERR: Pgp decryption of stream", e);
            throw new RuntimeException(e);
        }
        return inputStream;
    }

    @Override
    public void terminate() {
        try {
            outputStream.flush();
            bufferedOutputStream.flush();
            outputStream.close();
            bufferedOutputStream.close();
        } catch (IOException e) {
            LOGGER.error("ERR:Closing output stream", e);
        }
    }

    @Override
    public void flush() {
        try {
            outputStream.flush();
            bufferedOutputStream.flush();
        } catch (IOException e) {
            LOGGER.error("ERR:Flushing output stream", e);
        }
    }

    @Override
    public String extension() {
        return EncrConstants.extension;
    }
}
