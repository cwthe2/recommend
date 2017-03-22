package com.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.manager.bean.RecommendResult;
import com.manager.service.RecommendService;
import com.manager.untils.Recommend;

//test

@Controller
@RequestMapping(value="/recommend")
public class RecommendController {
	@Autowired
	private RecommendService rservice;
	
	@RequestMapping(value="/recommendIndex")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/recommend/index");
		return mav;
		
	}
	@RequestMapping(value="/train",method=RequestMethod.POST)
	public ModelAndView train(HttpServletRequest request,HttpSession session){
		ModelAndView mav=new ModelAndView();
		int num=Integer.parseInt(request.getParameter("numResult").toString());
		Recommend recommend=new Recommend();
		MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(recommend.sc.sc(),
				"hdfs://172.18.16.237:9000//user/fansy");
		if(sameModel!=null){
			List<Rating> result=new ArrayList<Rating>();
			result=recommend.recommendationsResult(Integer.parseInt(session.getAttribute("userid").toString()), sameModel, recommend.getRatings(),
															recommend.getProducts(), num);
			
			//推荐结果插入数据库
			for(int i=0;i<result.size();i++){
				RecommendResult r=new RecommendResult();
				r.setRating(result.get(i).rating());
				r.setRid(result.get(i).product());
				r.setUid(result.get(i).user());
				this.rservice.save(r);
			}
			
			
			 mav=new ModelAndView("");
			 
		}else{
			//接收字符串转为数组
			String ranks=request.getParameter("ranks").toString();
			String lambdas=request.getParameter("lambdas").toString();
			String numIters=request.getParameter("numIters").toString();
			
			
			String ranknum[]=ranks.split("-");
			String lambnum[]=lambdas.split("-");
			String numItersnum[]=numIters.split("-");
			
			int rankint[]=new int[2];
			float[] lamfloat=new float[2];
			int numint[]=new int[2];
			
			
			for(int i=0;i<ranknum.length;i++){			
				rankint[i]=Integer.parseInt(ranknum[i]);
				lamfloat[i]=Float.parseFloat(lambnum[i]);
				numint[i]=Integer.parseInt(numItersnum[i]);
			}
			
			
			String ratingsPath=request.getParameter("ratingsPath").toString();
			String moviesPath=request.getParameter("moviesPath").toString();
			recommend.splitData(ratingsPath, moviesPath);
			//2.训练数据
			recommend.trainData();
			//3.训练模型
			MatrixFactorizationModel bestModel=recommend.trainModel(recommend.getTraining(), recommend.getValidation(), rankint, lamfloat, numint,"hdfs://172.18.16.237:9000//user/fansy");
			//System.out.println("训练的模型为"+bestModel.logName());
			//4.计算RMSE
			recommend.computeRMSE(bestModel, recommend.getTest());
			//5.推荐结果
			List<Rating> result=new ArrayList<Rating>();
			result=recommend.recommendationsResult(Integer.parseInt(session.getAttribute("userid").toString()), bestModel, recommend.getRatings(),
															recommend.getProducts(), num);
			
			
			//推荐结果插入数据库
			for(int i=0;i<result.size();i++){
				RecommendResult r=new RecommendResult();
				r.setRating(result.get(i).rating());
				r.setRid(result.get(i).product());
				r.setUid(result.get(i).user());
				this.rservice.save(r);
			}
		}
	
		return mav;
	}

	
}
