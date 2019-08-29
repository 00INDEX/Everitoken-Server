package everitoken.controller;

import com.sun.xml.internal.bind.v2.model.core.ID;
import everitoken.dao.ApplicationRepository;
import everitoken.dao.ProcessRepository;
import everitoken.dao.impl.ApplicationRepositoryImpl;
import everitoken.dao.impl.ProcessRepositoryImpl;
import everitoken.entity.ApplicationEntity;
import everitoken.entity.GovernmentEntity;
import everitoken.entity.ProcessEntity;
import everitoken.entity.ProducerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import everitoken.Operations.Operate.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static everitoken.Operations.Operate.*;


@Controller
@RequestMapping(value = "/Authorize")
public class AuthorizeController {
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
        Timestamp time = Timestamp.valueOf(data.get("ApplicationTime").toString());
        ApplicationEntity applicationEntity=new ApplicationEntity();
        ApplicationRepository applicationRepository = new ApplicationRepositoryImpl();
        applicationEntity.setApplicantUid(Integer.parseInt(data.get("ApplicantID").toString()));
        applicationEntity.setApplicationDocuments(data.get("ApplicationDocument").toString());
        applicationEntity.setApplicationTime(time);
        applicationRepository.add(applicationEntity);
        res.put("code",0);
        res.put("ID",applicationEntity.getUid());
        return res;
    }
    @RequestMapping(value = "/SetAuthorize",method = RequestMethod.POST)
    @ResponseBody
    public Object SetAuthorize(@RequestBody Map<String,Object>data) throws Exception {//function:政府授权;need:政府ID,政府授权时间,授权值，授权理由，申请者ID,申请的UID
        Map<String,Object> res = new HashMap<>();
        if(!data.containsKey("ApplicantID")&&!data.containsKey("GovernmentID")&&!data.containsKey("AuthorizeTime")&&!data.containsKey("AuthorizeValue")
        &&!data.containsKey("AuthorizeReason")&&!data.containsKey("ApplicantUid"))
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
        ProcessEntity processEntity =new ProcessEntity();
        ProcessRepository processRepository = new ProcessRepositoryImpl();
        if(producerEntity==null||governmentEntity==null||applicationEntity==null){
            return IDExist();
        }
        if(!applicationEntity.getApplicantUid().equals(producerEntity.getProducerUid()))
        {
            res.put("code",30001);
            res.put("msg","字段信息有误，数据库数据冲突");
            return res;
        }
        processEntity.setApplicantUid(applicationEntity.getApplicantUid());
        processEntity.setProcessReason(data.get("AuthorizeReason").toString());
        processEntity.setProcessTime(Timestamp.valueOf(data.get("AuthorizeTime").toString()));
        processEntity.setProcessorUid(governmentEntity.getGovernmentUid());
        processRepository.add(processEntity);
        res.put("code",0);
        res.put("msg","success");
        return res;
    }

}
