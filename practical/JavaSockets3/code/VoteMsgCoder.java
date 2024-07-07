import java.io.IOException;

public interface VoteMsgCoder {
  byte[] toWire(VoteMsg msg);
  VoteMsg fromWire(byte[] input) throws IOException;
}
