import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class VoteMsgTextCoder implements VoteMsgCoder {
	/*
	 * Wire Format
	 * MSG = <MAGIC><sp><TYPE><sp><BODY>
	 * <MAGIC> = "Voting"
	 * <TYPE> = "v" | "i"
	 * <BODY> = <RESPONSE> | <REQUEST>
	 * <RESPONSE> = "R"<sp><CANDIDATE><VOTECNT>
	 * <REQUEST> = <CANDIDATE>
	 * <CANDIDATE> = <CANDIDATEID><sp>
	 * <CANDIDATEID> = [0-9]+  // [0..1000]
	 * <VOTECNT> = [0-9]+  // [0..2^31]
	 * 
	 * Character encoding is fixed by the wire format.
	 */

	// (De)serialization constants
	public static final String MAGIC = "Voting";
	public static final String VOTESTR = "v";
	public static final String INQSTR = "i";
	public static final String RESPONSESTR = "R";

	public static final Charset CHARSET = StandardCharsets.US_ASCII;
	public static final String DELIMSTR = " ";
	public static final int MAX_WIRE_LENGTH = 2000;

	@Override
	public byte[] toWire(VoteMsg msg) {
		Objects.requireNonNull(msg, "msg cannot be null");
		String msgString = MAGIC + DELIMSTR + (msg.isInquiry() ? INQSTR : VOTESTR) + DELIMSTR
				+ (msg.isResponse() ? RESPONSESTR + DELIMSTR : "") + msg.getCandidateID() + DELIMSTR
				+ (msg.isResponse() ? msg.getVoteCount() : "");

		return msgString.getBytes(CHARSET);
	}

	@Override
	public VoteMsg fromWire(byte[] input) throws IOException {
		Objects.requireNonNull(input, "input byte array cannot be null");
		try (Scanner s = new Scanner(new ByteArrayInputStream(input), CHARSET)) {
			String token = s.next();
			if (!MAGIC.equals(token)) {
				throw new IOException("Bad magic string: " + token);
			}

			token = s.next();
			// Not sure why this doesn't work
//			boolean isInquiry = switch (token) {
//			case VOTESTR -> false;
//			case INQSTR -> true;
//			default -> throw new IOException("Bad vote/inq indicator");
//			};
			// Temporary fix.
			boolean isInquiry;
			if (token.equals(VOTESTR)) {
				isInquiry = false;
			} else if (!token.equals(INQSTR)) {
				throw new IOException("Bad vote/inq indicator: " + token);
			} else {
				isInquiry = true;
			}


			token = s.next();
			boolean isResponse;
			if (RESPONSESTR.equals(token)) {
				isResponse = true;
				token = s.next();
			} else {
				isResponse = false;
			}
			// Current token is candidateID
			// Note: isResponse now valid
			int candidateID = Integer.parseInt(token);
			long voteCount = 0;
			if (isResponse) {
				token = s.next();
				voteCount = Long.parseLong(token);
			}

			return new VoteMsg(isResponse, isInquiry, candidateID, voteCount);
		} catch (NoSuchElementException e) {
			throw new EOFException("Voting serialization too short");
		}
	}
}
