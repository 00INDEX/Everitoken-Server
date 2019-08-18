package everitoken.controller;

import everitoken.dao.impl.CustomerRepositoryImpl;
import everitoken.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private CustomerRepositoryImpl customerRepository;
    private Map<String, Object> res;

    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object> test(){
        Configuration cfg;
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerEmail("111111@1111.111");
        customerEntity.setCustomerPassword("2222222");
        customerEntity.setCustomerName("ces");
        customerEntity.setCustomerSex(0);
        customerEntity.setCustomerIdnumber("11111");
        customerEntity.setCustomerPrivateKey("1111");

        session.save(customerEntity);
        transaction.commit();
        session.close();
        sessionFactory.close();

        res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "success");
        return res;
    }

    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    @ResponseBody
    public Object customer_register(@RequestBody Map<String, Object> data){
        res = new HashMap<>();
        CustomerEntity customerEntity = new CustomerEntity();
        customerRepository = new CustomerRepositoryImpl();
        if (!data.containsKey("name") || !data.containsKey("password") || !data.containsKey("email") || !data.containsKey("sex") || !data.containsKey("idnumber")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        if (data.containsKey("name")) customerEntity.setCustomerName(data.get("name").toString());
        if (data.containsKey("password")) customerEntity.setCustomerPassword(data.get("password").toString());
        if (data.containsKey("email")) customerEntity.setCustomerEmail(data.get("email").toString());
        if (data.containsKey("sex")) customerEntity.setCustomerSex(Integer.parseInt(data.get("sex").toString()));
        if (data.containsKey("idnumber")) customerEntity.setCustomerIdnumber(data.get("idnumber").toString());
        try {
            customerRepository.add(customerEntity);
        }catch (Exception e){
            res.put("code", 20002);
            res.put("msg", "服务器异常");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "success");
        return res;
    }
}
