import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class VoteMsgBin extends VoteMsg {
 /* Wire Format
  *   ------------------------------------
  *   | FLAGS/Magic (1) |  Candidate (3)  | - low-order 12 bits only
  *   ------------------------------------
  *   |    Vote Count (8)                 | - included only in response
  *   ------------------------------------
  */

  /* manifest constants for encoding */
  public static final int MIN_WIRE_LENGTH = 4;
  public static final int MAGIC = 0x54000000;
  public static final int MAGIC_MASK = 0xf6000000;
  public static final int MAGIC_SHIFT = 24;
  public static final int CANDIDATE_MASK = 0x03ff;  // low 10 bits - enough
  public static final int RESPONSE_FLAG = 0x02000000;
  public static final int INQUIRE_FLAG = 0x01000000;

  public VoteMsgBin(boolean response, boolean inquireOnly,
		 int candidate, long voteCount)
    throws IllegalArgumentException {
    super(response,inquireOnly,candidate,voteCount);
  }

  public byte[] toWire() throws IOException {
    ByteArrayOutputStream bs = new ByteArrayOutputStream(); // holds message
    DataOutputStream out = new DataOutputStream(bs); // handles int cnvrsn

    int firstWord = this.candidateID;  // 0 <= candidate <= 1000
    firstWord |= MAGIC;
    if (isInquiry) {
      firstWord |= INQUIRE_FLAG;
    }
    if (isResponse) {
      firstWord |= RESPONSE_FLAG;
    }
    out.writeInt(firstWord);
    if (isResponse) {
      out.writeLong(voteCount);
    }
    out.flush();
    return bs.toByteArray();
  }

  /* parse a received message and return a new instance;
   * If any of the invariants are violated, throw an Exception
   */
  public static VoteMsg fromWire(byte[] input) throws IOException {
    boolean response;
    boolean inquiry;
    /* sanity checks */
    if (input.length < MIN_WIRE_LENGTH) {
      throw new MessageFormatException("runt message");
    }
    ByteArrayInputStream bs = new ByteArrayInputStream(input);
    DataInputStream in = new DataInputStream(bs);
    int firstWord = in.readInt();
    if ((firstWord & MAGIC_MASK) != MAGIC) {
      throw new MessageFormatException("bad Magic #: " +
	       ((firstWord & MAGIC_MASK)>>MAGIC_SHIFT));
    }
    boolean resp = ((firstWord & RESPONSE_FLAG) != 0);
    boolean inq = ((firstWord & INQUIRE_FLAG) != 0);
    int candidateID = firstWord & CANDIDATE_MASK;
    long count = 0;
    if (resp) {
      count = in.readLong();
      if (count < 0)
	throw new MessageFormatException("bad vote count: " + count);
    }
    return new VoteMsgBin(resp,inq,candidateID,count);
  }

}
