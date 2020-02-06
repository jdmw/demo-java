import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(SalaryType.Month.genSalary(0,0));
        System.out.println(SalaryType.Month.genSalary(0,50));
        System.out.println(SalaryType.Month.genSalary(10,50));
        System.out.println(SalaryType.Month.genSalary(1,999));
        System.out.println(SalaryType.Hour.genSalary(0,0));
        System.out.println(SalaryType.Hour.genSalary(0,50));
        System.out.println(SalaryType.Hour.genSalary(10,50));
        System.out.println(SalaryType.Hour.genSalary(1,999));
        System.out.println(SalaryType.Year.genSalary(0,0));
        System.out.println(SalaryType.Year.genSalary(0,50));
        System.out.println(SalaryType.Year.genSalary(10,50));
        System.out.println(SalaryType.Year.genSalary(1,999));



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


/**
 * Created by huangxia on 2020/1/14.
 */
 enum SalaryType {
    Month(1,"k","月"),Hour(2,"","小时"),Year(3,"k","年");

    //1:月薪 2:时薪 3:年薪
    private byte salaryType ;
    private String name ;
    private String unit ; // 薪资单位： 千元或元

    SalaryType(int salaryType,String unit , String name) {
        this.salaryType = (byte)salaryType;
        this.unit = unit;
        this.name = name;
    }

    public byte getSalaryType() {
        return salaryType;
    }

    public static SalaryType instanceOf(Byte salaryType){
        if( salaryType != null){
            for (SalaryType type : values()) {
                if(type.getSalaryType() == salaryType) return type;
            }
        }
        return null ;
    }

    public String genSalary( int salaryBottom,int salaryTop){
        if(salaryTop <= 0 && salaryBottom <= 0){
            return "薪资面议" ;
        }else if( salaryBottom > 0 &&  salaryTop == 999){
            return String.format("%d%s以上/%s",salaryBottom,unit,name);
        }else if(salaryTop == salaryBottom){
            return String.format("%d%s/%s",salaryBottom,unit,name);
        }else{
            return String.format("%d%s/%s - %d%s/%s",salaryBottom,unit,name,salaryTop,unit,name);
        }
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
