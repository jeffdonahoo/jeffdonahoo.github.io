import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
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
 * Test for binary coder for voting protocol.
 * Test is intended as an example of serialization testing
 * techniques, not an example of complete testing or proper
 * commenting.
 */
@DisplayName("VoteMsgBinCoder")
public class VoteMsgBinCoderTest {

	@DisplayName("deserialization")
	@Nested
	class Decode {
		@DisplayName("valid")
		@ParameterizedTest(name = "isResponse = {0} isInquiry = {1} candidateID = {2} voteCount = {3} enc = {4}")
		@ArgumentsSource(ValidVote.class)
		void testValid(boolean isResponse, boolean isInquiry, int candidateID, long voteCount, byte[] enc)
				throws IllegalArgumentException, IOException {
			VoteMsg msg = new VoteMsgBinCoder().fromWire(enc);
			VoteMsgTest.verifyExpected(msg, isResponse, isInquiry, candidateID, voteCount);
		}

		@DisplayName("null")
		@Test
		void testNull() {
			Throwable ex = assertThrows(NullPointerException.class, () -> new VoteMsgBinCoder().fromWire(null));
			assertNotNull(ex.getMessage(), "Exception message cannot be null");
			assertTrue(!ex.getMessage().isEmpty(), "Exception message cannot be empty");
		}

		@DisplayName("invalid")
		@ParameterizedTest(name = "enc = {0} class = {1}")
		@ArgumentsSource(InvalidVoteEncode.class)
		void testInvalid(byte[] enc, Class<? extends Throwable> cl) throws IllegalArgumentException, IOException {
			Throwable ex = assertThrows(cl, () -> new VoteMsgBinCoder().fromWire(enc));
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
			assertArrayEquals(enc, new VoteMsgBinCoder().toWire(msg));
		}

		@DisplayName("null")
		@Test
		void testNull() {
			Throwable ex = assertThrows(NullPointerException.class, () -> new VoteMsgBinCoder().toWire(null));
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
					new byte[] { 0b01010111, // Magic + flags
							0, // ZERO
							0, 5, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64 // Vote Count
					}));
			list.add(Arguments.of(false, // isResponse
					true, // isInquiry
					5, // candidateID
					0, // voteCount
					new byte[] { 0b01010101, // Magic + flags
							0, // ZERO
							0, 5 // Candidate ID
					}));
			list.add(Arguments.of(true, // isResponse
					false, // isInquiry
					5, // candidateID
					100, // voteCount
					new byte[] { 0b01010110, // Magic + flags
							0, // ZERO
							0, 5, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64 // Vote Count
					}));
			list.add(Arguments.of(false, // isResponse
					false, // isInquiry
					5, // candidateID
					0, // voteCount
					new byte[] { 0b01010100, // Magic + flags
							0, // ZERO
							0, 5 // Candidate ID
					}));
			// Large candidate ID
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					1000, // candidateID
					100, // voteCount
					new byte[] { 0b01010111, // Magic + flags
							0, // ZERO
							0x3, (byte) 0xE8, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64 // Vote Count
					}));
			// Small candidate ID
			list.add(Arguments.of(false, // isResponse
					true, // isInquiry
					0, // candidateID
					0, // voteCount
					new byte[] { 0b01010101, // Magic + flags
							0, // ZERO
							0, 0 // Candidate ID
					}));
			// Large vote count
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					5, // candidateID
					Long.MAX_VALUE, // voteCount
					new byte[] { 0b01010111, // Magic + flags
							0, // ZERO
							0, 5, // Candidate ID
							(byte) 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
							(byte) 0xFF // Vote Count
					}));

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
					// Bad Magic (expect IOException)
					Arguments.of(new byte[] { 0b01011111, // Magic + flags
							0, // ZERO
							0, 5, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64 // Vote Count
					}, IOException.class),
					// Only magic (expect EOFException)
					Arguments.of(new byte[] { 0b01010111, // Magic + flags
					}, EOFException.class),
					// Non-zero ZERO
					Arguments.of(new byte[] { 0b01010111, // Magic + flags
							1, // ZERO
							0, 5, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64 // Vote Count
					}, IOException.class),
					// Candidate ID too big
					Arguments.of(new byte[] { 0b01010111, // Magic + flags
							0, // ZERO
							0x3, (byte) 0xE9, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64 // Vote Count
					}, IllegalArgumentException.class),
					// No vote count with response
					Arguments.of(new byte[] { 0b01010111, // Magic + flags
							0, // ZERO
							0, 5, // Candidate ID
					}, EOFException.class),
					// Too long
					Arguments.of(new byte[] { 0b01010111, // Magic + flags
							0, // ZERO
							0, 5, // Candidate ID
							0, 0, 0, 0, 0, 0, 0, 0x64, // Vote Count
							5  // Extra (bad) bytes
					}, IOException.class)
					);
		}
	}
}
