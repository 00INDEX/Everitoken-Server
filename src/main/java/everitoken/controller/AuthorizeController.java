package everitoken.controller;


import everitoken.dao.impl.ApplicationRepositoryImpl;
import everitoken.dao.impl.ProcessRepositoryImpl;
import everitoken.dao.impl.ProducterRepositoryImpl;
import everitoken.entity.ApplicationEntity;
import everitoken.entity.GovernmentEntity;
import everitoken.entity.ProcessEntity;
import everitoken.entity.ProducerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static everitoken.Operations.Operate.*;


@Controller
@RequestMapping(value = "/Authorize")
public class AuthorizeController {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @RequestMapping(value = "/Apply",method = RequestMethod.POST)
    @ResponseBody
    public Object Apply(@RequestBody Map<String,Object>data) throws Exception {//function:申请授权;need:申请者ID，申请文件,申请时间
        Map<String,Object> res=new HashMap<>();
        if(!data.containsKey("ApplicantID")){
            res.put("code",10001);
            res.put("msg", "缺少申请者ID");
            return res;
        }
        if(!data.containsKey("ApplicationDocument")){
            res.put("code",10001);
            res.put("msg", "缺少申请文件");
            return res;
        }
        if(!data.containsKey("ApplicationTime")){
            res.put("code",10001);
            res.put("msg", "缺少申请时间");
            return res;
        }
        ProducerEntity producerEntity=GetProducer(Integer.parseInt(data.get("ApplicantID").toString()));

        if (producerEntity==null){
            res.put("code",10005);
            res.put("msg","ID不存在数据库");
            return res;
        }
        Timestamp time = Timestamp.valueOf(data.get("ApplicationTime").toString());
        ApplicationEntity applicationEntity=new ApplicationEntity();
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        applicationEntity.setApplicantUid(Integer.parseInt(data.get("ApplicantID").toString()));
        applicationEntity.setApplicationDocuments(data.get("ApplicationDocument").toString());
        applicationEntity.setApplicationTime(time);
        applicationEntity.setAuthorized(-1);
        applicationRepository.add(applicationEntity);

        res.put("code",0);
        res.put("msg","success");
        return res;
    }
    @RequestMapping(value = "/SetAuthorize",method = RequestMethod.POST)
    @ResponseBody
    public Object SetAuthorize(@RequestBody Map<String,Object>data) throws Exception {//function:政府授权;need:政府ID,政府授权时间,授权值，授权理由，申请者ID,申请的UID
        Map<String,Object> res = new HashMap<>();
        if(!data.containsKey("ApplicantID")&&!data.containsKey("GovernmentID")&&!data.containsKey("AuthorizeTime")&&!data.containsKey("AuthorizeValue")
                &&!data.containsKey("AuthorizeReason")&&!data.containsKey("ApplicationUid"))
        {
            res.put("code",10001);
            res.put("msg","缺少必要字段");
            return res;
        }
        Integer P_ID = Integer.parseInt(data.get("ApplicantID").toString());
        Integer G_ID = Integer.parseInt(data.get("GovernmentID").toString());
        Integer A_ID = Integer.parseInt(data.get("ApplicationUid").toString());
        ProducerEntity producerEntity = GetProducer(P_ID);
        GovernmentEntity governmentEntity = GetGovernment(G_ID);
        ApplicationEntity applicationEntity = GetApplication(A_ID);
        ApplicationRepositoryImpl applicationRepository = new ApplicationRepositoryImpl();
        ProcessEntity processEntity =new ProcessEntity();
        ProcessRepositoryImpl processRepository = new ProcessRepositoryImpl();
        ProducterRepositoryImpl producterRepository = new ProducterRepositoryImpl();
        if(producerEntity==null||governmentEntity==null||applicationEntity==null){
            return IDExist();
        }
        if(!applicationEntity.getApplicantUid().equals(producerEntity.getProducerUid()))
        {
            res.put("code",30001);
            res.put("msg","字段信息有误，数据库数据冲突");
            return res;
        }
        if(applicationEntity.getAuthorized()==1){
            res.put("code",10007);
            res.put("msg","该用户已被授权");
            return res;
        }
        processEntity.setApplicantUid(applicationEntity.getApplicantUid());
        processEntity.setProcessReason(data.get("AuthorizeReason").toString());
        processEntity.setProcessTime(Timestamp.valueOf(data.get("AuthorizeTime").toString()));
        processEntity.setProcessorUid(governmentEntity.getGovernmentUid());
        processEntity.setValue((Integer) data.get("AuthorizeValue"));
        processEntity.setApplicationUid(Integer.parseInt(data.get("ApplicationUid").toString()));
        processRepository.add(processEntity);
        producerEntity.setProducerAuthorized((Integer)data.get("AuthorizeValue"));
        producterRepository.update(producerEntity);
        applicationEntity.setAuthorized(Integer.parseInt(data.get("AuthorizeValue").toString()));
        applicationRepository.update(applicationEntity);
        List<ApplicationEntity> applicationEntityList =new ArrayList<>();
        int i;
        if (data.get("AuthorizeValue").toString().equals("1")) {
            applicationEntityList = applicationRepository.getByAId(Integer.parseInt(data.get("ApplicantID").toString()));
            for (i = 0; i < applicationEntityList.size(); i++) {
                applicationEntityList.get(i).setAuthorized(Integer.parseInt(data.get("AuthorizeValue").toString()));
            }
        }

        res.put("code",0);
        res.put("msg","success");
        return res;
    }

}


