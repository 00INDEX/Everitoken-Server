package everitoken.controller;

import everitoken.dao.GovernmentRepository;
import everitoken.dao.RecyclingStationRepository;
import everitoken.dao.impl.*;
import everitoken.entity.*;
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
    private GovernmentRepositoryImpl governmentRepository;
    private RecyclingStationRepositoryImpl recyclingStationRepository;
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
        if (data.containsKey("sex")) customerEntity.setCustomerSex(Integer.parseInt(data.get("sex").toString()));
        if (data.containsKey("idnumber")) customerEntity.setCustomerIdnumber(data.get("idnumber").toString());
        if (data.containsKey("phone")) customerEntity.setCustomerPhone(data.get("phone").toString());
        userEntity.setType(0);
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
     */
    @RequestMapping(value = "/producer/register", method = RequestMethod.POST)
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
        if (!data.containsKey("username") || !data.containsKey("producer_name") || !data.containsKey("password") || !data.containsKey("email") || !data.containsKey("producer_CHNCode")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        if (data.containsKey("producer_name")) producerEntity.setProducerName(data.get("producer_name").toString());
        if (data.containsKey("username")) userEntity.setUsername(data.get("username").toString());
        if (data.containsKey("password")) userEntity.setPassword(data.get("password").toString());
        if (data.containsKey("email")) userEntity.setEmail(data.get("email").toString());
        if(data.containsKey("producer_CHNCode")) producerEntity.setProducerChnCode(data.get("producer_CHNCode").toString());
        userEntity.setType(1);
        //if (data.containsKey("producer_private_key")) producerEntity.setProducerPrivateKey(data.get("producer_private_key").toString());
        /**
         * todo 完善权限系统之后再编辑此区域
        if (data.containsKey("authorized"))
        {
            Integer value=Integer.parseInt(data.get("authorized").toString());
            producerEntity.setProducerAuthorized(value.byteValue());
        }
         */
        int uid = -1;
        try {
            uid = producterRepository.add(producerEntity);
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
     * 政府注册方法
     * @param httpSession session数据
     * @param data 政府数据
     * @return
     */
    @RequestMapping(value = "/government/register", method = RequestMethod.POST)
    @ResponseBody
    public Object government_register(HttpSession httpSession, @RequestBody Map<String, Object> data){
        res = new HashMap<>();
        GovernmentEntity governmentEntity = new GovernmentEntity();
        UserEntity userEntity = new UserEntity();
        governmentRepository = new GovernmentRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        if (data.containsKey("username")){
            UserEntity oldUser = userRepository.getByUsername((String)data.get("username"));
            if (oldUser != null) {
                res.put("code", 20006);
                res.put("msg", "用户名重复");
                return res;
            }
        }
        if (!data.containsKey("username") || !data.containsKey("government_name") || !data.containsKey("password") || !data.containsKey("email") || !data.containsKey("government_CHNCode")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        if (data.containsKey("government_name")) governmentEntity.setGovernmentName(data.get("government_name").toString());
        if (data.containsKey("username")) userEntity.setUsername(data.get("username").toString());
        if (data.containsKey("password")) userEntity.setPassword(data.get("password").toString());
        if (data.containsKey("email")) userEntity.setEmail(data.get("email").toString());
        if(data.containsKey("uid")) userEntity.setUid(Integer.parseInt(data.get("uid").toString()));
        //if (data.containsKey("government_private_key")) governmentEntity.setGovernmentPrivateKey(data.get("government_private_key").toString());
        if(data.containsKey("government_CHNCode")) governmentEntity.setGovernmentChnCode(data.get("government_CHNCode").toString());
        userEntity.setType(2);
        int uid = -1;
        try {
            uid = governmentRepository.add(governmentEntity);
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
     * 回收站注册方法
     * @param httpSession session数据
     * @param data 回收站数据
     * @return
     */

    @RequestMapping(value = "/recycling_station/register", method = RequestMethod.POST)
    @ResponseBody
    public Object recycling_station_register(HttpSession httpSession, @RequestBody Map<String, Object> data){
        res = new HashMap<>();
        RecyclingStationEntity recylingStationEntity = new RecyclingStationEntity();
        UserEntity userEntity = new UserEntity();
        recyclingStationRepository=new RecyclingStationRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        if (data.containsKey("username")){
            UserEntity oldUser = userRepository.getByUsername((String)data.get("username"));
            if (oldUser != null) {
                res.put("code", 20006);
                res.put("msg", "用户名重复");
                return res;
            }
        }
        if (!data.containsKey("username") || !data.containsKey("rs_name") || !data.containsKey("password") || !data.containsKey("email")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        if (data.containsKey("rs_name")) recylingStationEntity.setRsName(data.get("rs_name").toString());
        if (data.containsKey("username")) userEntity.setUsername(data.get("username").toString());
        if (data.containsKey("password")) userEntity.setPassword(data.get("password").toString());
        if (data.containsKey("email")) userEntity.setEmail(data.get("email").toString());
        /**
         * todo 加入everitoken之后再编辑此区域
        if (data.containsKey("rs_private_key")) recylingStationEntity.setRsPrivateKey(data.get("rs_private_key").toString());
         */
        userEntity.setType(3);
        int uid = -1;
        try {
            uid =recyclingStationRepository.add(recylingStationEntity);
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
                httpSession.setAttribute("uid", userEntity.getUid());
                res.put("code", 0);
                res.put("msg", "success");
                res.put("type", userEntity.getType());
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
     * 查询用户数据
     * @param httpSession session数据
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object getUserInfo(HttpSession httpSession){
        res = new HashMap<>();


        Map<String, Object> info = new HashMap<>();
        if(httpSession.getAttribute("uid") == null || (int)httpSession.getAttribute("uid") <= 0){
            res.put("code", 20003);
            res.put("msg", "用户没有登陆");
            return res;
        }


        int uid = (int)httpSession.getAttribute("uid");
        try {
            UserEntity userEntity = userRepository.getById(uid);
            info.put("email", userEntity.getEmail());
            info.put("username", userEntity.getUsername());
            info.put("type", userEntity.getType());
            switch (userEntity.getType()){
                case 0:
                    CustomerEntity customerEntity = customerRepository.getById((int)userEntity.getInfoId());
                    info.put("name", customerEntity.getCustomerName());
                    info.put("phone", customerEntity.getCustomerPhone());
                    info.put("sex", customerEntity.getCustomerSex());
                    break;
                case 1:
                    ProducerEntity producerEntity = producterRepository.getById((int)userEntity.getInfoId());
                    info.put("producer_name", producerEntity.getProducerName());
                    info.put("producer_CHNCode", producerEntity.getProducerChnCode());
                    break;
                case 2:
                    GovernmentEntity governmentEntity = governmentRepository.getById((int)userEntity.getInfoId());
                    info.put("government_name", governmentEntity.getGovernmentName());
                    info.put("government_CHNCode", governmentEntity.getGovernmentChnCode());
                    break;
                case 3:
                    RecyclingStationEntity recyclingStationEntity = recyclingStationRepository.getById((int)userEntity.getInfoId());
                    info.put("rs_name", recyclingStationEntity.getRsName());
                    break;
            }
            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", info);
            return res;
        }catch (Exception e){
            res.put("code", 20002);
            res.put("msg", e.getMessage());
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
