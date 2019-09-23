package kr.or.connect.edwith.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.edwith.dao.FileInfoDao;
import kr.or.connect.edwith.dto.FileInfo;
import kr.or.connect.edwith.service.FileInfoService;

@Service
public class FileInfoServiceImpl implements FileInfoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	FileInfoDao fileInfoDao;
	
	@Override
	public Integer putFileInfo(FileInfo fileInfo) {
		
		logger.debug("FileInfoServiceImpl... {}",fileInfo.toString());
		return fileInfoDao.insertOne(fileInfo);
	}

}
