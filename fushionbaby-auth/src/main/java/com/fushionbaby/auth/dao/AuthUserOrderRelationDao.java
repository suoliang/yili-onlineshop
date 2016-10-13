package com.fushionbaby.auth.dao;
import java.util.List;
import java.util.Map;

import com.fushionbaby.auth.model.AuthUserOrderRelation;
/***
 * 后天用户订单关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月28日上午9:32:37
 */
public interface AuthUserOrderRelationDao {
	/***
	 * 删除
	 * @param id
	 */
    void deleteById(Long id);
    /***
     * 增加
     * @param auUser
     */
    void add(AuthUserOrderRelation auUser);
    /***
     * 查找
     * @param id
     * @return
     */
    AuthUserOrderRelation findById(Long id);
    /***
     * 更新
     * @param auUser
     */
    void update(AuthUserOrderRelation auUser);
    /***
     * 查询全部
     * @return
     */
    List<AuthUserOrderRelation> findAll();
    /**
	 * 分页查询
	 */
	List<AuthUserOrderRelation> getListPage(Map<String, Object> map);
	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
	/***
	 * 通过后台登陆用的 id和姓名查询
	 * @param map
	 * @return
	 */
	List<AuthUserOrderRelation> findByUserIdAndUserName(Map<String, Object> map);
	/***
	 * 删除已撤回的关联记录
	 * @param map
	 */
	void deleteByOrderCodeAndUserId(Map<String, Object> map);
    
}