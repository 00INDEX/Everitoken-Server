package everitoken.controller;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.sun.xml.bind.v2.model.core.ID;
import everitoken.EveriTokenOperation.Action;
import everitoken.EveriTokenOperation.Info;
import everitoken.Operations.BatteryInfos;
import everitoken.Operations.Operate;
import everitoken.dao.*;
import everitoken.dao.impl.*;
import everitoken.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static everitoken.Operations.Operate.*;

@Controller
@RequestMapping(value = "/Info")
public class GetInfoController {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @RequestMapping(value = "/PastOwner",method = RequestMethod.POST)
    @ResponseBody
    public Object GetPastOwner(@RequestBody Map<String,Object> data)//function：获得一个电池的交易记录 need：电池名称
    {
        Map<String,Object> res=new HashMap<>();
        BatteryInfos batteryInfos=new BatteryInfos();
        Info info=new Info();
        String[] PastOwner;
        if(data.containsKey("BatteryName"))
        PastOwner=info.getSource(data.get("BatteryName").toString());
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
        List<String> Details=new ArrayList<>();
        for(int i=1;i<PastOwner.length;i++)
            Details.add(info.getTransactionDetail(PastOwner[i-1],PastOwner[i],data.get("BatteryName").toString()));
        for(int i=0;i<Details.size();i++)
            res.put("Detail"+i,batteryInfos.String2Map(Details.get(i)));
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
        ProcessRepositoryImpl processRepository = new ProcessRepositoryImpl();
        List<ProcessEntity> processEntities = processRepository.getByPId(Integer.parseInt(data.get("id").toString()));
        if(processEntities==null||processEntities.size()==0) {
            res.put("code", 10005);
            res.put("msg", "ID不存在数据库");
            return res;
        }
        List<ProducerEntity> producerEntity=new ArrayList<>();
        ProducterRepositoryImpl producterRepository = new ProducterRepositoryImpl();
        List<ApplicationEntity> applicationEntity=new ArrayList<>();
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        ;
        int i;
        for (i=0;i<processEntities.size();i++){
            applicationEntity.add(applicationRepository.getById(processEntities.get(i).getApplicationUid()));
            producerEntity.add(producterRepository.getById(processEntities.get(i).getApplicantUid()));
        }
        res.put("Processes",processEntities);
        res.put("Applications",applicationEntity);
        res.put("Producer",producerEntity);

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
            res.put("msg","ID不存在数据库");
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
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        ProcessRepositoryImpl processRepository = new ProcessRepositoryImpl();
        List<ApplicationEntity> applicationEntities = applicationRepository.getByAId(Integer.parseInt(data.get("id").toString()));
        List<ProcessEntity> processEntities= new ArrayList<>();
        if(applicationEntities==null){
            res.put("code",10005);
            res.put("msg","ID不存在数据库");
            return res;
        }
        int i=applicationEntities.size();
        int j;
        for(j=0;j<i;j++){
            res.put(""+j+"",Operate.GetApplicationInfo(applicationEntities.get(j)));
            if (applicationEntities.get(j).getAuthorized()==0){
                processEntities.add(processRepository.getByAId(applicationEntities.get(j).getUid()));
                res.put(""+j+"授权情况",processEntities);
            }
        }
        return res;
    }

    @RequestMapping(value = "/GetAllApplicationInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object GetAllApplicationInfo(){
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        List result =  applicationRepository.findAll();
        Map<String,Object> res=new HashMap<>();
        res.put("code", 0);
        res.put("data", result);
        return res;
    }

    @RequestMapping(value = "/BasicInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object GetBasicInfo(@RequestBody Map<String,Object> data){//function:获取用户基础信息;need:用户id，用户类型
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
            case 0:{
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
            case 1:{
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
        String ID=data.get("id").toString();
        BatteryEntity batteryEntity = GetBattery(ID);
        if(batteryEntity==null)
            return IDExist();
        res.put("Battery",Operate.GetBatteryInfo(batteryEntity));
        ProducerEntity producerEntity = GetProducer(batteryEntity.getProducer());
        res.put("Producer",producerEntity.getProducerName());
        return res;
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
            case 0: {
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
            case 1: {
                ProducerEntity producerEntity = GetProducer(ID);
                if(producerEntity==null)
                    return IDExist();
                Batteries = info.getTokens(action.toPublicKey(producerEntity.getProducerPrivateKey()));
                break;
            }

        }
        if(Batteries == null) {
            res.put("code", 11004);
            res.put("msg", "查询所有电池失败失败");
            return res;
        }
        res.put("code", 0);
        //res.put("msg", "成功返回数据");
        res.put("msg", Batteries);

        return res;
    }
    @RequestMapping(value = "/GetPassedAuthorize",method = RequestMethod.POST)
    @ResponseBody
    public Object GetPassedAuthorize(@RequestBody Map<String,Object>data){
        Map<String,Object> res = new HashMap<>();
        List<ProcessEntity> processEntities;
        ProcessRepositoryImpl processRepository = new ProcessRepositoryImpl();
        if (!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","缺少必要字段");
            return res;
        }
        Integer ID = Integer.parseInt(data.get("id").toString());
        processEntities = processRepository.getPassedProcesses(ID);
        if (processEntities.size()==0){
            res.put("code",10005);
            res.put("msg","该用户没有被授权操作过");
            return res;
        }
        ProducerEntity producerEntity;
        ProducterRepositoryImpl producterRepository = new ProducterRepositoryImpl();
        List<ApplicationEntity> applicationEntity=new ArrayList<>();
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        producerEntity = producterRepository.getById(processEntities.get(0).getApplicantUid());
        int i;
        for (i=0;i<processEntities.size();i++){
            applicationEntity.add(applicationRepository.getById(processEntities.get(i).getApplicationUid()));
        }
        res.put("Processes",processEntities);
        res.put("Applications",applicationEntity);
        res.put("Producer",producerEntity.getProducerName());
        return res;
    }
    @RequestMapping(value = "/GetProducerAuthorizeRecord",method = RequestMethod.POST)
    @ResponseBody
    public Object GetProducerAuthorizeRecord(@RequestBody Map<String,Object>data){
        Map<String,Object> res = new HashMap<>();
        if (!data.containsKey("id")){
            res.put("code",10001);
            res.put("msg","缺少必要字段");
            return res;
        }
        List<ProcessEntity> processEntities;
        List<ApplicationEntity> applicationEntities=new ArrayList<>();
        List<ProducerEntity> producerEntities = new ArrayList<>();
        ProcessRepositoryImpl processRepository = new ProcessRepositoryImpl();
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        ProducterRepositoryImpl producterRepository = new ProducterRepositoryImpl();
        Integer ID = Integer.parseInt(data.get("id").toString());
        processEntities = processRepository.getProcess(ID);
        int i;
        for (i=0;i<processEntities.size();i++){
            applicationEntities.add(applicationRepository.getById(processEntities.get(i).getApplicationUid()));
            producerEntities.add(producterRepository.getById(processEntities.get(i).getApplicantUid()));
        }
        res.put("申请文件",applicationEntities);
        res.put("申请公司",producerEntities);
        res.put("授权文件",processEntities);
        return res;
    }
    @RequestMapping(value = "/NotAuthorizedApplication",method = RequestMethod.POST)
    @ResponseBody
    public Object GetNotAuthorizedApplication(){
        Map<String,Object> res = new HashMap<>();
        List<ApplicationEntity> entities;
        int i=0;
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        List<ProducerEntity> producerEntity = new ArrayList<>();
        ProducterRepositoryImpl processRepository = new ProducterRepositoryImpl();

        entities = applicationRepository.RandomGet();
        for (i=0;i<entities.size();i++)
            producerEntity.add(processRepository.get(entities.get(i).getApplicantUid()));
        res.put("code",0);
        res.put("info",entities);
        res.put("producer",producerEntity);
        return res;
    }
}
