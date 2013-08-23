package jetbrick.schema.app;

import httl.Engine;
import httl.Template;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import jetbrick.commons.exception.SystemException;

public class TemplateEngine {
    private static final Engine engine = Engine.getEngine(null, getEngineConfig());

    private static Properties getEngineConfig() {
        Properties p = new Properties();
        p.setProperty("import.packages+", "jetbrick.schema.app.model");
        p.setProperty("import.methods+", "jetbrick.schema.app.model.methods.JsonUtils, jetbrick.schema.app.model.methods.ChecksumUtils,jetbrick.schema.app.model.methods.TableInfoUtils,jetbrick.schema.app.model.methods.TableColumnUtils");
        p.setProperty("import.variables+", "Schema schema, TableInfo table");

        p.setProperty("input.encoding", "utf-8");
        p.setProperty("output.encoding", "utf-8");

        p.setProperty("reloadable", "false");
        p.setProperty("precompiled", "false");
        p.setProperty("compiler", "httl.spi.compilers.JavassistCompiler");
        p.setProperty("loggers", "httl.spi.loggers.Slf4jLogger");

        p.setProperty("loaders", "httl.spi.loaders.ClasspathLoader");
        p.setProperty("template.directory", "");
        p.setProperty("template.suffix", ".httl");

        p.setProperty("value.switchers", "");
        p.setProperty("value.filters", "");
        p.setProperty("text.filters", "");
        p.setProperty("json.codec", "httl.spi.codecs.FastjsonCodec");
        p.setProperty("remove.directive.blank.line", "false");

        //p.setProperty("code.directory", "c:/temp");

        p.setProperty("output.stream", "false");
        p.setProperty("output.writer", "true");

        p.setProperty("source.in.class", "false");
        p.setProperty("text.in.class", "false");

        return p;
    }

    public static String apply(String file, Map<String, Object> context) {
        try {
            Template template = engine.getTemplate(file);
            StringWriter writer = new StringWriter();
            template.render(context, writer);
            return writer.toString();
        } catch (Exception e) {
            throw SystemException.unchecked(e);
        }
    }
}
