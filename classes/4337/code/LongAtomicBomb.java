public class LongAtomicBomb {
    private static final int NOASSIGNS = 10000000;
    private static final int NOTHREADS = 10;
    private static final int HALFLONG = Long.SIZE / 2;

    private static long sharedLong;

    /**
     * Return a long value where the most and least significant bytes
     * are value
     * 
     * @param value value to assign to the most and least significant bytes
     * @return final value
     */
    public static long createValue(long value) {
        return (value << HALFLONG) | value;
    }
    
    /**
     * Verify that the most and least significant bytes are equal
     * 
     * @param value value to test the most and least significant bytes
     * for equality
     */
    public static void verifyValue(long value) {
        if (value >>> HALFLONG != (value << HALFLONG) >>> HALFLONG) {
            System.err.println("Test failed");
            System.exit(1);
        }
    }

    public static void main(String args[]) {
        // Create NOTHREADS threads
        for (int i = 0; i < NOTHREADS; i++) {
            final long x = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long localLong = createValue(x);  // Make local value
                    // Perform NOASSIGNS assignments of local to shared long
                    for (int i = 0; i < NOASSIGNS; i++) {
                        sharedLong = localLong;
                        verifyValue(sharedLong);  // VERIFY atomicity of assignment
                    }
                }
            }).start();
        }
    }
}
