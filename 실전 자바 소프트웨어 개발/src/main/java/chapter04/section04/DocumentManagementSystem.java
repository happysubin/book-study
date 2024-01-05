package chapter04.section04;

import java.util.HashMap;
import java.util.Map;

public class DocumentManagementSystem {

    private final Map<String, Importer> extensionToImporter = new HashMap<>();

    public DocumentManagementSystem() {
        extensionToImporter.put("jpg", new ImageImporter());
        extensionToImporter.put("report", new ReportImporter());
    }
}
