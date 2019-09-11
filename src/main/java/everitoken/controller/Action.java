package everitoken.EveriTokenOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.everitoken.sdk.java.*;
import io.everitoken.sdk.java.abi.*;
import io.everitoken.sdk.java.apiResource.Info;
import io.everitoken.sdk.java.dto.*;
import io.everitoken.sdk.java.exceptions.ApiResponseException;
import io.everitoken.sdk.java.param.*;
import io.everitoken.sdk.java.provider.KeyProvider;
import io.everitoken.sdk.java.service.*;


public class Action {
    private NetParams netParams = null;
    private final String PRIVATEKEY = "5KMA17kx29TLYevvCK7ov3kL2kyUdqrBCA7KP7DiQu1P6pTzPwr";
    private final String PUBLICKEY = "EVT85rTze1SYcEJS5hxf64t8moTPuxP2SCiB9FsgFd6e1fcWbzdvf";


    public Action() {
        this.netParams = new TestNetNetParams();
    }


    public String createPrivateKey() {
        PrivateKey privateKey = PrivateKey.randomPrivateKey();
        return privateKey.toWif();
    }

    public String toPublicKey(String privateKey) {
        PublicKey publicKey = (PrivateKey.of(privateKey)).toPublicKey();
        return publicKey.toString();
    }


    public boolean createDomain(String jsonData) {

        // specify the content of the action
        final String actionData = jsonData;
        final JSONObject json = JSONObject.parseObject(actionData);

        final NewDomainAction newDomainAction = NewDomainAction.ofRaw(json.getString("name"), json.getString("creator"),
                json.getJSONObject("issue"), json.getJSONObject("transfer"), json.getJSONObject("manage"));


        KeyProvider keyProvider = KeyProvider.of(PRIVATEKEY);
        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));
            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(newDomainAction), false, keyProvider);
            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (final ApiResponseException ex) {
            System.out.println(ex.getRaw());
            return false;
        }
    }


    public boolean updataDomain(String jsonData) {
        final String data = jsonData;

        final JSONObject json = JSONObject.parseObject(data);
        final UpdateDomainAction updateDomainAction = UpdateDomainAction.ofRaw(json.getString("name"), null, null,
                json.getJSONObject("manage"));

        List<String> privateKeyList = Arrays.asList(PRIVATEKEY, "5JCseygdrpwPgbJYmQseWDGTc2cKf5Rb9C1ABVQwXS6jYi3f5ku");

        KeyProvider keyProvider = KeyProvider.of(privateKeyList.toArray(new String[]{}));

        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));
            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(updateDomainAction), false, keyProvider);

            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (final ApiResponseException ex) {
            System.out.println(ex.getRaw());
            return false;
        }
    }


    public boolean issueBattery(String publicKey, String[] batteryName) {

        List<String> names = new ArrayList<String>(Arrays.asList(batteryName));
        List<String> privateKeyList = new ArrayList<String>();


        IssueTokenAction issueTokenAction = IssueTokenAction.of("BatteryTokenTest", names,
                Collections.singletonList(Address.of(publicKey)));

        privateKeyList.add(PRIVATEKEY);
        privateKeyList.add("5JG6puHZKdDqQFo9o8pgMfzXBub54YChYLMH2TBrDxWD2Xa81QZ");
        privateKeyList.add("5Kbv19F4AB5hjLoATicViNPxJ9E7T9kFpj3t372tDzsPvTK5WpS");

        KeyProvider keyProvider = KeyProvider.of(privateKeyList.toArray(new String[]{}));


        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));

            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(issueTokenAction), false, keyProvider);
            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (ApiResponseException ex) {
            System.out.println(ex.getRaw());
            return false;
        }
    }


    public boolean issueBattery(String publicKey, String batteryName) {
        return issueBattery(publicKey, new String[]{batteryName});
    }


    public boolean transferBattery(String tokenName, String privateKey, String publicKey) {

        KeyProvider keyProvider = null;
        if (privateKey == "") {
            keyProvider = KeyProvider.of(PRIVATEKEY);
        } else {
            String[] keys = {PRIVATEKEY, privateKey};
            keyProvider = KeyProvider.of(keys);
        }

        TransferAction transferAction = TransferAction.of("BatteryTokenTest", tokenName,
                Arrays.asList(publicKey), "");
        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));

            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(transferAction), false, keyProvider);
            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (ApiResponseException ex) {
            System.out.println(ex.getRaw());
            return false;
        }
    }


    public boolean destroyBattery(String batteryName, String privateKey) {

        KeyProvider keyProvider = null;

        if (privateKey == "") {
            keyProvider = KeyProvider.of(PRIVATEKEY);
        } else {
            String[] keys = {PRIVATEKEY, privateKey};
            keyProvider = KeyProvider.of(keys);
        }

        DestroyTokenAction destroyTokenAction = DestroyTokenAction.of("BatteryTokenTest", batteryName);
        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));
            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(destroyTokenAction), false, keyProvider);
            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (ApiResponseException ex) {
            System.out.println(ex.getRaw());
        }
        return false;
    }


    public boolean createGroup(String JSONData, String groupName) {
        final JSONObject json = JSONObject.parseObject(JSONData);
        final NewGroupAction newGroupAction = NewGroupAction.ofRaw(groupName, json);
        KeyProvider keyProvider = KeyProvider.of(PRIVATEKEY);

        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));
            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(newGroupAction), false, keyProvider);
            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (final ApiResponseException ex) {
            System.out.println(ex.getRaw());
            return false;
        }
    }


    public boolean updataGroup(String JSONData, String groupName, String[] privateKey) {

        final JSONObject json = JSONObject.parseObject(JSONData);
        final UpdateGroupAction updateGroupAction = UpdateGroupAction.ofRaw("BatteryTokenTest", json);

        List<String> keys = Arrays.asList(privateKey);
        keys.add(PRIVATEKEY);
        KeyProvider keyProvider = KeyProvider.of(keys.toArray(new String[]{}));

        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(netParams));
            TransactionService transactionService = TransactionService.of(netParams);
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of(PUBLICKEY));
            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(updateGroupAction), false, keyProvider);

            if (txData.isExecuted()) {
                System.out.println(txData.getTrxId());
                return true;
            } else {
                System.out.println(txData.getProcessed());
                return false;
            }
        } catch (final ApiResponseException ex) {
            System.out.println(ex.getRaw());
        }


        return false;
    }
}
