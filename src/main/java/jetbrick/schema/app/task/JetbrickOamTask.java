package jetbrick.schema.app.task;

import java.util.Map;
import jetbrick.schema.app.Task;
import jetbrick.schema.app.modal.TableInfo;

public class JetbrickOamTask extends Task {

    public JetbrickOamTask() {
        name = "oam";
    }

    @Override
    public void execute() throws Throwable {
        // output pojo file for each table.
        for (TableInfo table : schema.getTables()) {
            Map<String, Object> context = getTemplateContext();
            context.put("table", table);

            writeFile("pojo.java.httl", getPackagePath() + "/dao/data/" + table.getTableClass() + ".java", context);
        }

        Map<String, Object> context = getTemplateContext();
        writeFile("GlobalsEnum.java.httl", getPackagePath() + "/config/GlobalsEnum.java", context);
    }

}
