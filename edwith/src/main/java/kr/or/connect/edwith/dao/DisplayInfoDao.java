package kr.or.connect.edwith.dao;

import org.springframework.stereotype.Repository;

import kr.or.connect.edwith.dto.DisplayInfo;

@Repository
public interface DisplayInfoDao {
	public DisplayInfo selectOneById(Integer displayInfoId);
}
