package jetbrick.schema.app.task;

import java.util.Map;
import jetbrick.schema.app.Task;
import jetbrick.schema.app.model.BulkFile;

public class XmlSchemaTask extends Task {

    public XmlSchemaTask() {
        name = "schema";
    }

    @Override
    public void execute() throws Throwable {
        Map<String, Object> context = getTemplateContext();

        writeFile("schema-table.xml.httl", "META-INF/schema-table.xml", context);
        writeFile("schema-enum.xml.httl", "META-INF/schema-enum.xml", context);
        writeFile("schema-bulk.xml.httl", "META-INF/schema-bulk.xml", context);

        for (BulkFile bulk : schema.getBulkFiles()) {
            writeFile("META-INF/bulk/" + bulk.getFileName(), bulk.getContents());
        }
        
        writeFile("GlobalsEnum.java.httl", getPackagePath() + "/config/GlobalsEnum.java", context);
    }

}
