package everitoken.controller;

import EveritokenSDK.Action;
import everitoken.dao.CustomerRepository;
import everitoken.dao.impl.CustomerRepositoryImpl;
import everitoken.entity.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/transfer")
public class transferController{
   private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private Map<String,Object> Condition = new HashMap<>();
   private Map<String, Date> timeMap = new HashMap<>();
   private Action action = new Action();
    @RequestMapping(value = "/sell",method = RequestMethod.POST)
    @ResponseBody
    public Object Customer_transfer_Beginner(@RequestBody Map<String,Object> data){//发起者的ID，识别码
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
        if(Condition.containsKey(ID)){
            res.put("code",10002);
            res.put("msg","您有一个订单未完成，请取消订单后再交易");
            return res;
        }
        Condition.put(ID.toString(),ID_code);
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

            if(Condition.containsKey(Stater_ID))
            {
                if(timeMap.containsKey(Stater_ID)&&expired(timeMap.get(Stater_ID),new Date(),Stater_ID))
                {
                    if(Condition.get(Stater_ID).equals(data.get("ID_code"))){
                        res.put("code",10003);
                        res.put("msg","识别码不匹配");
                        return res;
                    }
                    String privateKey;
                    String publicKey;
                    CustomerRepository customerRepository = new CustomerRepositoryImpl();
                    CustomerEntity StarterEntity;
                    CustomerEntity CheckerEntity;
                    StarterEntity = customerRepository.get(Integer.parseInt(Stater_ID));
                    CheckerEntity = customerRepository.get(Integer.parseInt(Checker_ID));
                    privateKey = StarterEntity.getCustomerPrivateKey();
                    publicKey =action.toPublicKey(CheckerEntity.getCustomerPrivateKey());
                    if(action.transferBattery(Battery_name,privateKey,publicKey))
                    {
                        Condition.remove(Stater_ID);
                        res.put("code",0);
                        res.put("msg","交易成功");
                        return res;
                    }
                    else
                    {
                        res.put("code",11001);
                        res.put("msg","交易失败");
                    }
                }
            }
        }
        return false;
    }

    @RequestMapping(value = "/sell/Cancel",method = RequestMethod.POST)
    @ResponseBody
    public Object Cancel(@RequestBody Map<String,Object> data){//用户id
        Map<String,Object> res = new HashMap<>();
        if(data.containsKey("id"))
        {
            Condition.remove(data.get("id").toString());
            timeMap.remove(data.get("id").toString());
            res.put("code",1);
            res.put("mes","成功");
        }
        else
        {
            res.put("code",10001);
            res.put("mes","没有用户ID");
            return res;
        }
        return res;
    }
    private boolean expired(Date OldDate, Date NewDate,String ID){//查看交易是否过期
        long Old =  OldDate.getTime();
        long New =  NewDate.getTime();
        if((New-Old)<=1000*60*2){
            timeMap.remove(ID);
            //Condition.remove(ID);
            return true;
        }
        return false;
    }
}

