package everitoken.controller;

import everitoken.dao.PostRepository;
import everitoken.dao.impl.PostRepositoryImpl;
import everitoken.entity.PostEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/Market")
public class MarketController {
    Map<String,Object> res = new HashMap();
    @RequestMapping(value = "/deleteMyPost",method = RequestMethod.POST)
    @ResponseBody
    public Object DeletePost(@RequestBody Map<String,Object> data){
        if(!res.containsKey("post_id"))
        {
            res.put("msg:","No ID");
            res.put("code",20001);
            return res;
        }
        PostRepository postRepository = new PostRepositoryImpl();
        postRepository.delete(Integer.valueOf(data.get("post_id").toString()));
        res.put("msg:","success");
        res.put("code",0);
        return res;
    }
    @RequestMapping(value = "/queryAll",method = RequestMethod.POST)
    @ResponseBody
    public Object QueryAll()
    {
        List<PostEntity> postEntities;
        PostRepository postRepository = new PostRepositoryImpl();
        postEntities=postRepository.RandomGet();
        if(postEntities.get(0)== null)
            res.put("msg","The List is null");
        else
            res.put("msg","success");
        res.put("Entities",postEntities);
        return res;
    }
    @RequestMapping(value = "/queryMine",method = RequestMethod.POST)
    @ResponseBody
    public Object QueryMine(@RequestBody Map<String,Object>data){
        if(!data.containsKey("user_id")){
            res.put("msg","没有用户ID");
            res.put("code","20001");
            return res;
        }
        Integer id=Integer.valueOf(data.get("user_id").toString());
        List<PostEntity> postEntities;
        PostRepository postRepository = new PostRepositoryImpl();
        postEntities = postRepository.GetByUserID(id);
        if (postEntities.get(0)==null){
            res.put("msg","ID不存在数据库");
            res.put("code",10005);
            return res;
        }
        res.put("msg","success");
        res.put("code",0);
        res.put("Entities",postEntities);
        return res;
    }
    @RequestMapping(value = "/issuePost",method = RequestMethod.POST)
    @ResponseBody
    public Object issuePost(@RequestBody Map<String,Object>data) throws Exception {
        if (!data.containsKey("battery_id")||!data.containsKey("user_comment")||!data.containsKey("user_name")||
                !data.containsKey("user_email")||!data.containsKey("user_phone")){
            res.put("msg","缺少必要信息");
            res.put("code",10007);
            return res;
        }
        PostEntity postEntity = new PostEntity();
        /*
         * set方法*/
        PostRepository postRepository = new PostRepositoryImpl();
        postRepository.add(postEntity);
        return res;
    }
    @RequestMapping(value = "/updatePost",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePost(@RequestBody Map<String,Object>data) throws Exception {
        if (!data.containsKey("battery_id")||!data.containsKey("user_comment")||!data.containsKey("user_name")||
                !data.containsKey("user_email")||!data.containsKey("user_phone")){
            res.put("msg","缺少必要信息");
            res.put("code",10007);
            return res;
        }
        PostEntity postEntity = new PostEntity();
        /*
        * set方法*/
        PostRepository postRepository = new PostRepositoryImpl();
        postRepository.update(postEntity);
        return res;
    }
}
