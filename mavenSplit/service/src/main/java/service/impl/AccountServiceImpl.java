package service.impl;

import dao.AccountDao;
import domain.Account;
import exception.TransferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.AccountService;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/12 19:34
 * @Version: 1.0
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 查询所有
     *
     * @return 所有Account数据
     */
    @Transactional(propagation= Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    /**
     * 根据id查询Account
     *
     * @param id Account的id
     * @return Account对象数据
     */
    @Transactional(propagation= Propagation.SUPPORTS,readOnly = true)
    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    /**
     * 更新Account
     *
     * @param account 要更新的数据
     * @return 是否更新成功
     */
    @Override
    public boolean update(Account account) {
        return accountDao.update(account);
    }

    /**
     * 插入一条数据
     *
     * @param account 要插入的数据
     * @return 是否插入成功
     */
    @Override
    public boolean insert(Account account) {
        return accountDao.insert(account);
    }

    /**
     * 根据id删除指定数据
     *
     * @param id 要删除的数据id
     * @return 是否删除成功
     */
    @Override
    public boolean delete(Integer id) {
        return accountDao.delete(id);
    }


    @Override
    public void transfer(Integer sid, Integer tid, Double money) {
        Account sAccount = accountDao.findById(sid);
        if (sAccount == null) {
            throw new TransferException("转出账户不存在");
        }
        Account tAccount = accountDao.findById(tid);
        if (tAccount == null) {
            throw new TransferException("转入账户不存在");
        }
        if (sAccount.getMoney() < money) {
            throw new TransferException("余额不足");
        }

        //转账操作
        sAccount.setMoney(sAccount.getMoney()-money);
        tAccount.setMoney(tAccount.getMoney()+money);
       accountDao.update(sAccount);
//       int i=1/0;
       accountDao.update(tAccount);


    }
}
