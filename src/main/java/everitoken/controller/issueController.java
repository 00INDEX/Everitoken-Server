package everitoken.controller;


import everitoken.EveriTokenOperation.Action;
import everitoken.dao.impl.BatteryRepositoryImpl;
import everitoken.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static everitoken.Operations.Operate.*;
import static everitoken.Operations.Operate.GetProducerInfo;

@Controller
@RequestMapping(value = "/Manage")
public class issueController {
    @RequestMapping(value = "/issueBattery",method = RequestMethod.POST)
    @ResponseBody
    public Object IssueBattery(@RequestBody Map<String,Object>data){//function:发行电池 need:生产商ID、电池名称、电池类型,电池循环充放电次数
        Map<String,Object> res=new HashMap<>();
        String batteryName;
        Action action = new Action();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","缺少用户id");
            return res;
        }

        if(!data.containsKey("type")
                ||!data.containsKey("batteryMaxVoltage")
                ||!data.containsKey("batteryAverageTemperature")
                ||!data.containsKey("batteryChgCycles"))
        {
            res.put("code",10001);
            res.put("msg","缺少电池信息");
            return res;
        }


        Integer ID = Integer.parseInt(data.get("id").toString());
        ProducerEntity producerEntity = GetProducer(ID);
        if(producerEntity==null){
            res.put("code",10005);
            res.put("msg","id不存在数据库");
            return res;
        }
        if (producerEntity.getProducerAuthorized()==0){
            res.put("code",10006);
            res.put("msg","该用户未被授权");
            return res;
        }
        batteryName = producerEntity.getProducerUid() + UUID.randomUUID().toString();
        batteryName.replaceAll("-", "");
        batteryName = batteryName.substring(0,19);
        int count=0;
        while(!action.issueBattery(action.toPublicKey(producerEntity.getProducerPrivateKey()),
                batteryName)&&count<10){
            count++;
        }
        if(count>=10){
            res.put("code",11002);
            res.put("msg","电池发行失败");
            return res;
        }
        BatteryEntity batteryEntity = new BatteryEntity();
        BatteryRepositoryImpl batteryRepository = new BatteryRepositoryImpl();
        if(data.containsKey("type")) batteryEntity.setBatteryType(Integer.parseInt(data.get("type").toString()));
        if(data.containsKey("BatteryName")) batteryEntity.setUid(Integer.parseInt(data.get("BatteryName").toString()));
        if(data.containsKey("batteryMaxVoltage")) batteryEntity.setBatteryMaxVoltage(data.get("batteryMaxVoltage").toString());
        if(data.containsKey("batteryAverageTemperature")) batteryEntity.setBatteryAverageTemperature(data.get("batteryAverageTemperature").toString());
        if(data.containsKey("batteryChgCycles")) batteryEntity.setBatteryChgCycles(Integer.parseInt(data.get("batteryChgCycles").toString()));
        batteryEntity.setBatteryName(batteryName);
        batteryEntity.setBatteryCapacity(data.get("batteryMaxVoltage").toString());
        batteryRepository.save(batteryEntity);
        res.put("code",0);
        res.put("msg","success");
        return res;
    }
    @RequestMapping(value = "/DestroyBattery",method = RequestMethod.POST)
    @ResponseBody
    public Object DestroyBattery(@RequestBody Map<String,Object>data){//function:销毁电池;need:电池名称(id),用户ID
        Map<String,Object> res=new HashMap<>();
        Action action=new Action();
        if(!data.containsKey("id")||!data.containsKey("info_id")||!data.containsKey("type"))
        {
            res.put("code",10001);
            res.put("msg","没有ID或者用户ID或者用户类型");
            return res;
        }
        Integer type =Integer.parseInt(data.get("type").toString());
        Integer ID = Integer.parseInt(data.get("info_id").toString());
        String privateKey = null;
        switch (type) {
            case 0: {
                CustomerEntity customerEntity = GetCustomer(ID);
                if (customerEntity == null)
                    return IDExist();
                privateKey = customerEntity.getCustomerPrivateKey();
                break;
            }
            case 2: {
                GovernmentEntity governmentEntity = GetGovernment(ID);
                if (governmentEntity == null)
                    return IDExist();
                privateKey = governmentEntity.getGovernmentPrivateKey();
                break;
            }
            case 3: {
                RecyclingStationEntity recyclingStationEntity = GetRS(ID);
                if (recyclingStationEntity == null)
                    return IDExist();
                privateKey = recyclingStationEntity.getRsPrivateKey();
                break;
            }
            case 1: {
                ProducerEntity producerEntity = GetProducer(ID);
                if (producerEntity == null) {
                    return IDExist();
                }
                privateKey = producerEntity.getProducerPrivateKey();
                break;
            }
        default:
            res.put("code", 10004);
            res.put("msg", "用户类型不对");
            return res;
        }
        if(!action.destroyBattery(data.get("id").toString(),privateKey))
        {
            res.put("code",11005);
            res.put("msg","电池销毁失败");
            return res;
        }
        res.put("code",0);
        res.put("msg","success");
        return res;
    }
    @RequestMapping(value = "/Test",method = RequestMethod.POST)
    @ResponseBody
    public Object Test(@RequestBody Map<String,Object>data){
        data.put("id",55555);

        return data;
    }
}
