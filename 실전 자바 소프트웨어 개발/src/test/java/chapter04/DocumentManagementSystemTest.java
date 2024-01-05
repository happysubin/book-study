package chapter04;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentManagementSystemTest {

    DocumentManagementSystem system = new DocumentManagementSystem();

    @Test
    void shouldImportFile() throws IOException {
        String path = "src/test/resources/patient.letter";
        system.importFile(path);
        onlyDocument();
    }

    private void assertAttributeEquals(
            final Document document,
            final String attributeName,
            final String expectedValue)
    {
        assertEquals(
                "Document has the wrong value for " + attributeName,
                expectedValue,
                document.getAttribute(attributeName));
    }

    private Document onlyDocument()
    {
        final List<Document> documents = system.contents();
        assertEquals(1, documents.size());
        return documents.get(0);
    }

}