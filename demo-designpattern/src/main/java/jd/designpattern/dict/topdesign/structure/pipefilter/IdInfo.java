package jd.designpattern.dict.topdesign.structure.pipefilter;

import java.util.Date;

public class IdInfo{
	String name;
	String id;
	String phone;
	String sex;
	Date birth;
	String headPic ;
	String fingerPrint;
	boolean hasIdcard ;
	boolean dirtyCloth ;
	boolean fingersHarmed;
	boolean haveBadRecords;
	
	public IdInfo(String name, String id, String phone) {
		this.name = name;
		this.id = id;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getSex() {
		return sex;
	}
	public Date getBirth() {
		return birth;
	}
	public int getAge(){
		return new Date().getYear() - birth.getYear() + 1 ;
	}
	
	public String getHeadPic() {
		return headPic;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}
	public boolean isHasIdcard() {
		return hasIdcard;
	}
	public boolean isDirtyCloth() {
		return dirtyCloth;
	}
	public boolean isFingersHarmed() {
		return fingersHarmed;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	public void setHasIdcard(boolean hasIdcard) {
		this.hasIdcard = hasIdcard;
	}
	public void setDirtyCloth(boolean dirtyCloth) {
		this.dirtyCloth = dirtyCloth;
	}
	public void setFingersHarmed(boolean fingersHarmed) {
		this.fingersHarmed = fingersHarmed;
	}
	
}