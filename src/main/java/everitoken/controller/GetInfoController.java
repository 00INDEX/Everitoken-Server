package everitoken.controller;

import EveritokenSDK.Action;
import EveritokenSDK.Info;
import com.sun.corba.se.impl.logging.InterceptorsSystemException;
import everitoken.Operations.Operate;
import everitoken.dao.*;
import everitoken.dao.impl.*;
import everitoken.entity.*;
import javafx.beans.property.ObjectProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static everitoken.Operations.Operate.*;

@Controller
@RequestMapping(value = "/Info")
public class GetInfoController {
    @RequestMapping(value = "/PastOwner",method = RequestMethod.POST)
    @ResponseBody
    public Object GetPastOwner(@RequestBody Map<String,Object> data)//function：获得一个电池的交易记录 need：电池名称
    {
        Map<String,Object> res=new HashMap<>();
        Info info=new Info();
        String[] PastOwner;
        if(data.containsKey("Battery_Name"))
        PastOwner=info.getSource(data.get("Battery_Name").toString());
        else
        {
            res.put("code",10001);
            res.put("msg","没有电池名称");
            return res;
        }
        if(PastOwner==null){
            res.put("code",11003);
            res.put("msg","溯源失败");
            return res;
        }

        res.put("code",0);
        res.put("msg",ToUserName(PastOwner));
        return res;
    }
    @RequestMapping(value = "/AuthorizeRecordList",method = RequestMethod.POST)
    @ResponseBody
    public Object GetAuthorizedRecordList(@RequestBody Map<String,Object>data){//function:获取授权记录 need:授权者ID
        Map<String,Object> res=new HashMap<>();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","没有ID");
            return res;
        }
        ProcessRepository processRepository = new ProcessRepositoryImpl();
        List<ProcessEntity> processEntities = processRepository.getByPId(Integer.parseInt(data.get("id").toString()));
        if(processEntities==null||processEntities.size()==0){
            res.put("code",10005);
            res.put("msg","ID不存在");
            return res;
        }
        int i=processEntities.size();
        int j;
        for(j=0;j<i;j++){
            res.put(""+j+"",Operate.GetProcessInfo(processEntities.get(j)));
        }
        return res;
    }
    @RequestMapping(value = "/AuthorizeRecord",method = RequestMethod.POST)
    @ResponseBody
    public Object GetAuthorizedRecord(@RequestBody Map<String,Object>data){//function:获取授权记录 need:授权记录ID
        Map<String,Object> res = new HashMap<>();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","没有ID");
            return res;
        }
        ProcessEntity processEntity = GetProcess(Integer.parseInt(data.get("id").toString()));
        if(processEntity==null){
            res.put("code",10005);
            res.put("msg","ID不存在");
            return res;
        }
        return Operate.GetProcessInfo(processEntity);
    }
    @RequestMapping(value = "/ApplicationInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object GetApplicationInfo(@RequestBody Map<String,Object>data){//function:获取申请信息，需要申请ID
        Map<String,Object> res=new HashMap<>();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","没有ID");
            return res;
        }
        Integer ID= Integer.parseInt(data.get("id").toString());
        ApplicationEntity applicationEntity=GetApplication(ID);
        if(applicationEntity==null){
            return IDExist();
        }
        return Operate.GetApplicationInfo(applicationEntity);
    }

    @RequestMapping(value = "/ApplicationInfoInMass",method = RequestMethod.POST)
    @ResponseBody
    public Object GetApplicationInfoInMass(@RequestBody Map<String,Object>data){//function:获取某个申请者的所有申请;need：申请者ID
        Map<String,Object> res=new HashMap<>();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","没有ID");
            return res;
        }
        ApplicationRepository applicationRepository = new ApplicationRepositoryImpl();
        List<ApplicationEntity> applicationEntities = applicationRepository.getByAId(Integer.parseInt(data.get("id").toString()));
        if(applicationEntities==null){
            res.put("code",10005);
            res.put("msg","ID不存在");
            return res;
        }
        int i=applicationEntities.size();
        int j;
        for(j=0;j<i;j++){
            res.put(""+j+"",Operate.GetApplicationInfo(applicationEntities.get(j)));
        }
        return res;
    }

    @RequestMapping(value = "/BasicInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object GetBasicInfo(@RequestBody Map<String,Object>data){//function:获取用户基础信息;need:用户id，用户类型
        Map<String,Object> res=new HashMap<>();
        Integer type;
        Integer ID;
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","缺少ID");
            return res;
        }
        if(!data.containsKey("type")){
            res.put("code",10001);
            res.put("msg","缺少分类");
            return res;
        }
        type = Integer.parseInt(data.get("type").toString());
        ID = Integer.parseInt(data.get("id").toString());
        switch (type){
            case 1:{
                CustomerEntity customerEntity = GetCustomer(ID);
                if(customerEntity==null)
                    return IDExist();
                return GetCustomInfo(customerEntity);
            }
            case 2:{
                GovernmentEntity governmentEntity=GetGovernment(ID);
                if(governmentEntity==null)
                    return IDExist();
                return GetGovernmentInfo(governmentEntity);
            }
            case 3:{
                RecyclingStationEntity recyclingStationEntity = GetRS(ID);
                if(recyclingStationEntity==null){
                    return IDExist();
                }
                return GetRSInfo(recyclingStationEntity);
            }
            case 4:{
                ProducerEntity producerEntity=GetProducer(ID);
                if(producerEntity==null){
                    return IDExist();
                }
                return GetProducerInfo(producerEntity);
            }
        }
        res.put("code",10004);
        res.put("msg","用户类型不对");
        return res;
    }
    @RequestMapping(value = "/BatteryInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object GetBatteryInfo(@RequestBody Map<String,Object>data){//function:获取电池信息 need:电池ID
        Map<String,Object> res=new HashMap<>();
        if(!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","没有电池ID");
            return res;
        }
        Integer ID=Integer.parseInt(data.get("id").toString());
        BatteryEntity batteryEntity = GetBattery(ID);
        if(batteryEntity==null)
            return IDExist();
        return Operate.GetBatteryInfo(batteryEntity);
    }
    @RequestMapping(value = "/possessBattery",method = RequestMethod.POST)
    @ResponseBody
    public Object GetPossessBattery(@RequestBody Map<String,Object>data) {//function:获得个人拥有的所有电池;need:持有者ID,用户类型
        Map<String, Object> res = new HashMap<>();
        Info info = new Info();
        Action action = new Action();
        if (!data.containsKey("id")) {
            res.put("code", 10001);
            res.put("msg", "缺少ID");
            return res;
        }
        if (!data.containsKey("type")) {
            res.put("code", 10001);
            res.put("msg", "缺少用户类型");
            return res;
        }
        Integer type = Integer.parseInt(data.get("type").toString());
        Integer ID = Integer.parseInt(data.get("id").toString());
        String[] Batteries=null;
        switch (type) {
            case 1: {
                CustomerEntity customerEntity = GetCustomer(ID);
                if(customerEntity==null)
                    return IDExist();
                Batteries = info.getTokens(action.toPublicKey(customerEntity.getCustomerPrivateKey()));
                break;
            }
            case 2: {
                GovernmentEntity governmentEntity = GetGovernment(ID);
                if(governmentEntity==null)
                    return IDExist();
                Batteries = info.getTokens(action.toPublicKey(governmentEntity.getGovernmentPrivateKey()));
                break;
            }
            case 3: {
                RecyclingStationEntity recyclingStationEntity = GetRS(ID);
                if(recyclingStationEntity==null)
                    return IDExist();
                Batteries = info.getTokens(action.toPublicKey(recyclingStationEntity.getRsPrivateKey()));
                break;
            }
            case 4: {
                ProducerEntity producerEntity = GetProducer(ID);
                if(producerEntity==null)
                    return IDExist();
                Batteries = info.getTokens(action.toPublicKey(producerEntity.getProducerPrivateKey()));
                break;
            }

        }
        if(Batteries == null) {
            res.put("code", 11004);
            res.put("msg", "查询电池所有者失败");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "成功返回数据");
        res.put("Batteries", Batteries);

        return res;
    }

}
