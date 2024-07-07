public class VoteMsg {
  private boolean isInquiry; // true if inquiry; false if vote
  private boolean isResponse;// true if response
  private int candidateID;   // in [0,1000]
  private long voteCount;    // nonzero only in response

  public static final int MIN_CANDIDATE_ID = 0;
  public static final int MAX_CANDIDATE_ID = 1000;

  public VoteMsg(boolean isResponse, boolean isInquiry, int candidateID, long voteCount)
      throws IllegalArgumentException {
	setResponse(isResponse);  // Must set before vote count
	setVoteCount(voteCount);
	setCandidateID(candidateID);
	setInquiry(isInquiry);
  }

  public final void setInquiry(boolean isInquiry) {
    this.isInquiry = isInquiry;
  }

  public boolean isInquiry() {
    return isInquiry;
  }
  
  public final void setResponse(boolean isResponse) {
    this.isResponse = isResponse;
  }

  public boolean isResponse() {
    return isResponse;
  }

  public final void setCandidateID(int candidateID) throws IllegalArgumentException {
    if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
      throw new IllegalArgumentException("Bad Candidate ID: " + candidateID);
    }
    this.candidateID = candidateID;
  }

  public int getCandidateID() {
    return candidateID;
  }

  public final void setVoteCount(long count) {
    if (count < 0 || (count != 0 && !isResponse)) {
      throw new IllegalArgumentException("Bad vote count");
    }
    voteCount = count;
  }

  public long getVoteCount() {
    return voteCount;
  }

  @Override
  public String toString() {
    String res = (isInquiry ? "inquiry" : "vote") + " for candidate " + candidateID;
    if (isResponse) {
      res = "response to " + res + " who now has " + voteCount + " vote(s)";
    }
    return res;
  }
  // You should have hashCode and equals; left out for space
}
