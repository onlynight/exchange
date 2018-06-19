import io.github.onlynight.exchange.typed.text.plugin.sdk.doc.DocumentIOS;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DocumentIOSTest {

    @Test
    public void testParseDoc() {
        File file = new File(new File("").getAbsolutePath(), "Localizable.strings");
        String absPath = file.getAbsolutePath();
        DocumentIOS doc = new DocumentIOS(absPath).parse();
        List<DocumentIOS.IOSStringLine> lines = doc.getLines();
        System.out.println(lines);
    }

}