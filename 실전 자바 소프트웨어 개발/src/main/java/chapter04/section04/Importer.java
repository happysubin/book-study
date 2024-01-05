package chapter04.section04;

import java.io.File;
import java.io.IOException;

public interface Importer {

    String PATH = "path";
    String WIDTH = "witdh";
    String HEIGHT = "height";
    String TYPE = "type";

    Document importFile(File file) throws IOException;
}
