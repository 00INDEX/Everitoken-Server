package everitoken.EveriTokenOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.everitoken.sdk.java.*;
import io.everitoken.sdk.java.dto.ActionData;
import io.everitoken.sdk.java.dto.TokenDetailData;
import io.everitoken.sdk.java.dto.TokenDomain;
import io.everitoken.sdk.java.exceptions.ApiResponseException;
import io.everitoken.sdk.java.param.ActionQueryParams;
import io.everitoken.sdk.java.param.NetParams;
import io.everitoken.sdk.java.param.PublicKeysParams;
import io.everitoken.sdk.java.param.TestNetNetParams;

public class Info {
    private NetParams netParams = null;
    private Api api = null;

    public Info() {
        this.netParams = new TestNetNetParams();
        this.api = new Api(netParams);
    }


    public BatteryDetail[] getOwner(String[] tokenNames) {
        List<String> nameList = Arrays.asList(tokenNames);
        List<BatteryDetail> data = new ArrayList<BatteryDetail>();
        nameList.forEach(tokenName->{
            TokenDetailData res = null;
            try {
                res = api.getToken("testDomain", tokenName);
                res.getOwner().forEach(publicKey -> data.add(new BatteryDetail(tokenName, publicKey.toString())));
            } catch (ApiResponseException e) {
                e.printStackTrace();
                return;
            }
        });
        return data.toArray(new BatteryDetail[]{});
    }

    public BatteryDetail getOwner(String tokenNames) {
        BatteryDetail[] batteryDetail = getOwner(new String[] {tokenNames});
        return batteryDetail[0];
    }



    public BatteryDetail[] getAllBattery() {
        List<BatteryDetail> data = new ArrayList<BatteryDetail>();
        try {
            List<TokenDetailData> tokens = api.getDomainTokens("testDomain", 10, 0);
            tokens.forEach(token->{
                        token.getOwner().forEach(publicKey->{
                            data.add(new BatteryDetail(token.getName(), publicKey.toString()));
                        });
                    }
            );
            return data.toArray(new BatteryDetail[]{});

        } catch (ApiResponseException e) {
            e.printStackTrace();
            return new BatteryDetail[]{};
        }
    }

    public String[] getTokens(String publicKey){
        final PublicKeysParams publicKeysParams = new PublicKeysParams(new String[] { publicKey });
        List<TokenDomain> res = new ArrayList<TokenDomain>();
        try {
            res = new Api(netParams).getOwnedTokens(publicKeysParams);
        } catch (ApiResponseException e) {
            e.printStackTrace();
        }
        List<String> names = new ArrayList<String>();
        res.forEach(datas->names.add(datas.getName()));
        return names.toArray(new String[]{});
    }


    public String[] getSource(String tokenName) {
        List<String> Owners = new ArrayList<String>();
        try {
            List<ActionData> actionData = api.getActions(new ActionQueryParams("testDomain", tokenName));
            actionData.forEach(data->{
                Owners.add(data.getData().getJSONArray("to").getString(0));
            });
            return Owners.toArray(new String[]{});
        } catch (ApiResponseException e) {
            e.printStackTrace();
            return Owners.toArray(new String[]{});
        }
    }
}
