package kr.co.mgv.theater.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import kr.co.mgv.theater.facility.vo.TheaterFacility;
import kr.co.mgv.theater.floors.vo.FloorInfo;
import kr.co.mgv.theater.location.vo.Location;
import kr.co.mgv.theater.parking.vo.ParkingInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Alias("theater")
public class Theater {

	private int no;
	private Location location;
	private String name;
	private String address;
	private String tel;
	private String disabled;
	private String info;
	private List<TheaterFacility> facilities;
	private List<FloorInfo> floorInfos;
	private ParkingInfo parkingInfo;
	
	public Theater(int no) {
		this.no = no;
	}

}