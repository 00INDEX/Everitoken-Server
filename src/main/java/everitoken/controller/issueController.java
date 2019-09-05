package everitoken.controller;

import everitoken.EveriTokenOperation.Action;
import everitoken.Operations.Operate;
import everitoken.dao.BatteryRepository;
import everitoken.dao.impl.BatteryRepositoryImpl;
import everitoken.entity.BatteryEntity;
import everitoken.entity.ProducerEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static everitoken.Operations.Operate.GetProducer;

@RequestMapping(value = "/Manage")
public class issueController {
    @RequestMapping(value = "/issueBattery",method = RequestMethod.POST)
    @ResponseBody
    public Object IssueBattery(@RequestBody Map<String,Object>data){//function:发行电池 need:生产商ID、电池名称、电池类型,电池循环充放电次数
        Map<String,Object> res=new HashMap<>();
        String BatteryName;
        Action action = new Action();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","缺少用户id");
            return res;
        }
        if(!data.containsKey("BatteryName")){
            res.put("code",10001);
            res.put("msg","缺少电池名称");
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

        BatteryName = data.get("BatteryName").toString();
        Integer ID = Integer.parseInt(data.get("id").toString());
        ProducerEntity producerEntity = GetProducer(ID);
        if(producerEntity==null){
            res.put("code",10005);
            res.put("msg","id不存在数据库");
            return res;
        }
        if (producerEntity.getProducerAuthorized().equals("false")){
            res.put("code",10006);
            res.put("msg","该用户未被授权");
            return res;
        }
        if(!action.issueBattery(action.toPublicKey(producerEntity.getProducerPrivateKey()),
                producerEntity.getProducerPrivateKey(),BatteryName)){
            res.put("code",11002);
            res.put("msg","电池发行失败");
            return res;
        }
        BatteryEntity batteryEntity = new BatteryEntity();
        BatteryRepository batteryRepository = new BatteryRepositoryImpl();
        if(data.containsKey("type")) batteryEntity.setBatteryType(Integer.parseInt(data.get("type").toString()));
        if(data.containsKey("BatteryName")) batteryEntity.setUid(Integer.parseInt(data.get("BatteryName").toString()));
        if(data.containsKey("batteryMaxVoltage")) batteryEntity.setBatteryMaxVoltage(data.get("batteryMaxVoltage").toString());
        if(data.containsKey("batteryAverageTemperature")) batteryEntity.setBatteryAverageTemperature(data.get("batteryAverageTemperature").toString());
        if(data.containsKey("batteryChgCycles")) batteryEntity.setBatteryChgCycles(Integer.parseInt(data.get("batteryChgCycles").toString()));

        // batteryRepository.add(batteryEntity);

        return res;
    }
    @RequestMapping(value = "/DestroyBattery",method = RequestMethod.POST)
    @ResponseBody
    public Object DestroyBattery(@RequestBody Map<String,Object>data){//function:销毁电池;need:电池名称(id)
        Map<String,Object> res=new HashMap<>();
        Action action=new Action();
        if(!data.containsKey("id"))
        {
            res.put("code",10001);
            res.put("msg","没有ID");
            return res;
        }
        if(!action.destroyBattery(data.get("id").toString()))
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
