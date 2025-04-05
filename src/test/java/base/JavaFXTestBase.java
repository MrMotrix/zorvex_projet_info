package base;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public abstract class JavaFXTestBase {

    private static boolean initialized = false;

    @BeforeAll
    static void initToolkit() {
        if (!initialized) {
            if (!Platform.isFxApplicationThread()) {
                Platform.startup(() -> {});
            }
            initialized = true;
        }
    }
}
