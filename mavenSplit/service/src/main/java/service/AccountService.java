package service;

import domain.Account;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/12 19:33
 * @Version: 1.0
 */
public interface AccountService {
    /**
     * 查询所有
     * @return 所有Account数据
     */
    List<Account> findAll();

    /**
     * 根据id查询Account
     * @param id Account的id
     * @return Account对象数据
     */
    Account findById(Integer id);

    /**
     * 更新Account
     * @param account 要更新的数据
     * @return 是否更新成功
     */
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

    void transfer(Integer sid, Integer tid, Double money);
}
