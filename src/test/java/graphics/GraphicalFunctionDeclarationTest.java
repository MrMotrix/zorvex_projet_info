package graphics;

import graphics.GraphicalArray;
import graphics.GraphicalFunctionDeclaration;
import interpreter.instruction.FunctionDeclaration;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import base.JavaFXTestBase;

public class GraphicalFunctionDeclarationTest extends JavaFXTestBase{

        private GraphicalFunctionDeclaration functionDeclaration;
        private Pane pane;

    // @BeforeAll
    // static void initToolkit() {
    //     if (!Platform.isFxApplicationThread()) {
    //         Platform.startup(() -> {});
    //     }
    // }

    @BeforeEach
    public void setUp() {
        pane = new Pane(); // Create a new Pane for each test
        functionDeclaration = new GraphicalFunctionDeclaration("myFunctionDeclaration",pane, List.of("param1, param2, param3"), "var1, var2, var3", 0);
    }

    @Test
    void testDraw(){
        functionDeclaration.draw(100, 100);
        assertEquals(functionDeclaration.getRenderedNodes().size(),4);

    }

    @Test
    void testNullVars(){
        functionDeclaration = new GraphicalFunctionDeclaration("myFunctionDeclaration",pane, List.of("param1, param2, param3"), null, 0);
        functionDeclaration.draw(100, 100);
        Text text = (Text) functionDeclaration.getRenderedNodes().get(3);
        assertEquals(text.getText(),"Paramétres : param1, param2, param3");

    }

    @Test
    void testVarsAndParameters(){
        functionDeclaration.draw(100, 100);
        Text text = (Text) functionDeclaration.getRenderedNodes().get(3);
        assertEquals(text.getText(),"Paramétres : param1, param2, param3\nVars: var1, var2, var3");

    }
}
