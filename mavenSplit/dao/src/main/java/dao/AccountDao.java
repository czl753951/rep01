package dao;

import domain.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/12 18:55
 * @Version: 1.0
 */
public interface AccountDao {
    /**
     * 查询所有
     * @return 所有Account数据
     */
    @Select("SELECT id,NAME,money FROM account")
    List<Account> findAll();

    /**
     * 根据id查询Account
     * @param id Account的id
     * @return Account对象数据
     */
    @Select("SELECT id,NAME,money FROM account where id=#{id}")
    Account findById(Integer id);

    /**
     * 更新Account
     * @param account 要更新的数据
     * @return 是否更新成功
     */
    @Update("UPDATE account SET NAME=#{name},money=#{money} WHERE id=#{id}")
    boolean update(Account account);

    /**
     * 插入一条数据
     * @param account 要插入的数据
     * @return 是否插入成功
     */
    boolean insert(Account account);

    /**
     * 根据id删除指定数据
     * @param id 要删除的数据id
     * @return 是否删除成功
     */
    boolean delete(Integer id);
}
