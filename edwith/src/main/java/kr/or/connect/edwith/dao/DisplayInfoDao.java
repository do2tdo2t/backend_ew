package kr.or.connect.edwith.dao;

import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dto.DisplayInfo;
import kr.or.connect.edwith.dto.DisplayInfoImage;

@Repository
public interface DisplayInfoDao {
	public DisplayInfo selectDisplayInfoById(Integer displayInfoId);
}
