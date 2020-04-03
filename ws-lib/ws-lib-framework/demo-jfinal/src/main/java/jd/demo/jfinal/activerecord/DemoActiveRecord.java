package jd.demo.jfinal.activerecord;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;

import com.jfinal.plugin.activerecord.Model;



/**
 * ref : https://gitee.com/jfinal/activerecord
 */
public class DemoActiveRecord {

    public static class CandidateRelatedPosition extends Model<CandidateRelatedPosition> {

        private static final long serialVersionUID = 8791222298465576187L;
        public static final CandidateRelatedPosition dao = new CandidateRelatedPosition();
    }


    static String jdbcUrl = "jdbc:mysql://localhost:3306/candidatedb?useSSL=false&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone = GMT";
    static String user = "user";
    static String password = "pass";

    public static DruidPlugin createDruidPlugin() {
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
        return druidPlugin;
    }

    public static void initActiveRecordPlugin() {
        DruidPlugin druidPlugin = createDruidPlugin();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setDevMode(true);
        arp.setShowSql(true);

        // 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
        //arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");

        // 所有映射在生成的 _MappingKit.java 中自动化搞定
        arp.addMapping("candidate_position",CandidateRelatedPosition.class);

        // 先启动 druidPlugin，后启动 arp
        druidPlugin.start();
        arp.start();
    }

    public static void main(String[] args) {
        initActiveRecordPlugin();

        CandidateRelatedPosition candidate = CandidateRelatedPosition.dao.findFirst("select "
                + " user_id, position_id, is_interested "
                + " from candidate_position "
                + " limit 1");
        System.out.printf("%s : %s \n",candidate,candidate.getBoolean("is_interested"));
        /*// 使用 Model
        Blog dao = new Blog().dao();
        Blog blog = dao.template("findBlog", 1).findFirst();
        System.out.println(blog.getTitle());

        // 使用 Db + Record 模式
        Record record = Db.template("findBlog", 1).findFirst();
        System.out.println(record.getStr("title"));*/
    }
}
