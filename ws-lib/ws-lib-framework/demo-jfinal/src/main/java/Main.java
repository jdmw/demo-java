
public class Main {

    public static void main(String[] args) {
        String findCandidateSelect = "SELECT cd.id, cd.wxuser_id, cd.is_recommend, "
                + "cd.name, cd.mobile, cd.email, cd.nickname, cd.headimgurl, "
                + "cd.sys_user_id ";

        StringBuilder findCandidateFrom = new StringBuilder();
        findCandidateFrom.append(" \nFROM candidate_company cd ");

        // 如果是 hr 子帐号的查询,需要连表查询下 job_position 的 publisher 1
        if(true) {
            findCandidateFrom.append("\n INNER JOIN candidate_position cp on cp.candidate_company_id = cd.id ")
                    .append("\n INNER JOIN (SELECT id, publisher from jobdb.job_position where company_id = ")
                    .append(4).append(") pt ")
                    .append(" ON pt.id = cp.position_id ");
        }

        // 过滤条件4: 是否投递了简历
        findCandidateSelect += "\n, if(t.applier_id, 1, 0) has_app";

        if (true) {
            findCandidateFrom.append("\n INNER ");
        }
        else {
            findCandidateFrom.append("\n LEFT ");
        }
        findCandidateFrom.append(" JOIN (SELECT DISTINCT applier_id FROM jobdb.job_application WHERE company_id = ")
                .append(4)
                .append(" and ((apply_type = 0) or (apply_type = 1 and email_status = 0))")
                .append(") t ON cd.sys_user_id = t.applier_id ");


        findCandidateFrom.append("\nWHERE cd.status = Constant.ABLE_NEW")
                .append(" AND cd.company_id = ").append(4)
                .append(" AND cd.id IN (?) ");

        // 过滤条件5: 是否有员工评价信息
        if(true) {
            findCandidateFrom.append("\n AND cd.is_recommend = Constant.ABLE_NEW " );
        }

        // 过滤条件6: 是否有联系方式
        if(true) {
            findCandidateFrom.append("\n AND cd.mobile != '' AND cd.email != ''");
        }

        // 过滤条件4: 是否投递了简历
        if (true) {
            findCandidateFrom.append("\n AND t.applier_id > 1");
        }

        // 如果是 hr 子帐号的查询,需要连表查询下 job_position 的 publisher 2
        if(true) {
            findCandidateFrom.append("\n AND pt.publisher = ? " );
        }

        // 去掉重复的候选人
        findCandidateFrom.append("\n GROUP BY cd.sys_user_id");
        findCandidateFrom.append("\n ORDER BY cd.update_time DESC ");


        StringBuilder sysUserIdsForCp = new StringBuilder("(?)");

        StringBuilder findCPSql = new StringBuilder();
        findCPSql
                .append("SELECT h.sys_user_id, COALESCE(t1.c, t2.c) AS company, COALESCE(t1.p, t2.p) AS position, t1.mobile, t1.name, t1.nickname, t1.email, t2.realname ")
                .append("\nFROM candidate_company h ")
                .append("\nINNER JOIN ")
                .append("\n(SELECT id, company AS c, position AS p, mobile, name, nickname, email FROM user_user WHERE id in ").append(sysUserIdsForCp.toString())
                .append("\n ) t1 ON t1.id = h.sys_user_id ")
                .append("\nLEFT JOIN ")
                .append("\n(SELECT rr.presentee_user_id, rr.company AS c, rr.positiON AS p, rr.realname as realname FROM candidate_recom_record rr ")
                .append("\n where rr.presentee_user_id in ").append(sysUserIdsForCp.toString())
                .append("\nand rr.is_recom = 0 ORDER BY rr.update_time DESC LIMIT 1 ")
                .append("\n) t2 ON t2.presentee_user_id = h.sys_user_id ");


        //System.out.println((findCandidateSelect+findCandidateFrom.toString()).toUpperCase().replaceAll("HCP","hcp").replaceAll("CD","cd").replaceAll("HP","hp"));
        System.out.println(findCPSql.toString().toUpperCase().replaceAll("RR","rr").replaceAll("T1","t1").replaceAll("T2","t2"));
    }
}

