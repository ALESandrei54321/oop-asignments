
import classes.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.*;

import javax.lang.model.element.PackageElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(final String[] args) throws IOException {

        Path in_file = Paths.get(args[0]);

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(String.valueOf(in_file)),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();


        Database.getDatabase().initialiseDatabase(inputData);


        for (ActionsInput action : inputData.getActions()) {
            Database.getDatabase().doAction(action,objectMapper,output);
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File("results/out_" + in_file.getFileName()), output);
        objectWriter.writeValue(new File("results.out"), output);
    }

}