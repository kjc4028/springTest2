package testjc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import testjc.util.ListHelper;


@Repository
public class TestDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<Map<String, String>> selectTestList(){
		return sqlSession.selectList("test.sql.testSelect");
	};
	
	public List<Map<String, String>> helperList(Map<String, Object> paramMap){
		return sqlSession.selectList("test.sql.helperList",paramMap);
	};
	
	public void insertTest(Map<String, String> param){
		sqlSession.insert("test.sql.testInsert", param);
	};
	
	public Integer totalCount(){
		return sqlSession.selectOne("test.sql.totalSelect");
	}
	
	public void updateTest(Map<String, String> param){
		sqlSession.update("test.sql.testUpdate", param);
	}
	
	public void deleteTest(Map<String, String> param){
		sqlSession.delete("test.sql.testDelete", param);
	}
	
}
