import java.io.*;
import java.util.Scanner;

public class VoteMsgText extends VoteMsg {
 /* Wire Format
  *   "VOTEPROTO" <VOTEORINQ> [<RESPFLAG>] <CANDIDATE> [<VOTECOUNT>]
  * Charset is fixed by the wire format
  */

 /* manifest constants for encoding */
  public static final String MAGIC = "Voting";
  public static final String VOTESTR = "v";
  public static final String INQSTR  = "i";
  public static final String RESPONSESTR = "R";

  public static final String CHARSETNAME = "US-ASCII";
  public static final String DELIMSTR = " ";  // isWhitespace()

  public VoteMsgText(boolean response, boolean inquireOnly,
		     int candidate, long voteCount)
    throws Exception {
    super(response,inquireOnly,candidate,voteCount);
  }

  public byte[] toWire() throws IOException {
    String msgString = MAGIC + DELIMSTR + (isInquiry ? INQSTR : VOTESTR)
      + DELIMSTR + (isResponse ? RESPONSESTR + DELIMSTR : "")
      + Integer.toString(candidateID) + DELIMSTR + Long.toString(voteCount);
    return msgString.getBytes(CHARSETNAME);
  }

  /* Note: byte[] input (instead of stream) forces framing
   * to be done separately.
   */
  public static VoteMsgText fromWire(byte[] b) throws Exception {
    ByteArrayInputStream ba = new ByteArrayInputStream(b);
    Scanner s = new Scanner(new InputStreamReader(ba,CHARSETNAME));
    boolean isInquiry;
    boolean isResponse;
    int candidateID;
    long voteCount;
    String token;

    try {
      token = s.next();
      if (!token.equals(MAGIC)) {
	throw new MessageFormatException("Bad Magic string: " + token);
      }
      token = s.next();
      if (token.equals(VOTESTR)) {
	isInquiry = false;
      } else if (!token.equals(INQSTR)) {
	throw new MessageFormatException("Bad Vote/Inq indicator: " + token);
      } else {
	isInquiry = true;
      }	       

      token = s.next();
      if (token.equals(RESPONSESTR)) {
	isResponse = true;
	token = s.next();
      } else {
	isResponse = false;
      }
      // current token is candidateID
      // note: isResponse now valid
      candidateID = Integer.parseInt(token);
      if (isResponse) {
	token = s.next();
	voteCount = Long.parseLong(token);
      } else {
	voteCount = 0;
      }
    } catch (IOException ioe) {
      throw new MessageFormatException("Parse error...");
    }
    return new VoteMsgText(isResponse,isInquiry,candidateID,voteCount);
  }
}
