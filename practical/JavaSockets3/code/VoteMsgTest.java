import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("VoteMsg")
public class VoteMsgTest {

	@DisplayName("constructor")
	@Nested
	class AttributeConstructor {
		@DisplayName("valid")
		@ParameterizedTest(name = "isResponse = {0} isInquiry = {1} candidateID = {2} voteCount = {3}")
		@ArgumentsSource(ValidVote.class)
		void testValid(boolean isResponse, boolean isInquiry, int candidateID, long voteCount)
				throws IllegalArgumentException {
			verifyExpected(new VoteMsg(isResponse, isInquiry, candidateID, voteCount), isResponse, isInquiry,
					candidateID, voteCount);
		}

		@DisplayName("invalid")
		@ParameterizedTest(name = "message = {0} isResponse = {1} isInquiry = {2} candidateID = {3} voteCount = {4}")
		@ArgumentsSource(InvalidVote.class)
		void testInvalid(String message, boolean isResponse, boolean isInquiry, int candidateID, long voteCount)
				throws IllegalArgumentException {
			Throwable ex = assertThrows(IllegalArgumentException.class,
					() -> new VoteMsg(isResponse, isInquiry, candidateID, voteCount), message);
			assertTrue(ex != null && !ex.getMessage().isBlank(), "Exception message may not be null or blank");
		}
	}

	@DisplayName("setter")
	@Nested
	class Setter {
		boolean defResp = false;
		boolean defInq = false;
		int defCanID = 5;
		long defVote = 0;
		VoteMsg v = new VoteMsg(defResp, defInq, defCanID, defVote);

		@DisplayName("valid response/vote count")
		@ParameterizedTest(name = "isResponse = {0} voteCount = {1}")
		@CsvSource({ "true, 0", "true, 1000", "false, 0" })
		void testValidResponse_VoteCount(boolean isResponse, long voteCount) throws IllegalArgumentException {
			v.setResponse(isResponse);
			v.setVoteCount(voteCount);
			verifyExpected(v, isResponse, defInq, defCanID, voteCount);
		}

		@DisplayName("invalid response/vote count")
		@ParameterizedTest(name = "isResponse = {0} voteCount = {1}")
		@CsvSource({ "false, 100", "false, -1" })
		void testInvalidResponse_VoteCount(boolean isResponse, long voteCount) {
			assertThrows(IllegalArgumentException.class, () -> {
				v.setResponse(isResponse);
				v.setVoteCount(voteCount);
			});
		}

		@DisplayName("valid candidate ID")
		@ParameterizedTest(name = "candidateID = {0}")
		@ValueSource(ints = { 0, 100, 1000 })
		void testValid(int candidateID) throws IllegalArgumentException {
			v.setCandidateID(candidateID);
			verifyExpected(v, defResp, defInq, candidateID, defVote);
		}

		@DisplayName("invalid candidate ID")
		@ParameterizedTest(name = "candidateID = {0}")
		@ValueSource(ints = { -1, 1001, -2147483648, 2147483647 })
		void testInvalidCandidateID(int candidateID) {
			assertThrows(IllegalArgumentException.class, () -> {
				v.setCandidateID(candidateID);
			});
		}

		@DisplayName("valid inquiry")
		@Test
		void testValidInquiry() {
			v.setInquiry(!defInq);
			verifyExpected(v, defResp, !defInq, defCanID, defVote);
		}
	}

	// Should also test toString, hashCode, and equals

	public static void verifyExpected(VoteMsg voteMsg, boolean isResponse, boolean isInquiry, int candidateID,
			long voteCount) {
		assertEquals(isResponse, voteMsg.isResponse());
		assertEquals(isInquiry, voteMsg.isInquiry());
		assertEquals(candidateID, voteMsg.getCandidateID());
		assertEquals(voteCount, voteMsg.getVoteCount());
	}

	/**
	 * isResponse(boolean), isInquiry(boolean), candidateID(int), voteCount(long)
	 */
	static class ValidVote implements ArgumentsProvider {
		@Override
		public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
			List<Arguments> list = new ArrayList<>();
			// Normal
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					5, // candidateID
					100 // voteCount
					));
			list.add(Arguments.of(false, // isResponse
					true, // isInquiry
					5, // candidateID
					0 // voteCount
					));
			list.add(Arguments.of(true, // isResponse
					false, // isInquiry
					5, // candidateID
					100 // voteCount
					));
			list.add(Arguments.of(false, // isResponse
					false, // isInquiry
					5, // candidateID
					0 // voteCount
					));
			// Large candidate ID
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					1000, // candidateID
					100 // voteCount
					));
			// Small candidate ID
			list.add(Arguments.of(false, // isResponse
					true, // isInquiry
					0, // candidateID
					0 // voteCount
					));
			// Large vote count
			list.add(Arguments.of(true, // isResponse
					true, // isInquiry
					5, // candidateID
					Long.MAX_VALUE // voteCount
					));

			return list.stream();
		}
	}

	/**
	 * message (String), isResponse(boolean), isInquiry(boolean), candidateID(int),
	 * voteCount(long)
	 */
	static class InvalidVote implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(Arguments.of("Negative candidateID", true, true, -1, 10),
					Arguments.of("CandidateID too big", true, true, 1001, 10),
					Arguments.of("Negative vote count", true, true, 5, -1),
					Arguments.of("Request with positive vote count", false, true, 5, 10));
		}
	}
}
