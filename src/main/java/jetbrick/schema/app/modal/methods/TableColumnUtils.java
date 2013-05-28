package jetbrick.schema.app.modal.methods;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import jetbrick.schema.app.modal.EnumItem;
import jetbrick.schema.app.modal.TableColumn;

public class TableColumnUtils {

    public static String getter(TableColumn c) {
        return "get" + WordUtils.capitalize(c.getFieldName());
    }

    public static String setter(TableColumn c) {
        return "set" + WordUtils.capitalize(c.getFieldName());
    }

    public static String sqlTypeName(TableColumn c) {
        return c.getTable().getSchema().getDialect().asSqlType(c.getTypeName(), c.getTypeLength(), c.getTypeScale());
    }
    
    public static String fullFieldClass(TableColumn c) {
        if (c.getFieldClass().getName().startsWith("java.lang.")) {
            return c.getFieldClass().getSimpleName();
        } else {
            return c.getFieldClass().getName();
        }
    }

    public static String enumGroupDescription(TableColumn c) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.trimToEmpty(c.getDescription()));
        if (c.getEnumGroup() != null) {
            for (EnumItem en : c.getEnumGroup().getItems()) {
                if (sb.length() > 0) {
                    sb.append("<br/>");
                }
                sb.append(en.getId() + "-" + en.getName());
            }
        }
        return sb.toString();
    }

    public static String fieldDefaultValue(TableColumn c) {
        StringBuffer sb = new StringBuffer();
        sb.append("this.");
        sb.append(c.getFieldName());
        sb.append(" = ");
        if (String.class.equals(c.getFieldClass())) {
            sb.append("\"");
            sb.append(StringEscapeUtils.escapeJava((String) c.getDefaultValue()));
            sb.append("\"");
        } else if (Double.class.equals(c.getFieldClass())) {
            sb.append(c.getDefaultValue() + "D");
        } else if (Float.class.equals(c.getFieldClass())) {
            sb.append(c.getDefaultValue() + "F");
        } else if (Long.class.equals(c.getFieldClass())) {
            sb.append(c.getDefaultValue() + "L");
        } else {
            sb.append(c.getDefaultValue());
        }
        return sb.toString();
    }

    // hibernate hbm column 定义
    public static String getHbmColumnDefination(TableColumn c) {
        StringBuffer sb = new StringBuffer();
        sb.append("<property name='");
        sb.append(c.getFieldName());
        sb.append("' type='");
        sb.append(c.getFieldClass().getName());
        sb.append("' not-null='");
        sb.append(!c.isNullable());
        sb.append("'>\n");
        sb.append("\t\t\t<column name='");
        sb.append(StringEscapeUtils.escapeHtml4(c.getTable().getSchema().getDialect().getIdentifier(c.getColumnName())));
        sb.append("'");
        if (c.getTypeLength() != null) {
            sb.append(" length='");
            sb.append(c.getTypeLength());
            sb.append("'");
        }
        if (c.getTypeScale() != null) {
            sb.append(" scale='");
            sb.append(c.getTypeScale());
            sb.append("'");
        }
        sb.append(" />\n");
        sb.append("\t\t</property>");
        return StringUtils.replaceChars(sb.toString(), "'", "\"");
    }
}
