package com.xunge.model.basedata.vo;

import java.util.Date;

import com.xunge.model.basedata.DatBaseresource;

/**
 * 
* Desc: 机房模型
* Title: RoomVO
*
* @author Rob
 */
public class RoomVO extends BaseDataVO{

	private String city;
	private String region;
	private String roomReg;
	private int verifyStatus;
	private String roomId;
	private String roomCode;
	private String roomName;
	private String roomCity;
	private String roomRegion;
	private int roomStatus;
	private String roomOpendate;
	private int roomType;
	private int roomCategory;
	private int roomPowmode;
	private int roomPowtype;
	private int roomBuildstructure;
	private int roomBuildmode;
	private int roomBuildshare;
	private int roomOwner;
	private long roomAirpower;
	private int roomOpttype;
	private String roomNote;
	private String roomAuditor;
	
	public DatBaseresource toEntity(){
		DatBaseresource room = new DatBaseresource();
		room.setBaseresourceId(this.roomId);
		if(this.roomRegion != null){
			room.setRegId(this.roomRegion);
		}else{
			room.setRegId(this.roomCity);
		}
		room.setBaseresourceType(0);
		room.setBaseresourceCategory(this.roomCategory);
		room.setBaseresourceCode(this.roomCode);
		room.setBaseresourceName(this.roomName);
		room.setBaseresourceOpendate(new Date());//this.roomOpendate);
		room.setRoomOwner(this.roomOwner);
		room.setRoomProperty(this.roomBuildmode);
		room.setRoomShare(this.roomBuildshare);
		room.setAirconditionerPower(this.roomAirpower);
		room.setBaseresourceState(1);
		room.setBaseresourceNote(this.roomNote);
		room.setDataFrom(0);
		return room;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRoomReg() {
		return roomReg;
	}

	public void setRoomReg(String roomReg) {
		this.roomReg = roomReg;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomCity() {
		return roomCity;
	}

	public void setRoomCity(String roomCity) {
		this.roomCity = roomCity;
	}

	public String getRoomRegion() {
		return roomRegion;
	}

	public void setRoomRegion(String roomRegion) {
		this.roomRegion = roomRegion;
	}

	public int getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(int roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getRoomOpendate() {
		return roomOpendate;
	}

	public void setRoomOpendate(String roomOpendate) {
		this.roomOpendate = roomOpendate;
	}

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public int getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(int roomCategory) {
		this.roomCategory = roomCategory;
	}

	public int getRoomPowmode() {
		return roomPowmode;
	}

	public void setRoomPowmode(int roomPowmode) {
		this.roomPowmode = roomPowmode;
	}

	public int getRoomPowtype() {
		return roomPowtype;
	}

	public void setRoomPowtype(int roomPowtype) {
		this.roomPowtype = roomPowtype;
	}

	public int getRoomBuildstructure() {
		return roomBuildstructure;
	}

	public void setRoomBuildstructure(int roomBuildstructure) {
		this.roomBuildstructure = roomBuildstructure;
	}

	public int getRoomBuildmode() {
		return roomBuildmode;
	}

	public void setRoomBuildmode(int roomBuildmode) {
		this.roomBuildmode = roomBuildmode;
	}

	public int getRoomBuildshare() {
		return roomBuildshare;
	}

	public void setRoomBuildshare(int roomBuildshare) {
		this.roomBuildshare = roomBuildshare;
	}

	public int getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(int roomOwner) {
		this.roomOwner = roomOwner;
	}

	public long getRoomAirpower() {
		return roomAirpower;
	}

	public void setRoomAirpower(long roomAirpower) {
		this.roomAirpower = roomAirpower;
	}

	public int getRoomOpttype() {
		return roomOpttype;
	}

	public void setRoomOpttype(int roomOpttype) {
		this.roomOpttype = roomOpttype;
	}

	public String getRoomNote() {
		return roomNote;
	}

	public void setRoomNote(String roomNote) {
		this.roomNote = roomNote;
	}

	public String getRoomAuditor() {
		return roomAuditor;
	}

	public void setRoomAuditor(String roomAuditor) {
		this.roomAuditor = roomAuditor;
	}
}
