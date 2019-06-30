package jd.designpattern.dict.topdesign.structure.pipefilter;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.Date;
import java.util.UUID;

import jd.designpattern.common.dto.DataDTO;
import jd.util.StrUt;

public class DMainApplyForPassportFilter {

	private static abstract class IdInfoFilter implements Filter<DataDTO<IdInfo,Boolean>> {
		@Override
		public boolean match(DataDTO<IdInfo,Boolean> dto) {
			IdInfo info = dto.getData();
			return check(info,dto);
		}
		public abstract boolean check(IdInfo info,DataDTO<IdInfo,Boolean> dto);
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		IdInfo info = new IdInfo("Hubery","342911111****23","18912345678");
		info.setBirth(new Date(1991-1900,1,1));
		info.setHasIdcard( true );
		info.dirtyCloth = true;
		
		
		boolean result = new SingleThreadCompositFilter<IdInfo>()
				.addFilter(new IdInfoFilter(){ // fill in infomations
					@Override
					public boolean check(IdInfo info,
							DataDTO<IdInfo, Boolean> dto) {
						if(StrUt.isNotBlank(info.name,info.id,info.phone) && info.birth != null ){
							if(info.getAge() >= 18){
								return true ;
							}else{
								err.println("Your age is below 18");
							}
						}else{
							err.println("Please complete your infomation");
						}
						return false;
					}
				}).addFilter(new IdInfoFilter(){ // scan the ID card

					@Override
					public boolean check(IdInfo info,
							DataDTO<IdInfo, Boolean> dto) {
						if( info.hasIdcard){
							out.println("Scanning the ID card...");
							return true;
						}else{
							err.println("Please take your ID card!");
							return false;
						}
					}
				}).addFilter(new IdInfoFilter(){ // take photo

					@Override
					public boolean check(IdInfo info,
							DataDTO<IdInfo, Boolean> dto) {
						if( !info.dirtyCloth){
							out.println("taking your head photo");
							info.setHeadPic(info.getId()+".jpg");
							return true;
						}else{
							err.println("Please tidy your clothes.");
							return false;
						}
					}
				}).addFilter(new IdInfoFilter(){ // fingerprint

					@Override
					public boolean check(IdInfo info,
							DataDTO<IdInfo, Boolean> dto) {
						if( !info.fingersHarmed){
							out.println("fingerprint...");
							info.setFingerPrint(UUID.randomUUID().toString());
							return true;
						}else{
							err.println("Sorry,you can'f fingerprint because they're not complete.");
							return false;
						}
					}
				}).addFilter(new IdInfoFilter(){ // apply

					@Override
					public boolean check(IdInfo info,
							DataDTO<IdInfo, Boolean> dto) {
						if( !info.haveBadRecords){
							out.println("Your passport is applied successful,please pay at window 7");
							return true;
						}else{
							err.println("Sorry,you can't apply for passport");
							return false;
						}
					}
				}).addFilter(new IdInfoFilter(){

					@Override
					public boolean check(IdInfo info,
							DataDTO<IdInfo, Boolean> dto) {
						out.println("You've payed the fee.\nYou've complete the application."
								+ "you could take your password after 15 workdays.");
						return true;
					}
				}).match(info);
		Thread.sleep(10);
		out.println("--------------------------------------\nyour apply:" +( result?"successful":"failed"));
	}

}
