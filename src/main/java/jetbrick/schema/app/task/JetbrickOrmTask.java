package jetbrick.schema.app.task;

import java.util.Map;
import jetbrick.schema.app.Task;
import jetbrick.schema.app.model.TableInfo;

public class JetbrickOrmTask extends Task {

    public JetbrickOrmTask() {
        name = "orm";
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
