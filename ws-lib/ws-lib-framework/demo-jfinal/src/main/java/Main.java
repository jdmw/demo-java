import java.util.List;

public class Main {

    public static void main(String[] args) {
        String findPositions = "select hp.title, hp.id, hp.status from job_position hp where hp.company_id = ?  and "
                + " hp.publisher = ? and hp.id not in "
                + "(select id from candidate_suggest_position where hraccount_id = ? and user_id = ? ) and hp.status = 0 ";
        String findSugPosition = "select hp.id "
                + " from candidate_suggest_position sp inner join job_position hp "
                + " on sp.position_id = hp.id "
                + " where sp.hraccount_id = ? and sp.disable = 0 and "
                + " sp.user_id = ?";
        System.out.println(findPositions.toUpperCase().replaceAll("HP","job_position".toUpperCase()));
        System.out.println(findSugPosition.toUpperCase().replaceAll("SP","sp").replaceAll("HP","hp"));
    }
}

