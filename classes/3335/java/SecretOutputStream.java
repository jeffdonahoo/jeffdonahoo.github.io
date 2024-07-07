import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Implements an output byte stream encrypter.
 */
public class SecretOutputStream extends CipherOutputStream {

	/**
	 * Creates a new SecretOutputStream that outputs encrypted bytes using the
	 * given password to the given output stream.
	 *
	 * @param outputStream encrypted byte output stream.
	 * @param password password to use in encryption.
	 * @throws IOException if an I/O error has occurred.
	 */
	public SecretOutputStream(OutputStream outputStream, String password)
			throws IOException {
		super(outputStream, createCipher(password, true));
	}

	/**
	 * Create a cipher for use in CipherInput/OutputStream.
	 *
	 * @param password password to use for encryption or decryption.
	 * @param encrypt true if cipher should encrypt; false if cipher should decrypt.
	 * @return Encryption/Decryption cipher.
	 * @throws IOException if I/O error has occurred.
	 */
	protected static Cipher createCipher(String password, boolean encrypt)
			throws IOException {
		try {
			// Get DES encryption Cipher using ECB cipher mode and PKCS5 padding (for short files)
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			// Password must be exactly 8 bytes for DES
			password = (password + "xxxxxxxx").substring(0, 8);
			// Convert password to DES key
			DESKeySpec key = new DESKeySpec(password.getBytes());
			SecretKey sk = SecretKeyFactory.getInstance("DES").generateSecret(
					key);
			// Initialize for with encryption or decryption with derived DES key
			cipher
					.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
							sk);
			return cipher;
		} catch (Exception e) {
			throw new IOException("Unable to initialized encryption");
		}
	}

	/**
	 * Copy input to output stream and then close both.
	 *
	 * @param in byte source.
	 * @param out byte sink.
	 * @throws IOException if I/O error has occurred.
	 */
	public static void streamCopyAndClose(InputStream in, OutputStream out)
			throws IOException {
		int noRead; // Number of bytes read
		byte buf[] = new byte[256]; // Buffer to store read bytes
		while ((noRead = in.read(buf)) != -1) { // Read and then write bytes until EoS
			out.write(buf, 0, noRead);
		}
		in.close();
		out.close();
	}

	/**
	 * Demonstrate using SecretOutputStream.  Given an plaintext source
	 * file and the password, this program encrypts the source and writes the
	 * results to the specified destination file.
	 *
	 * <src. file> - name of plaintesxt source file.
	 * <dest. file> - name of encrypted destination file.
	 * <password> - password for the encryption.
	 */
	public static void main(String args[]) throws Exception {
		if (args.length != 3) {
			System.err
					.println("Parameter(s): <src. file> <dest. file> <password>");
			System.exit(1);
		}

		streamCopyAndClose(new FileInputStream(args[0]),
				new SecretOutputStream(new FileOutputStream(args[1]), args[2]));
	}
}
