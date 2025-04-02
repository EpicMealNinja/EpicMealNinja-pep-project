package Service;

import DAO.AccountDao;
import Model.Account;



public class AccountService {
    public AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account createAccount(Account acnt) {
        if (accountDao.getAccountByName(acnt) != null || acnt.getPassword().length() < 4 || acnt.getUsername() == "") return null;

        return accountDao.createAccount(acnt);
    }

    public Account loginAccount(Account acnt) {
        return accountDao.login(acnt);
    }

    public boolean accountExist(int id){
        Account acnt = new Account();
        acnt.setAccount_id(id);
        if (accountDao.getAccountByID(acnt) != null) return true;
        return false;
    }
}
