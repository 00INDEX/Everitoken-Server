package everitoken.Operations;

import everitoken.dao.*;
import everitoken.dao.impl.*;
import everitoken.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return res;
    }

    public static CustomerEntity GetCustomer(Integer ID){
        CustomerEntity customerEntity;
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        customerEntity= customerRepository.get(ID);
        return customerEntity;
    }
    public static GovernmentEntity GetGovernment(Integer ID){
        GovernmentEntity customerEntity;
        GovernmentRepository customerRepository = new GovernmentRepositoryImpl();
        customerEntity= customerRepository.get(ID);
        return customerEntity;
    }
    public static ProducerEntity GetProducer(Integer ID){
        ProducerEntity producerEntity;
        ProducterRepository producterRepository=new ProducterRepositoryImpl();
        producerEntity=producterRepository.get(ID);
        return producerEntity;
    }
    public static RecyclingStationEntity GetRS(Integer ID){
        RecyclingStationEntity producerEntity;
        RecyclingStationRepository producterRepository=new RecyclingStationRepositoryImpl();
        producerEntity=producterRepository.get(ID);
        return producerEntity;
    }
    public static BatteryEntity GetBattery(Integer ID){
        BatteryEntity batteryEntity;
        BatteryRepository batteryRepository = new BatteryRepositoryImpl();
        batteryEntity = batteryRepository.get(ID);
        return batteryEntity;
    }
    public static Object IDExist(){
        Map<String,Object> res=new HashMap<>();
        res.put("code",10005);
        res.put("msg","ID不存在");
        return res;
    }
    public static ApplicationEntity GetApplication(Integer ID){
        ApplicationEntity applicationEntity;
        ApplicationRepository applicationRepository=new ApplicationRepositoryImpl();
        applicationEntity = applicationRepository.get(ID);
        return applicationEntity;
    }

    public static ProcessEntity GetProcess(Integer ID){
        ProcessEntity processEntity;
        ProcessRepository processRepository = new ProcessRepositoryImpl();
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
        return res;
    }

    public static List<String> ToUserName(String[] Past){
        List<String> PastOwner = new ArrayList<>();
        Integer PastID;
        PublicKeyEntity publicKeyEntity;
        UserEntity userEntity;
        PublicKeyRepository publicKeyRepository = new PublicKeyRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        int i;
        int j=Past.length;
        for(i=0;i<j;i++){
            publicKeyEntity=publicKeyRepository.getByPK(Past[i]);
            PastID=publicKeyEntity.getUserUid();
            userEntity = userRepository.get(PastID);
            PastOwner.add(userEntity.getUsername());
        }
        return PastOwner;
    }

}
