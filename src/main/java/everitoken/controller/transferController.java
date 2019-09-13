package everitoken.controller;

import everitoken.EveriTokenOperation.Action;
import everitoken.Utils.Func;
import everitoken.dao.impl.*;
import everitoken.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import everitoken.Utils.Func.*;

@Controller
@RequestMapping(value = "/transfer")
public class transferController{
   private Action action = new Action();
    @RequestMapping(value = "/sell/begin",method = RequestMethod.POST)
    @ResponseBody
    public Object Customer_transfer_Beginner(HttpSession httpSession, @RequestBody Map<String, Object> data){//发起者的ID，识别码.电池名称
        Integer ID ;
        Map<String,Object> res = new HashMap<>();
        String ID_code;
        if(data.containsKey("id")){
            ID = Integer.parseInt(data.get("id").toString());
        }
        else
        {
            res.put("code",10001);
            res.put("msg","没有用户ID");
            return res;
        }
        if(data.containsKey("ID_code")){
            ID_code=data.get("ID_code").toString();
        }
        else
        {
            res.put("code",10001);
            res.put("msg","识别码");
            return res;
        }
        if (!data.containsKey("BatteryName")){
            res.put("code",10001);
            res.put("msg","没有电池名称");
            return res;
        }
        Jedis jedis = Func.getRedis(0);
        if(jedis.exists(ID.toString() + "_confirm") && jedis.get(ID.toString() + "_confirm").equals("0")){
            res.put("code",10002);
            res.put("msg","您有一个订单未完成，请取消订单后再交易");
            return res;
        }
        jedis.setex(ID.toString() + "_ID_code", 5 * 60, ID_code);
        jedis.setex(ID.toString() + "_BatteryName",5 * 60,  data.get("BatteryName").toString());
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        jedis.setex(ID.toString() + "_BatterTime", 5 * 60, sdf.format(date));
        jedis.setex(ID.toString() + "_confirm",5 * 60, "0");
        res.put("code",0);
        res.put("msg","等待对方确认");
        return res;
    }
    @RequestMapping(value = "/sell/confirm",method = RequestMethod.POST)
    @ResponseBody
    public Object setPermission(@RequestBody Map<String,Object> data){//发起人的ID，接收人的ID，电池名称，识别码
        Map<String,Object> res = new HashMap<>();
        if(data.containsKey("starter_id")&&data.containsKey("checker_id")&&data.containsKey("Battery_name")){
            String Stater_ID = data.get("starter_id").toString();
            String Checker_ID = data.get("checker_id") .toString();
            String Battery_name = data.get("Battery_name").toString();
            Jedis jedis = Func.getRedis(0);
            if(jedis.exists(Stater_ID.toString() + "_confirm") && jedis.get(Stater_ID.toString() + "_confirm").equals("0"))
            {
                if(true)
                {
                    if(!jedis.get(Stater_ID + "_ID_code").equals(data.get("ID_code").toString())){
                        res.put("code",10003);
                        res.put("msg","识别码不匹配");
                        return res;
                    }
                    String privateKey;
                    String privateKey2;
                    String publicKey;
                    CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
                    ProducterRepositoryImpl producterRepository = new ProducterRepositoryImpl();
                    UserRepositoryImpl userRepository = new UserRepositoryImpl();
                    GovernmentRepositoryImpl governmentRepository = new GovernmentRepositoryImpl();
                    RecyclingStationRepositoryImpl recyclingStationRepository = new RecyclingStationRepositoryImpl();
                    UserEntity starter = userRepository.getById(Integer.parseInt(Stater_ID));
                    UserEntity checker = userRepository.getById(Integer.parseInt(Checker_ID));
                    if (starter == null || checker == null){
                        res.put("code", 10005);
                        res.put("msg", "用户不存在");
                        return res;
                    }
                    Object StarterEntity, CheckerEntity;
                    switch (checker.getType()){
                        case 0:
                            CheckerEntity = customerRepository.getById(checker.getInfoId());
                            if (CheckerEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey2 = ((CustomerEntity)CheckerEntity).getCustomerPrivateKey();
                            break;
                        case 1:
                            CheckerEntity = producterRepository.getById(checker.getInfoId());
                            if (CheckerEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey2 = ((ProducerEntity)CheckerEntity).getProducerPrivateKey();
                            break;
                        case 2:
                            CheckerEntity = governmentRepository.getById(checker.getInfoId());
                            if (CheckerEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey2 = ((GovernmentEntity)CheckerEntity).getGovernmentPrivateKey();
                            break;
                        case 3:
                            CheckerEntity = recyclingStationRepository.getById(checker.getInfoId());
                            if (CheckerEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey2 = ((RecyclingStationEntity)CheckerEntity).getRsPrivateKey();
                            break;
                        default:
                            res.put("code",10004);
                            res.put("msg","用户类型不对");
                            return res;
                    }
                    switch (starter.getType()){
                        case 0:
                            StarterEntity = customerRepository.getById(starter.getInfoId());
                            if (StarterEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey = ((CustomerEntity)StarterEntity).getCustomerPrivateKey();
                            break;
                        case 1:
                            StarterEntity = producterRepository.getById(starter.getInfoId());
                            if (StarterEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey = ((ProducerEntity)StarterEntity).getProducerPrivateKey();
                            break;
                        case 2:
                            StarterEntity = governmentRepository.getById(starter.getInfoId());
                            if (StarterEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey = ((GovernmentEntity)StarterEntity).getGovernmentPrivateKey();
                            break;
                        case 3:
                            StarterEntity = recyclingStationRepository.getById(starter.getInfoId());
                            if (StarterEntity == null){
                                res.put("code", 10005);
                                res.put("msg", "用户不存在");
                                return res;
                            }
                            privateKey = ((RecyclingStationEntity)StarterEntity).getRsPrivateKey();
                            break;
                            default:
                                res.put("code",10004);
                                res.put("msg","用户类型不对");
                                return res;
                    }



                    publicKey =action.toPublicKey(privateKey2);
                    if(action.transferBattery(Battery_name,privateKey,publicKey))
                    {
                        jedis.set(Stater_ID.toString() + "_confirm", "1");
                        res.put("code",0);
                        res.put("msg","交易成功");
                        BatteryEntity batteryEntity;
                        BatteryRepositoryImpl batteryRepository = new BatteryRepositoryImpl();
                        batteryEntity = batteryRepository.getById(Battery_name);
                        batteryEntity.setBatteryChgCycles(batteryEntity.getBatteryChgCycles()+1);
                        batteryRepository.update(batteryEntity);
                        return res;
                    }
                    else
                    {
                        res.put("code",11001);
                        res.put("msg","交易失败");
                        return res;
                    }
                }
            }
        }
        res.put("code", 10007);
        res.put("msg", "订单不存在");
        return res;
    }

    @RequestMapping(value = "/sell/cancel",method = RequestMethod.POST)
    @ResponseBody
    public Object Cancel(@RequestBody Map<String,Object> data){//用户id
        Map<String,Object> res = new HashMap<>();
        Jedis jedis = Func.getRedis(0);
        if(data.containsKey("id") && jedis.exists(data.get("id").toString() + "_ID_code"))
        {
            String ID_code = jedis.get(data.get("id").toString() + "_ID_code");
            jedis.set(data.get("id").toString() + "_confirm", "1");
            res.put("code",0);
            res.put("msg","成功");
        }
        else
        {
            res.put("code",10001);
            res.put("msg","没有用户ID");
            return res;
        }
        return res;
    }

    @RequestMapping(value = "/sell/GetTransactionInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object GetTransactionInfo(@RequestBody Map<String,Object>data){
        Map<String,Object> res=new HashMap<>();
        int ID = 0;
        Jedis jedis = Func.getRedis(0);
        if(data.containsKey("id")){
            ID = Integer.parseInt(data.get("id").toString());
        }
        else
        {
            res.put("code",10001);
            res.put("msg","没有用户ID");
            return res;
        }
        if(!data.containsKey("ID_code")){
            res.put("code",10001);
            res.put("msg","识别码");
            return res;
        }
        else{
            if (Integer.parseInt(jedis.get(ID+"_confirm"))==1){
                res.put("code",10007);
                res.put("msg","订单不存在");
                return res;
            }
            String ID_code=data.get("ID_code").toString();
            res.put("BatteryName", jedis.get(ID + "_BatteryName"));
            res.put("ID_code", jedis.get(ID + "_ID_code"));
        }
        return res;
    }

}

