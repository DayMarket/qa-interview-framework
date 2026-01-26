package utils.common;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class RandomUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static int randomInt(int boundExclusive) {
        if (boundExclusive <= 0) {
            throw new IllegalArgumentException("boundExclusive must be positive");
        }
        return RANDOM.nextInt(boundExclusive);
    }

    public static <T> T randomElementFromList(List<T> items) {
        Objects.requireNonNull(items, "items must not be null");
        if (items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }
        return items.get(RANDOM.nextInt(items.size()));
    }
}
