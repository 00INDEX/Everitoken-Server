package everitoken.Operations;

import everitoken.dao.*;
import everitoken.dao.impl.*;
import everitoken.entity.*;

import java.util.*;

public class Operate {
    public static Object GetCustomInfo(CustomerEntity customerEntity){
        Map<String,Object> res=new HashMap<>();
        res.put("name",customerEntity.getCustomerName());
        res.put("phone",customerEntity.getCustomerPhone());
        return res;
    }
    public static Object GetGovernmentInfo(GovernmentEntity customerEntity){
        Map<String,Object> res=new HashMap<>();
        res.put("name",customerEntity.getGovernmentName());
        res.put("CHNCode",customerEntity.getGovernmentChnCode());
        return res;
    }
    public static Object GetRSInfo(RecyclingStationEntity customerEntity){
        Map<String,Object> res=new HashMap<>();
        res.put("name",customerEntity.getRsName());
        return res;
    }
    public static Object GetProducerInfo(ProducerEntity customerEntity){
        Map<String,Object> res=new HashMap<>();
        res.put("name",customerEntity.getProducerName());
        res.put("CHNCode",customerEntity.getProducerChnCode());
        res.put("producer_authorized",customerEntity.getProducerAuthorized());
        return res;
    }

    public static Object GetBatteryInfo(BatteryEntity batteryEntity)
    {
        Map<String,Object> res=new HashMap<>();
        res.put("Type",batteryEntity.getBatteryType());
        res.put("batteryCapacity",batteryEntity.getBatteryCapacity());
        res.put("batteryMaxVoltage",batteryEntity.getBatteryMaxVoltage());
        res.put("batteryAverageTemperature",batteryEntity.getBatteryAverageTemperature());
        res.put("batteryChgCycles",batteryEntity.getBatteryChgCycles());
        return res;
    }

    public static Object GetApplicationInfo(ApplicationEntity applicationEntity){
        Map<String,Object> res=new HashMap<>();
        res.put("uid",applicationEntity.getUid());
        res.put("ApplicantUid",applicationEntity.getApplicantUid());
        res.put("applicationTime",applicationEntity.getApplicationTime());
        res.put("applicationDocuments",applicationEntity.getApplicationDocuments());
        res.put("Authorized",applicationEntity.getAuthorized());
        return res;
    }

    public static CustomerEntity GetCustomer(Integer ID){
        CustomerEntity customerEntity;
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
        customerEntity= customerRepository.getById(ID);
        return customerEntity;
    }
    public static GovernmentEntity GetGovernment(Integer ID){
        GovernmentEntity customerEntity;
        GovernmentRepositoryImpl customerRepository = new GovernmentRepositoryImpl();
        customerEntity= customerRepository.getById(ID);
        return customerEntity;
    }
    public static ProducerEntity GetProducer(Integer ID){
        ProducerEntity producerEntity;
        ProducterRepositoryImpl producterRepository=new ProducterRepositoryImpl();
        producerEntity=producterRepository.getById(ID);
        return producerEntity;
    }
    public static RecyclingStationEntity GetRS(Integer ID){
        RecyclingStationEntity producerEntity;
        RecyclingStationRepositoryImpl producterRepository=new RecyclingStationRepositoryImpl();
        producerEntity=producterRepository.getById(ID);
        return producerEntity;
    }
    public static BatteryEntity GetBattery(String ID){
        BatteryEntity batteryEntity;
        BatteryRepositoryImpl batteryRepository = new BatteryRepositoryImpl();
        batteryEntity = batteryRepository.getById(ID);
        return batteryEntity;
    }
    public static Object IDExist(){
        Map<String,Object> res=new HashMap<>();
        res.put("code",10005);
        res.put("msg","ID不存在数据库");
        return res;
    }
    public static ApplicationEntity GetApplication(Integer ID){
        ApplicationEntity applicationEntity;
        ApplicationRepositoryImpl applicationRepository=new ApplicationRepositoryImpl();
        applicationEntity = applicationRepository.getById(ID);
        return applicationEntity;
    }

    public static ProcessEntity GetProcess(Integer ID){
        ProcessEntity processEntity;
        ProcessRepositoryImpl processRepository = new ProcessRepositoryImpl();
        processEntity = processRepository.get(ID);
        return processEntity;
    }

    public static Object GetProcessInfo(ProcessEntity processEntity){
        Map<String,Object> res = new HashMap<>();
        res.put("uid",processEntity.getUid());
        res.put("applicantUid",processEntity.getApplicantUid());
        res.put("processorUid",processEntity.getProcessorUid());
        res.put("processTime",processEntity.getProcessTime());
        res.put("processReason",processEntity.getProcessReason());
        res.put("value",processEntity.getValue());
        return res;
    }

    public static List<String> ToUserName(String[] Past){
        List<String> PastOwner = new ArrayList<>();
        Integer PastID;
        PublicKeyEntity publicKeyEntity;
        UserEntity userEntity;
        PublicKeyRepositoryImpl publicKeyRepository = new PublicKeyRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        int i;
        int j=Past.length;
        for(i=0;i<j;i++){
            publicKeyEntity=publicKeyRepository.getByPK(Integer.parseInt(Past[i]));
            PastID=publicKeyEntity.getUserUid();
            userEntity = userRepository.get(PastID);
            PastOwner.add(userEntity.getUsername());
        }
        return PastOwner;
    }

}
