import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Test for text coder for voting protocol. Test is intended as an example of
 * serialization testing techniques, not an example of complete testing or
 * proper commenting.
 */
@DisplayName("VoteMsgTextCoder")
public class VoteMsgTextCoderTest {

	public static final Charset CHARSET = StandardCharsets.US_ASCII;

	@DisplayName("deserialization")
	@Nested
	class Decode {
		@DisplayName("valid")
		@ParameterizedTest(name = "isResponse = {0} isInquiry = {1} candidateID = {2} voteCount = {3} enc = {4}")
		@ArgumentsSource(ValidVote.class)
		void testValid(boolean isResponse, boolean isInquiry, int candidateID, long voteCount, byte[] enc)
				throws IllegalArgumentException, IOException {
			VoteMsg msg = new VoteMsgTextCoder().fromWire(enc);
			VoteMsgTest.verifyExpected(msg, isResponse, isInquiry, candidateID, voteCount);
		}

		@DisplayName("null")
		@Test
		void testNull() {
			VoteMsgCoder coder = new VoteMsgTextCoder();
			Throwable ex = assertThrows(NullPointerException.class, () -> coder.fromWire(null));
			assertNotNull(ex.getMessage(), "Exception message cannot be null");
			assertTrue(!ex.getMessage().isEmpty(), "Exception message cannot be empty");
		}

		@DisplayName("invalid")
		@ParameterizedTest(name = "enc = {0} class = {1}")
		@ArgumentsSource(InvalidVoteEncode.class)
		void testInvalid(byte[] enc, Class<? extends Throwable> cl) throws IllegalArgumentException, IOException {
			Throwable ex = assertThrows(cl, () -> new VoteMsgTextCoder().fromWire(enc));
			if (!(ex instanceof EOFException)) { // Java throws EOFException w/o message. Boo!!
				assertNotNull(ex.getMessage(), "Exception message cannot be null");
				assertTrue(!ex.getMessage().isEmpty(), "Exception message cannot be empty");
			}
		}
	}

	@DisplayName("serialization")
	@Nested
	class Encode {
		@DisplayName("valid")
		@ParameterizedTest(name = "isResponse = {0} isInquiry = {1} candidateID = {2} voteCount = {3} enc = {4}")
		@ArgumentsSource(ValidVote.class)
		void testValid(boolean isResponse, boolean isInquiry, int candidateID, long voteCount, byte[] enc)
				throws IOException {
			VoteMsg msg = new VoteMsg(isResponse, isInquiry, candidateID, voteCount);
			assertArrayEquals(enc, new VoteMsgTextCoder().toWire(msg));
		}

		@DisplayName("null")
		@Test
		void testNull() {
			Throwable ex = assertThrows(NullPointerException.class, () -> new VoteMsgTextCoder().toWire(null));
			assertNotNull(ex.getMessage(), "Exception message cannot be null");
			assertTrue(!ex.getMessage().isEmpty(), "Exception message cannot be empty");
		}
	}

	/**
	 * isResponse(boolean), isInquiry(boolean), candidateID(int), voteCount(long),
	 * enc(byte[])
	 */
	static class ValidVote implements ArgumentsProvider {
		@Override
		public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
			List<Arguments> list = new ArrayList<>();
			// Normal
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					5, // candidateID
					100, // voteCount
					"Voting i R 5 100".getBytes(CHARSET)));
			list.add(Arguments.of(false, // isResponse
					true, // isInquiry
					5, // candidateID
					0, // voteCount
					"Voting i 5 ".getBytes(CHARSET)));
			list.add(Arguments.of(true, // isResponse
					false, // isInquiry
					5, // candidateID
					100, // voteCount
					"Voting v R 5 100".getBytes(CHARSET)));
			list.add(Arguments.of(false, // isResponse
					false, // isInquiry
					5, // candidateID
					0, // voteCount
					"Voting v 5 ".getBytes(CHARSET)));
			// Large candidate ID
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					1000, // candidateID
					100, // voteCount
					"Voting i R 1000 100".getBytes(CHARSET)));
			// Small candidate ID
			list.add(Arguments.of(false, // isResponse
					true, // isInquiry
					0, // candidateID
					0, // voteCount
					"Voting i 0 ".getBytes(CHARSET)));
			// Large vote count
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					5, // candidateID
					Long.MAX_VALUE, // voteCount
					"Voting i R 5 9223372036854775807".getBytes(CHARSET)));

			return list.stream();
		}
	}

	/**
	 * enc(byte[]), exception (Class)
	 */
	static class InvalidVoteEncode implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of(
					// Bad Protocol (expect IOException)
					Arguments.of("Votingg i R 1000 100".getBytes(CHARSET), IOException.class),
					// Only magic (expect EOFException)
					Arguments.of("Voting".getBytes(CHARSET), EOFException.class),
					// Candidate ID too big
					Arguments.of("Voting i R 1001 100".getBytes(CHARSET), IllegalArgumentException.class),
					// No vote count with response
					Arguments.of("Voting i R 1000 ".getBytes(CHARSET), EOFException.class));
		}
	}
}
