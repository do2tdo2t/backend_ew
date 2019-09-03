package kr.or.connect.edwith.service;


import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;

public interface DisplayService {
	public DisplayInfo getDisplayInfoById(Integer displayInfoId);
	public DisplayInfoImage getDisplayInfoImageById(Integer displayInfoId);
	
}
