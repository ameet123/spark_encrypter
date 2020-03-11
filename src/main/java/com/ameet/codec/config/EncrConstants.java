package com.ameet.codec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EncrConstants {
    private static final String PROP_FILE = "application.properties";
    private static final String PASSWORD_KEY = "password";
    private static final String EXT_KEY = "extension";
    private static final String IS_SIGN_KEY = "decrypt_w_signing";
    private static final String ENCR_STRAT_KEY = "encrypt_strategy";
    private static final Logger LOGGER = LoggerFactory.getLogger(EncrConstants.class);
    public static String password;
    public static String extension;
    public static String pubKey, privKey;
    public static String recipient;
    public static String encryptionStrategy;
    public static boolean isSignRequired;

    static {
        LOGGER.info(">>Reading properties from :{}", PROP_FILE);
        Properties properties = new Properties();
        InputStream propertyStream = EncrConstants.class.getClassLoader().getResourceAsStream(EncrConstants.PROP_FILE);
        try {
            properties.load(propertyStream);
        } catch (IOException e) {
            LOGGER.error("ERR: Could not load property file", e);
            throw new RuntimeException(e);
        }
        password = properties.getProperty(EncrConstants.PASSWORD_KEY);
        extension = properties.getProperty(EncrConstants.EXT_KEY);
        pubKey = properties.getProperty("public_key");
        privKey = properties.getProperty("private_key");
        isSignRequired = Boolean.parseBoolean(properties.getProperty(IS_SIGN_KEY));
        recipient = properties.getProperty("recipient");
        encryptionStrategy = properties.getProperty(ENCR_STRAT_KEY);
        LOGGER.info(">>Encryption Strategy:{} Encrypted file extension:{}", encryptionStrategy, extension);
    }
    public static String P_KEY="-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
            "\n" +
            "mQENBF5nkA8BCACaxVNrE0Hyd2yUXJO3WRXDnbPD8QB8pafjVJgGTi+riwRe/ECB\n" +
            "oM91P6rjkY+zMBafmY3XL4XHy7Pa3ehewbYiXSqe0XqZeUjIOMj/RrB2QpLErcVD\n" +
            "4hoP9KLNjFVNQNptw5gTw8VIRbcQZJZTMwRl2qw+au8b2fiHl0xKuhzx8x1VvU7P\n" +
            "mAr4AB4OVRNilM2jhs49N/MoqrDiX/ONUNchgHUy6v86gBZa44P+kaevybJR8qv3\n" +
            "pmAsg26Y6BnGPIh0jLPOEM3fd9yLuqy1AJ+zn5wSgFVcUhjCDgK0Zogvz27W8Hlz\n" +
            "VHmzmhIX4kqrhfbNKODBAccl4Db3MgcLTxsZABEBAAG0JUFtZWV0Q2hhdWJhbCA8\n" +
            "YW1lZXRjaGF1YmFsQGdtYWlsLmNvbT6JAVQEEwEIAD4WIQSAPJu5x7oXDgfsd8cY\n" +
            "7VTGB+rdkQUCXmeQDwIbAwUJA8JnAAULCQgHAgYVCgkICwIEFgIDAQIeAQIXgAAK\n" +
            "CRAY7VTGB+rdkUDmB/4ogyUmAGTqLPz51bh5P+kFtmEHF1OCuPd8JY/PRsrBCmvh\n" +
            "rntWptQgoXQ2qkblTGAGPxxMXN8YfKB/OefGx8GcCGE5SN16Hy9/1pKRvYsuBAgU\n" +
            "7Ybz/2XLkodvaQLYhPHSrLJewirXIpk9VixqcH+OVYfVSPiE/kfCtG3dv0GssF1J\n" +
            "L9cFTfLQIUkYpDbZy6oYo78NEKDfEAjleUAXQxJpjBMQCWGT3jgwa99S8t+/16yI\n" +
            "/AcZ7dw3tLS2jHAN81rrDeYItQKv+7I+W1Bpe3dGuWyN8y6HKghWYMjxx5qCFN5c\n" +
            "qOMrlJ4tyt3Ho5AihyV/ByJgORlFzn55PCbPCzVpuQENBF5nkA8BCADoAzdyRJ1j\n" +
            "5pE47xUHWIwBUPNWuGsl/CGaRMku+5qJkxho8aHRDHfY58Gru9xkx5Cku8rfin+U\n" +
            "sBX18QlYyurw8X8NrJsD/6C7UIbivDhHGxJtglkmelRvkbDFNvMevhG+6ZJxlX9m\n" +
            "GTO/u4k65B7p0DlthnedLjvQNthH7c5dtfQl7bRRf4p9h63pvHzjfDn8fd0yAQIi\n" +
            "iDggJZydGnDHvg9EKK91eEqrqWjnVLp2VCUJ+mT65uoKNjbW+LmNlv4lNb5WwDPE\n" +
            "aVZ0L5tI6RCMz1OtxIui7lbdXpsXdKWITSaPkImZ/gOL2ffnBAL+IvWsp1kQmtkC\n" +
            "M1fgnUwXsQVdABEBAAGJATwEGAEIACYWIQSAPJu5x7oXDgfsd8cY7VTGB+rdkQUC\n" +
            "XmeQDwIbDAUJA8JnAAAKCRAY7VTGB+rdkc2qB/42Yb2vun9VGzcfE5Fy3UhUJuni\n" +
            "eFYJjJCKXKYJ6rKoNUiMsGmSfKaH0B0grZKmv796fQV+xn66WmZibQqLktIrY9Et\n" +
            "vz6cs7pk05i7Pb8+E5Di096B/RonV0mSv2eK1ta8MrKjrVTAFbM1av5TqZQD6pl6\n" +
            "9YP58QMKts9rjPxkNB0u2A9oc+HsmuVuphbmb69EAGuLbnMnRl0ARpDrDyZNuNMU\n" +
            "T5nqNFu/GgdmppO9aoLHl7jbBoxKXSS2jCQICciHanNIwv7wS2MKSQ8FAHwOalL/\n" +
            "vUW2f4dNj3JrTUvtdUdZ8Nn6JDIRl4SrhiLHkfgSUDV1HdByPPuKizG8N6Qk\n" +
            "=abe7\n" +
            "-----END PGP PUBLIC KEY BLOCK-----\n";
    public static String PR_KEY="-----BEGIN PGP PRIVATE KEY BLOCK-----\n" +
            "\n" +
            "lQPFBF5nkA8BCACaxVNrE0Hyd2yUXJO3WRXDnbPD8QB8pafjVJgGTi+riwRe/ECB\n" +
            "oM91P6rjkY+zMBafmY3XL4XHy7Pa3ehewbYiXSqe0XqZeUjIOMj/RrB2QpLErcVD\n" +
            "4hoP9KLNjFVNQNptw5gTw8VIRbcQZJZTMwRl2qw+au8b2fiHl0xKuhzx8x1VvU7P\n" +
            "mAr4AB4OVRNilM2jhs49N/MoqrDiX/ONUNchgHUy6v86gBZa44P+kaevybJR8qv3\n" +
            "pmAsg26Y6BnGPIh0jLPOEM3fd9yLuqy1AJ+zn5wSgFVcUhjCDgK0Zogvz27W8Hlz\n" +
            "VHmzmhIX4kqrhfbNKODBAccl4Db3MgcLTxsZABEBAAH+BwMC58kiSGvLBD7Kz+bG\n" +
            "W6O+EfvsGoql4rnAjnVS0yqm4BLFjDFzEIFr0J/JvL8Z0/wK4EwnI2LAiNk297Wv\n" +
            "z01LzFkvXrKg//eXjoNp7eQhxKZ4f7DKAt7K9xn3iwZC0tgeXdDpK0QM/UIPGaRB\n" +
            "k4mxuFPIoFxVTuuT73AmXoeWjoAsP80h8tyzajpBT4tJGnk6xf/jaR5sULcJgkxd\n" +
            "Ju7G1OqI7yGDew1CgBTL4LEssEpcLXp6MIELhXsotWeFYKG8r6E4WV/5aAKkxcCx\n" +
            "+ATDXTIXL1GgsYcjeZaFORDoENi1mc6QICXQNTqDY+GGGs7uzkeyS0URaj95CZR7\n" +
            "x1nYv3l8Ha9yZCXOQkoSnn0cNLlfmc7+I+N5ydtLy8XsFOaGyQ/5HWx55Jykk+f7\n" +
            "HqfEei0tXN5dRAYzDrjdvn/mSWFE+2vYs6lBEk/E7TuTUx+pXjQ7cUSlx0d3+SSX\n" +
            "83G4vn3CuaGFXDVtpKdhwdy1R+KRVmoUx+3HiHhrb9x6a99rV2HAmGQfQQw/hrDP\n" +
            "nJvqEy0m+zs4WgScOtk732q5APHnSa+olWPhkGy6VEFrS4Al/SfOmnTRcTOzssU4\n" +
            "wnePWTvEQBLUTrpU879AsgdL7vCcerqz8lJTAIpkph+IXu9lhzot2PkgPzEcR3uo\n" +
            "3ccJNkBBU5unxA0KBQwFnRf4SPL7VKXCpAwJUBz6ngiUbGYQi5fI57d2rIdjmThJ\n" +
            "ATJ6v+XOh2dOIub9DVlqJ9gALmpSQF+xnYqza7EEz0GeVasKEdDG/lRbxntgt3zk\n" +
            "8cVFR4AU4K6sV5y7fEO4NKtGRhDDPLi8y25jbDKpyeHy/eSvtTrS705OwKCgR3C9\n" +
            "VJf7jkqU/SOhHxvF8GycEKaL+s5vQuUgngYRAOLhT0y+kea4/rJp+PhFsaEccU3X\n" +
            "fYlODndOb5e0JUFtZWV0Q2hhdWJhbCA8YW1lZXRjaGF1YmFsQGdtYWlsLmNvbT6J\n" +
            "AVQEEwEIAD4WIQSAPJu5x7oXDgfsd8cY7VTGB+rdkQUCXmeQDwIbAwUJA8JnAAUL\n" +
            "CQgHAgYVCgkICwIEFgIDAQIeAQIXgAAKCRAY7VTGB+rdkUDmB/4ogyUmAGTqLPz5\n" +
            "1bh5P+kFtmEHF1OCuPd8JY/PRsrBCmvhrntWptQgoXQ2qkblTGAGPxxMXN8YfKB/\n" +
            "OefGx8GcCGE5SN16Hy9/1pKRvYsuBAgU7Ybz/2XLkodvaQLYhPHSrLJewirXIpk9\n" +
            "VixqcH+OVYfVSPiE/kfCtG3dv0GssF1JL9cFTfLQIUkYpDbZy6oYo78NEKDfEAjl\n" +
            "eUAXQxJpjBMQCWGT3jgwa99S8t+/16yI/AcZ7dw3tLS2jHAN81rrDeYItQKv+7I+\n" +
            "W1Bpe3dGuWyN8y6HKghWYMjxx5qCFN5cqOMrlJ4tyt3Ho5AihyV/ByJgORlFzn55\n" +
            "PCbPCzVpnQPGBF5nkA8BCADoAzdyRJ1j5pE47xUHWIwBUPNWuGsl/CGaRMku+5qJ\n" +
            "kxho8aHRDHfY58Gru9xkx5Cku8rfin+UsBX18QlYyurw8X8NrJsD/6C7UIbivDhH\n" +
            "GxJtglkmelRvkbDFNvMevhG+6ZJxlX9mGTO/u4k65B7p0DlthnedLjvQNthH7c5d\n" +
            "tfQl7bRRf4p9h63pvHzjfDn8fd0yAQIiiDggJZydGnDHvg9EKK91eEqrqWjnVLp2\n" +
            "VCUJ+mT65uoKNjbW+LmNlv4lNb5WwDPEaVZ0L5tI6RCMz1OtxIui7lbdXpsXdKWI\n" +
            "TSaPkImZ/gOL2ffnBAL+IvWsp1kQmtkCM1fgnUwXsQVdABEBAAH+BwMCSHUdfb1G\n" +
            "2p7K36gcgLFYCF5MfTW91tPYf7NDjzVUt6MFB3OP+VBRQZzkSr4bdAEbrnCjwzOW\n" +
            "GxEFn4o9es1ZFyUSb+kUsiJEEepCCADcc4Z0zLKFRgOgmzSGaZQ3i2YksGw+mBAG\n" +
            "j96kd05LfFj2IDafRxlZ5EZWlyIKyRCnpXjzYmZtXRpeNoniFaEOBz0XjcqBOrBl\n" +
            "hT4TJ1iy4hMVx4pFz3CH0UNO8slP+Pj5rgCwFTbElgEOb/E5UiuoKznOYFajh3Mm\n" +
            "+QQyeImK4RkOuED7NtFoRLYOYnLtEM3lPvlwd/t3Sa65HldBh3xJlmSC3EG9zGXW\n" +
            "iNaaPPGJKXZt8v+EpM7jLN81LJ1NLQeIEFFtXa6s+/tgS4IIjiNOprnuO2FVHggZ\n" +
            "DHJKzLKqJs/x1TZxq0fbQS/5GAtgHPA9IW16rW42naQQKf5y8R1nnItRWBh4MSxW\n" +
            "9GycYO8ORCoEBOdH6y0pSMvFSsuTdDiFjH8OSY767twwUT2UpXHZ1sExcT4nyRYo\n" +
            "oyFQqBHWh/Bh3cq23H/x++I7K0Mc+Iu9910arZIfForGMput2liBb/k6EHPAZVgS\n" +
            "tAEEKqtJ3j04cEHS9Va2U73AUL2cv2z0G61ibHNOd9AvadKWDcOFlEW1klxBlxYY\n" +
            "E9MPcIW1zZdW7k5LfU7AkkpniyY2xeExC4tHJOu7Oh9awxKGAJ+gYUqBW9PI9BZM\n" +
            "FtWSMVzWq/0T1X/xzicZgljBvRkzOw8M666PvhGqYM3zzORlFc7+/PEwQrXEdbNq\n" +
            "KAAZo0slklRgfYY2mpqzg8uPrn/e7FuQUZ/300cRcnSUdCHsXxYQdxhoX7G4WbhN\n" +
            "Hte07IlCf/3oo9rn4JsTX1dBZVF+iX6Jkuyfg+uCe88rNnHp1P7PiX7DBgbn9eSb\n" +
            "wjJWP//H3V/JgStpkP+riQE8BBgBCAAmFiEEgDybuce6Fw4H7HfHGO1Uxgfq3ZEF\n" +
            "Al5nkA8CGwwFCQPCZwAACgkQGO1Uxgfq3ZHNqgf+NmG9r7p/VRs3HxORct1IVCbp\n" +
            "4nhWCYyQilymCeqyqDVIjLBpknymh9AdIK2Spr+/en0FfsZ+ulpmYm0Ki5LSK2PR\n" +
            "Lb8+nLO6ZNOYuz2/PhOQ4tPegf0aJ1dJkr9nitbWvDKyo61UwBWzNWr+U6mUA+qZ\n" +
            "evWD+fEDCrbPa4z8ZDQdLtgPaHPh7JrlbqYW5m+vRABri25zJ0ZdAEaQ6w8mTbjT\n" +
            "FE+Z6jRbvxoHZqaTvWqCx5e42waMSl0ktowkCAnIh2pzSML+8EtjCkkPBQB8DmpS\n" +
            "/71Ftn+HTY9ya01L7XVHWfDZ+iQyEZeEq4Yix5H4ElA1dR3Qcjz7iosxvDekJA==\n" +
            "=EuWA\n" +
            "-----END PGP PRIVATE KEY BLOCK-----\n";
}
