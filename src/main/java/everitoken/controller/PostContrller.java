package everitoken.controller;

import everitoken.dao.impl.PostReposityImpl;
import everitoken.entity.PostEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/Post")
public class PostContrller {
    private Map<String, Object> res;
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Object publish(@RequestBody Map<String,Object> data){
        res = new HashMap<>();
        PostReposityImpl postReposity = new PostReposityImpl();
        if (!data.containsKey("battery_id") || !data.containsKey("customer_id") || !data.containsKey("phone") || !data.containsKey("email")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        PostEntity postEntity = new PostEntity();
        postEntity.setCustomerId(Integer.parseInt(data.get("customer_id").toString()));
        postEntity.setBatteryId(Integer.parseInt(data.get("battery_id").toString()));
        postEntity.setPhone(data.get("phone").toString());
        postEntity.setPhone(data.get("email").toString());
        postEntity.setDate(new Timestamp(System.currentTimeMillis()));
        int uid = -1;
        try {
            uid = postReposity.add(postEntity);
        }catch (Exception e){
            res.put("code", 20002);
            res.put("msg", e.getMessage());
            return res;
        }
        res.put("code", 0);
        res.put("msg", "success");
        return res;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ResponseBody
    public Object getAll(@RequestBody Map<String,Object> data){
        res = new HashMap<>();
        PostReposityImpl postReposity = new PostReposityImpl();
        if (!data.containsKey("customer_id")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        List list = postReposity.getByCId(Integer.parseInt(data.get("customer_id").toString()));
        if (list == null){
            res.put("code", 40001);
            res.put("msg", "该用户没有发过帖子");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "success");
        res.put("data", list);
        return res;
    }

    @RequestMapping(value = "/getAllByTime", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllByTime(@RequestBody Map<String,Object> data){
        res = new HashMap<>();
        PostReposityImpl postReposity = new PostReposityImpl();
        List list = postReposity.getByTime();
        if (list == null){
            res.put("code", 40002);
            res.put("msg", "数据库为空");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "success");
        res.put("data", list);
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestBody Map<String,Object> data){
        res = new HashMap<>();
        PostReposityImpl postReposity = new PostReposityImpl();
        if (!data.containsKey("uid")){
            res.put("code", 20001);
            res.put("msg", "缺失必要字段");
            return res;
        }
        PostEntity postEntity;
        if ((postEntity = postReposity.getById(Integer.parseInt(data.get("uid").toString())))== null){
            res.put("code", 40003);
            res.put("msg", "找不到该帖子");
            return res;
        }
        postReposity.delete(Integer.parseInt(data.get("uid").toString()));
        res.put("code", 0);
        res.put("msg", "success");
        return res;
    }
}
