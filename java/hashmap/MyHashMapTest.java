import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class MyHashMapTest {
    private record Key(int value) {
        @Override public int hashCode() { return value % 3; }
    }

    private static void check(boolean condition) {
        if (!condition) throw new AssertionError();
    }

    public static void main(String[] args) {
        MyHashMap<Key, Integer> actual = new MyHashMap<>();
        HashMap<Key, Integer> expected = new HashMap<>();
        Random random = new Random(42);
        for (int i = 0; i < 20000; i++) {
            Key key = random.nextInt(20) == 0 ? null : new Key(random.nextInt(200));
            Integer value = random.nextInt(5) == 0 ? null : random.nextInt();
            switch (random.nextInt(4)) {
                case 0 -> check(Objects.equals(actual.put(key, value), expected.put(key, value)));
                case 1 -> check(Objects.equals(actual.remove(key), expected.remove(key)));
                case 2 -> check(Objects.equals(actual.get(key), expected.get(key)));
                default -> check(actual.containsKey(key) == expected.containsKey(key));
            }
            check(actual.size() == expected.size());
        }
        for (Key key : expected.keySet()) {
            check(actual.containsKey(key));
            check(Objects.equals(actual.get(key), expected.get(key)));
        }
        actual.clear();
        check(actual.isEmpty() && actual.size() == 0 && !actual.containsKey(null));
        actual.put(null, null);
        check(actual.containsKey(null));
        System.out.println("MyHashMap checks passed");
    }
}

