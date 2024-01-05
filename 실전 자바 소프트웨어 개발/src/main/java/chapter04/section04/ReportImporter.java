package chapter04.section04;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportImporter implements Importer {

    @Override
    public Document importFile(File file) throws IOException {
//        final Map<String, String> attributes = new HashMap<>();
//
//        attributes.put(PATH, file.getPath());
//
//        BufferedImage image = ImageIO.read(file);
//
//        attributes.put(WIDTH, String.valueOf(image.getWidth()));
//        attributes.put(HEIGHT, String.valueOf(image.getHeight()));
//        attributes.put(TYPE, "IMAGE");
//
//        return new Document(attributes);
        return null;
    }
}
