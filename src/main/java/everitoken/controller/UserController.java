package everitoken.controller;

import everitoken.dao.impl.CustomerRepositoryImpl;
import everitoken.dao.impl.ProducterRepositoryImpl;
import everitoken.dao.impl.UserRepositoryImpl;
import everitoken.entity.CustomerEntity;
import everitoken.entity.ProducerEntity;
import everitoken.entity.UserEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private CustomerRepositoryImpl customerRepository;
    private UserRepositoryImpl userRepository;
    private ProducterRepositoryImpl producterRepository;
    private Map<String, Object> res;
    /**
     * 客户注册方法
     * @param httpSession session数据
     * @param data 用户数据
     * @return
     */
    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    @ResponseBody
    public Object customer_register(HttpSession httpSession, @RequestBody Map<String, Object> data){
        res = new HashMap<>();
        CustomerEntity customerEntity = new CustomerEntity();
        UserEntity userEntity = new UserEntity();
        customerRepository = new CustomerRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        if (data.containsKey("username")){
            UserEntity oldUser = userRepository.getByUsername((String)data.get("username"));
            if (oldUser != null) {
                res.put("code", 20006);
                res.put("msg", "用户名重复");
                return res;
            }
        }
        if (!data.containsKey("username") || !data.containsKey("name") || !data.containsKey("password") || !data.containsKey("email") || !data.containsKey("sex") || !data.containsKey("idnumber") || !data.containsKey("phone")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        if (data.containsKey("name")) customerEntity.setCustomerName(data.get("name").toString());
        if (data.containsKey("username")) userEntity.setUsername(data.get("username").toString());
        if (data.containsKey("password")) userEntity.setPassword(data.get("password").toString());
        if (data.containsKey("email")) userEntity.setEmail(data.get("email").toString());
        if (data.containsKey("type")) userEntity.setType((Integer)data.get("email"));
        if (data.containsKey("sex")) customerEntity.setCustomerSex(Integer.parseInt(data.get("sex").toString()));
        if (data.containsKey("idnumber")) customerEntity.setCustomerIdnumber(data.get("idnumber").toString());
        if (data.containsKey("phone")) customerEntity.setCustomerPhone(data.get("phone").toString());
        int uid = -1;
        try {
            uid = customerRepository.add(customerEntity);
            userEntity.setInfoId(uid);
            uid = userRepository.add(userEntity);
        }catch (Exception e){
            res.put("code", 20002);
            res.put("msg", e.getMessage());
            return res;
        }
        httpSession.setAttribute("uid", uid);
        res.put("code", 0);
        res.put("msg", "success");
        return res;
    }

    /**
     * 商家注册方法
     * @param httpSession session数据
     * @param data 用户数据
     * @return

    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    @ResponseBody
    public Object producer_register(HttpSession httpSession, @RequestBody Map<String, Object> data){
        res = new HashMap<>();
        ProducerEntity producerEntity = new ProducerEntity();
        UserEntity userEntity = new UserEntity();
        producterRepository = new ProducterRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        if (data.containsKey("username")){
            UserEntity oldUser = userRepository.getByUsername((String)data.get("username"));
            if (oldUser != null) {
                res.put("code", 20006);
                res.put("msg", "用户名重复");
                return res;
            }
        }
        if (!data.containsKey("username") || !data.containsKey("name") || !data.containsKey("password") || !data.containsKey("email") || !data.containsKey("sex") || !data.containsKey("idnumber") || !data.containsKey("phone")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        if (data.containsKey("name")) customerEntity.setCustomerName(data.get("name").toString());
        if (data.containsKey("username")) userEntity.setUsername(data.get("username").toString());
        if (data.containsKey("password")) userEntity.setPassword(data.get("password").toString());
        if (data.containsKey("email")) userEntity.setEmail(data.get("email").toString());
        if (data.containsKey("type")) userEntity.setType((Integer)data.get("email"));
        if (data.containsKey("sex")) customerEntity.setCustomerSex(Integer.parseInt(data.get("sex").toString()));
        if (data.containsKey("idnumber")) customerEntity.setCustomerIdnumber(data.get("idnumber").toString());
        if (data.containsKey("phone")) customerEntity.setCustomerPhone(data.get("phone").toString());
        int uid = -1;
        try {
            uid = customerRepository.add(customerEntity);
            userEntity.setInfoId(uid);
            uid = userRepository.add(userEntity);
        }catch (Exception e){
            res.put("code", 20002);
            res.put("msg", e.getMessage());
            return res;
        }
        httpSession.setAttribute("uid", uid);
        res.put("code", 0);
        res.put("msg", "success");
        return res;
    }
     */
    /**
     *
     * 用户登陆事件
     * @param httpSession session数据
     * @param data 登陆信息
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpSession httpSession, @RequestBody Map<String, Object> data){
        res = new HashMap<>();
        CustomerEntity customerEntity;
        customerRepository = new CustomerRepositoryImpl();
        UserEntity userEntity;
        userRepository = new UserRepositoryImpl();
        if (data.containsKey("username") && data.containsKey("password")){
            userEntity = userRepository.getByUsername((String) data.get("username"));
            if(userEntity == null){
                res.put("code", 20005);
                res.put("msg", "该用户不存在");
                return res;
            }
            if (userEntity.getPassword().equals((String)data.get("password"))){
                httpSession.setAttribute("customer_uid", userEntity.getUid());
                res.put("code", 0);
                res.put("msg", "success");
                return res;
            }else{
                res.put("code", 20004);
                res.put("msg", "用户名或者密码错误");
                return res;
            }
        }else{
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
    }

    /**
     * 用户未进行登陆操作时调用该方法
     * @param httpSession session数据
     * @return
     */
    @RequestMapping(value = "/notlogin")
    @ResponseBody
    public Object not_login(HttpSession httpSession){
        res = new HashMap<>();
        res.put("code", 20003);
        res.put("msg", "用户没有登陆");
        return res;
    }
}
