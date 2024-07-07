import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * JUnit 5 Test for Employee
 * 
 * Note: This is not designed to exemplify best practices. Instead, it merely
 * demonstrates the various approaches to testing in JUnit 5. In fact, it
 * doesn't even cover all tests.
 */
@DisplayName("Employee tests")
public class EmployeeFinalTest {

    // TODO Add assume (maybe with bad email)
    private Employee emp = new Employee("Mike", 25);

    /****************************************************
     * Test setName
     ****************************************************/
    @DisplayName("Name setter")
    @Nested
    class NameSetter {
        @Test
        @DisplayName("Valid")
        void testSetNameValid() {
            String nm = "Earl";
            emp.setName(nm);
            assertEquals(nm, emp.getName());
            assertSame(nm, emp.getName());
        }

        @Test
        @DisplayName("Null")
        void testSetNameNull() {
            assertThrows(NullPointerException.class, () -> emp.setName(null));
        }
    }

    /****************************************************
     * Test setAge
     ****************************************************/
    @DisplayName("Age setter")
    @Nested
    class AgeSetter {
        @DisplayName("Good age")
        @ParameterizedTest(name = " age = {0}")
        @ValueSource(ints = { 0, 50, 150 })
        void testSetAgeValid(int age) {
            emp.setAge(age);
            assertEquals(age, emp.getAge());
        }

        @DisplayName("Illegal age")
        @ParameterizedTest(name = " age = {0}")
        @ValueSource(ints = { -1, 151, 200000 })
        public void testSetAgeInvalid(int age) {
            assertThrows(IllegalArgumentException.class, () -> emp.setAge(age));
        }
    }

    /****************************************************
     * Test parameterized constructor
     ****************************************************/
    @DisplayName("Constructor")
    @Nested
    class Constructor {
        @DisplayName("Valid")
        @ParameterizedTest(name = "name = {0} age = {1}")
        @CsvSource({ "Bob, 23", "J, 0", "Long name, 150" })
        void testConstructorValid(String name, int age) {
            emp = new Employee(name, age);
            assertAll(() -> assertEquals(name, emp.getName()), () -> assertEquals(age, emp.getAge()));
        }

        @DisplayName("Null")
        @Test
        void testConstructorNull() {
            assertThrows(NullPointerException.class, () -> new Employee(null, 20));
        }

        @DisplayName("Bad age")
        @ParameterizedTest(name = "name = {0} age = {1}")
        @CsvSource({ "Bob, -1", "'', 160" })
        void testConstructorAgeInvalid(String name, int age) {
            assertThrows(IllegalArgumentException.class, () -> new Employee(name, age));
        }

        @DisplayName("Bad values")
        @TestFactory
        Collection<DynamicTest> makeBadValuesTests() {
            return Arrays.asList(
                dynamicTest("Good name/bad age", () -> assertThrows(IllegalArgumentException.class, () -> new Employee("Bob", -5))),
                dynamicTest("Null name/bad age", () -> assertThrows(NullPointerException.class, () -> new Employee(null, 5)))
            );
        }
        
        @DisplayName("All values")
        @TestFactory
        Stream<DynamicTest> makeAllValuesTests() {
            List<String> goodNames = Arrays.asList("Bob", "",
                    Stream.generate(() -> "x1z").limit(50).collect(joining()));
            List<Integer> goodAges = Arrays.asList(0, 23, 150);
            List<Integer> badAges = Arrays.asList(-1, 151);

            // Good names and good ages
            Stream<DynamicTest> tests = goodNames.stream().flatMap(name -> goodAges.stream()
                    .map(age -> dynamicTest("Good name/age name=" + name + " age=" + age, () -> {
                        emp = new Employee(name, age);
                        assertAll(() -> assertEquals(name, emp.getName()),
                                () -> assertEquals(age.intValue(), emp.getAge()));
                    })));
            // Null name and good ages
            tests = Stream.concat(tests, goodAges.stream()
                    .map(age -> dynamicTest("Null name/good age name=null age=" + age, () -> {
                        assertThrows(NullPointerException.class, () -> new Employee(null, age));
                    })));

            // Good names and bad ages
            tests = Stream.concat(tests, goodNames.stream().flatMap(name -> badAges.stream()
                    .map(age -> dynamicTest("Good name/bad age name=" + name + " age=" + age, () -> {
                        assertThrows(IllegalArgumentException.class, () -> new Employee(name, age));
                    }))));

            // Null name and bad ages
            tests = Stream.concat(tests, badAges.stream()
                    .map(age -> dynamicTest("Null name/bad age name=null age=" + age, () -> {
                        assertThrows(Exception.class, () -> new Employee(null, age));
                    })));

            return tests;
        }
    }

    /****************************************************
     * Test email setter
     ****************************************************/
    @DisplayName("Email setter")
    @Nested
    class EmailSetter {
        @DisplayName("Good email")
        @ParameterizedTest(name = "email = {0}")
        // @MethodSource("EmployeeFinalTest#getGoodEmails")
        @ArgumentsSource(GoodEmailsFactory.class)
        void testAddGoodEmail(List<String> emailList) {
            emailList.stream().forEach(e -> emp.addEmail(e));
            List<String> empEmails = emp.getEmail();
            Collections.sort(emailList);
            Collections.sort(empEmails);
            assertIterableEquals(emailList, empEmails);
        }

        @DisplayName("Bad email")
        @ParameterizedTest(name = "email = {0} where {1} is bad")
        // @MethodSource("EmployeeFinalTest#getBadEmails")
        @ArgumentsSource(BadEmailsFactory.class)
        void testAddBadEmail(List<String> emailList, int badIdx) {
            for (int i = 0; i < emailList.size(); i++) {
                String newEmail = emailList.get(i);
                if (badIdx == i) {
                    Throwable ex = assertThrows(IllegalArgumentException.class, () -> emp.addEmail(newEmail));
                    assertTrue(ex.getMessage().contains(newEmail));
                } else {
                    emp.addEmail(newEmail);
                }
            }
            assertEquals(emailList.size() - 1, emp.getEmail().size());
        }
    }

    static Stream<List<String>> getGoodEmails() {
        return Stream.<List<String>>of(Arrays.asList("bob@bob.com"), Arrays.asList("jane@jane.com", "jack@jack.com"));
    }

    static Stream<Arguments> getBadEmails() {
        return Stream.of(Arguments.of(Arrays.asList("jane@jane.com", "jackjack.com"), 1),
                Arguments.of(Arrays.asList("jane@jane.com", "jackjack.com", "fred@free.com"), 1));
    }

    static class GoodEmailsFactory implements ArgumentsProvider {
        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(Arrays.asList("bob@bob.com"), Arrays.asList("jane@jane.com", "jack@jack.com"))
                    .map(Arguments::of);
        }
    }

    static class BadEmailsFactory implements ArgumentsProvider {
        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(Arguments.of(Arrays.asList("jane@jane.com", "jackjack.com"), 1),
                    Arguments.of(Arrays.asList("jane@jane.com", "jackjack.com", "fred@free.com"), 1));
        }
    }

    @BeforeEach
    public void setUpTest() {
        // Executes before EACH test
        System.out.println("Before Each");
    }

    @AfterEach
    public void tearDownTest() {
        // Executes after EACH test
        System.out.println("After Each");
    }

    @BeforeAll
    public static void setUpAllTests() {
        // Executes once before ALL tests
        System.out.println("Before All");
    }

    @AfterAll
    public static void tearDownAllTests() {
        // Executes once after ALL tests
        System.out.println("After All");
    }
}
