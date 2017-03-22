package com.manager.serviceImpl;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manager.bean.RecommendResult;
import com.manager.dao.RecommendResultMapper;
import com.manager.service.RecommendService;
@Service
@Transactional
public class RecommendServiceImpl implements RecommendService{
@Autowired
private RecommendResultMapper re;
	public int save(RecommendResult r) {
		// TODO Auto-generated method stub
		return re.insert(r);
	}

}
