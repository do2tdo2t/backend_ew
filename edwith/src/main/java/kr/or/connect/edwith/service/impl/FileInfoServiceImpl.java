package kr.or.connect.edwith.service.impl;

import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.FileInfoDao;
import kr.or.connect.edwith.dto.FileInfo;
import kr.or.connect.edwith.service.FileInfoService;

@Service
public class FileInfoServiceImpl implements FileInfoService {

	FileInfoDao fileInfoDao;
	@Override
	public Integer putFileInfo(FileInfo fileInfo) {
		
		
		return fileInfoDao.insertOne(fileInfo);
	}

}
