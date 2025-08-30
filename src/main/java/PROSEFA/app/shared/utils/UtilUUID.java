package PROSEFA.app.shared.utils;

import java.util.Optional;
import java.util.UUID;

public class UtilUUID {

    public static Optional<UUID> parseUuid(String ref) {
        try {
            return Optional.of(UUID.fromString(ref));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
