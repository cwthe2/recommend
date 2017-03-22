 package com.manager.untils;

import java.util.ArrayList;
import java.util.List;


import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;



public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ratingsPath="hdfs://172.18.16.237:9000//user/root//ratings.dat";
		String moviesPath="hdfs://172.18.16.237:9000//user/root//movies.dat";
		int[] ranks = { 8, 12 };
		float[] lambdas = { 0.1f, 10.0f };
		int[] numIters = { 10, 20 };
		int userId=4;
		int numResult=4;
		String modelSavePath="hdfs://172.18.16.237:9000//user/fansy";
		Recommend recommend=new Recommend();
		//1.�����и�����
		recommend.splitData(ratingsPath, moviesPath);
		//2.ѵ������
		//recommend.trainData();
		//3.ѵ��ģ��
		//MatrixFactorizationModel bestModel=recommend.trainModel(recommend.getTraining(), recommend.getValidation(), ranks, lambdas, numIters,modelSavePath);
		//System.out.println("ѵ����ģ��Ϊ"+bestModel.logName());
		//4.����RMSE
		//recommend.computeRMSE(bestModel, recommend.getTest());
		//5.�Ƽ����
		MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(recommend.sc.sc(),
				modelSavePath);
		List<Rating> result=new ArrayList<Rating>();
		result=recommend.recommendationsResult(userId, sameModel, recommend.getRatings(),
														recommend.getProducts(), numResult);
		for(int i=0;i<result.size();i++){
			System.out.println("�ṹhhh+++"+result.get(i).user()+"kkkk"+result.get(i).rating()+"llllll"+result.get(i).product());
		}
		System.out.println("���Ϊ��"+result);
	}

}