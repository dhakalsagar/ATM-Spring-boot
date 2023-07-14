//package com.example.ATM.user;
//
//import com.example.ATM.account.Account;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//import jakarta.persistence.PersistenceUnit;
//import jakarta.persistence.PersistenceUnitUtil;
//import jakarta.persistence.PostPersist;
//
//public class UserInfoListener {
//    @PersistenceUnit
//    private static EntityManagerFactory emf;
//
//    @PostPersist
//    public void createAccount(UserInfo userInfo) {
//        EntityManager em = getEntityManager();
//        PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
//
//        if (unitUtil.isLoaded(userInfo.getAccountNumber()) && userInfo.getAccountNumber() != null) {
//            Account account = new Account();
//            account.setAccountNumber(userInfo.getAccountNumber());
//            account.setUserInfo(userInfo);
//            account.setRemainingBalance(0.0); // Set default remaining balance
//
//            EntityTransaction transaction = em.getTransaction();
//            transaction.begin();
//            em.persist(account);
//            transaction.commit();
//        }
//        em.close();
//    }
//
//    private EntityManager getEntityManager() {
//        if (emf == null) {
//            emf = Persistence.createEntityManagerFactory("ATM_DB");
//        }
//        return emf.createEntityManager();
//    }
//}