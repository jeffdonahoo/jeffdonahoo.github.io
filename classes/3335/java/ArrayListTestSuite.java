import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = { ArrayListBasicTest.class, ArrayListParameterizedTest.class })
public class ArrayListTestSuite {
}
