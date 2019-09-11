package everitoken.EveriTokenOperation;

public class Test {
    public static void main(String[] args) {

        // TODO Auto-generated method stub
        Info info = new Info();
        BatteryDetail[] batteryDetail = info.getAllBattery();
        for (int i = 0; i < batteryDetail.length; i++) {
            System.out.println(batteryDetail[i].getName()+": "+batteryDetail[i].getOwner());
        }

//        batteryDetail = info.getOwner(new String[] {"t1","t2","t3"});
//        for (int i = 0; i < batteryDetail.length; i++) {
//			System.out.println(batteryDetail[i].getName()+": "+batteryDetail[i].getOwner());
//		}


//        String[] listStrings = info.getSource("t2");
//        for (int i = 0; i < listStrings.length; i++) {
//			System.out.println(listStrings[i]);
//		}


        String[] names = info.getTokens("EVT5WQLGPpdYpCsSL6My7gkJWXKiedWLmrjVX8wur3NRDd5hHT71M");
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }

        Action action  = new Action();
//        String [] nameStrings = {"t12","t13"};
//
//        action.issueBattery("EVT5WQLGPpdYpCsSL6My7gkJWXKiedWLmrjVX8wur3NRDd5hHT71M", nameStrings);

//        BatteryDetail[] batteryDetail_2 = info.getAllBattery();
//        for (int i = 0; i < batteryDetail_2.length; i++) {
//            System.out.println(batteryDetail_2[i].getName()+": "+batteryDetail_2[i].getOwner());
//        }

//        String [] batteryNames = {"t1","t2","t3","t4","t5"};
//        action.issueBattery("EVT85rTze1SYcEJS5hxf64t8moTPuxP2SCiB9FsgFd6e1fcWbzdvf",batteryNames);

 //       action.transferBattery("t2","","EVT5WQLGPpdYpCsSL6My7gkJWXKiedWLmrjVX8wur3NRDd5hHT71M");

        action.destroyBattery("t1","5JG6puHZKdDqQFo9o8pgMfzXBub54YChYLMH2TBrDxWD2Xa81QZ");

        System.out.println();
        System.out.println();
        System.out.println();


        batteryDetail = info.getAllBattery();
        for (int i = 0; i < batteryDetail.length; i++) {
            System.out.println(batteryDetail[i].getName()+": "+batteryDetail[i].getOwner());
        }

//        action.destroyBattery("t12");



    }
}
