import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/* Wire Format
 *                                1  1  1  1  1  1
 *  0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |     Magic       | R| I|       ZERO            |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                  Candidate ID                 |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                                               |
 * |         Vote Count (only in response)         |
 * |                                               |
 * |                                               |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 */
public class VoteMsgBinCoder implements VoteMsgCoder {
	// (De)Serialization constants
	private static final int CAND_LENGTH = 2;
	private static final int VOTE_LENGTH = 8;
	private static final int MAGIC = 0b01010100;
	private static final int MAGIC_MASK = 0b11111100;
	private static final int RESPONSE_FLAG = 0b00000010;
	private static final int INQUIRE_FLAG = 0b00000001;

	@Override
	public byte[] toWire(final VoteMsg msg) {
		Objects.requireNonNull(msg, "msg cannot be null");
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte magicAndFlags = MAGIC;
		if (msg.isInquiry()) {
			magicAndFlags |= INQUIRE_FLAG;
		}
		if (msg.isResponse()) {
			magicAndFlags |= RESPONSE_FLAG;
		}
		out.write(magicAndFlags);
		out.write(0);
		byte[] encCandID = new byte[CAND_LENGTH];
		BruteForceCoding.encodeIntBigEndian(encCandID, msg.getCandidateID(), 0, CAND_LENGTH);
		out.writeBytes(encCandID);
		if (msg.isResponse()) {
			byte[] encVoteCt = new byte[VOTE_LENGTH];
			BruteForceCoding.encodeIntBigEndian(encVoteCt, msg.getVoteCount(), 0, VOTE_LENGTH);
			out.writeBytes(encVoteCt);
		}
		return out.toByteArray();
	}

	@Override
	public VoteMsg fromWire(byte[] input) throws IOException {
		Objects.requireNonNull(input, "input byte array cannot be null");
		ByteArrayInputStream in = new ByteArrayInputStream(input);
		
		byte[] magicFlag = readFully(in, 1);
		if ((magicFlag[0] & MAGIC_MASK) != MAGIC) {
			throw new IOException("Bad Magic #: " + (magicFlag[0] & MAGIC_MASK));
		}
		boolean resp = ((magicFlag[0] & RESPONSE_FLAG) != 0);
		boolean inq = ((magicFlag[0] & INQUIRE_FLAG) != 0);
		// Read and verify ZERO
		if (readFully(in, 1)[0] != 0) {
			throw new IOException("ZERO must be 0");
		}
		int candidateID = (int) BruteForceCoding.decodeIntBigEndian(readFully(in, CAND_LENGTH), 0, CAND_LENGTH);
		long count = (resp) ? BruteForceCoding.decodeIntBigEndian(readFully(in, VOTE_LENGTH), 0, VOTE_LENGTH) : 0;
		if (in.read() != -1) {
			throw new IOException("Extra bytes detected");
		}
		return new VoteMsg(resp, inq, candidateID, count);
	}
	
	private static byte[] readFully(InputStream in, int len) throws IOException {
		byte[] rv = in.readNBytes(len);
		if (rv.length != len) {
			throw new EOFException("EoS on input stream");
		}
		return rv;
	}
}
