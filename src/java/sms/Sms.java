/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

import cms.tools.Server;
import cms.tools.ServerLog;
import java.util.ArrayList;
import java.util.List;
import jj.jjNumber;
//import raygansms.ArrayOfLong;
//import raygansms.ArrayOfString;
//import raygansms.TrezSmsService;
//import raygansms.TrezSmsServiceSoap;

/**
 *
 * @author MrSalesi
 */
public class Sms {

    // Web Service Client... : http://www.sms.ardindata.com/webservice/smsService.php?wsdl
    // Web Service Client... : raygansms.com/TrezSmsService.asmx?WSDL
//    public static String senderNumber = "9830006859000477";//9830006859000333
//    public static String publicNo = "98300071934";
    //http://www.smshezare.com/send_via_get/send_sms.php?username=hafteghlim&password=1109325&sender_number=30006972777777&receiver_number=9132686053&note=aaaa
//    public static TrezSmsServiceSoap soap = new TrezSmsService().getTrezSmsServiceSoap();
    // RayganSms.com : username :mrsalesi password:esshahab

//    public static String send(String messageBody, String reciptionNumber) {
//        ArrayOfString numbers = new ArrayOfString();
//        List<String> list = new ArrayList<String>();
//        list.add(reciptionNumber);
//        List<String> smsConfig = smsConfig(list);
//        if (smsConfig.size() < 1) {
//            return "Invalid Number";
//        }
//        numbers.getString().add(smsConfig.get(0));
//        ServerLog.Print("one Massage to:" + numbers.getString().get(0).toString());
//        ArrayOfLong l = new ArrayOfLong();
//        long l1 = 1;
//        l.getLong().add(l1);
//        ArrayOfLong resultCode = new ArrayOfLong();
//        resultCode = soap.sendMessage(userName, password, senderNumber, messageBody, numbers, 1, l);
//        int size = resultCode.getLong().size();
//        for (int i = 0; i < size; i++) {
//            ServerLog.Print("--- " + i + " :" + resultCode.getLong().get(i));
//        
//        }
//        resultCode.getLong().subList(0, size);
//        String result;
//        ServerLog.Print("resultCode.getLong().get(0) = " + resultCode.getLong().get(0));
//        result = resultCode.getLong().get(0) > 1000 ? "sent" : "err";
//        return result;
//    }
    /**
     * @return وضعیت هر پیامک را در یک لیست بر می گرداند
     */
//    public static List<Long> send(String messageBody, List<String> reciptionNumber) {
//        List<Long> finalresult = new ArrayList<Long>();
//        ArrayOfString numbers = new ArrayOfString();
//        ArrayOfLong l = new ArrayOfLong();
//        int size = reciptionNumber.size();
//        long l1 = 1;
//        l.getLong().add(l1);
//
//
//        int i = 0;
//        int max = 79;//هر بار فقط 80 پیامک میتوان ارسال کرد                        
//        while (i <= size) {
//            numbers.getString().addAll(smsConfig(reciptionNumber.subList(i, (i + max > size) ? size : i + max)));
//            i += max;
//            ArrayOfLong result = soap.sendMessage(Server.userNameSMS, Server.passwordSMS, senderNumber, messageBody, numbers, 1, l);
//            finalresult.addAll(result.getLong());
//            result.getLong().clear();
//            numbers.getString().clear();
//        }
//        for (int j = 0; j < size; j++) {
//            ServerLog.Print(j + " - Massage to: " + reciptionNumber.get(j) + " - result:" + finalresult.get(j) + ";   (" + messageBody + ") ");
//        }
//        return finalresult;
//    }

    private static String convertToValidNumber(String number) {
        if (number.startsWith("0")) {
            number = number.substring(1, number.length());
        }
        if (jjNumber.isDigit(number)) {
            if (number.length() == 10) {
                if (number.startsWith("9")) {
                    return number;
                }
            }
        }
        return "";
    }

    private static boolean isContains(List<String> noList, String searchNo) {
        for (int i = 0; i < noList.size(); i++) {
            if (noList.get(i).equals(searchNo)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> smsConfig(List<String> reciptionNumber) {
        List<String> list2 = new ArrayList<String>();
        for (int i = 0; i < reciptionNumber.size(); i++) {
            String no = reciptionNumber.get(i);
            if (no.indexOf(",") > -1) {
                String[] split = no.split(",");
                for (int j = 0; j < split.length; j++) {
                    String convertToValidNumber = convertToValidNumber(split[j]);
                    if (convertToValidNumber.length() == 10) {
                        if (!isContains(list2, convertToValidNumber)) {
                            list2.add(convertToValidNumber);
                        }
                    }
                }
            } else {
                String convertToValidNumber = convertToValidNumber(no);
                if (convertToValidNumber.length() == 10) {
                    if (!isContains(list2, convertToValidNumber)) {
                        list2.add(convertToValidNumber);
                    }
                }
            }
        }
        return list2;
    }

//    public static List<String> getReciveMessage() {
//        String result = soap.getReciveMessage(Server.userNameSMS, Server.passwordSMS, senderNumber);
//        ServerLog.Print(result);
//        String[] resultArray = (result.split("(\\$)"));
//        List<String> recivedMassages = new ArrayList<String>();
//        for (int i = 0; i < resultArray.length; i++) {
//            recivedMassages.add(resultArray[i]);
//        }
//        return recivedMassages;
//    }
//        public static long getCredit(){
//        long l=  soap.getCredit(Server.userNameSMS, Server.passwordSMS,senderNumber);
//        return l;
//    }

    public static void main(String[] args) {
//        List<String> reciveMessage = getReciveMessage();
//        for (int i = 0; i < reciveMessage.size(); i++) {
//            ServerLog.Print(">>>" + reciveMessage.get(i));
//        }
//        if (true) {
//            return;
//        }
//        String str = "سلام تشكر-989374931859-9830006859000508-6/24/2013 11:39:10 PM$سلام تشكر-989374931859-9830006859000508-6/24/2013 11:39:10 PM$";
//
//        List<String> result = new ArrayList<String>();
//        String[] strArray = (str.split("(\\$)"));
//        for(int i=0;i<strArray.length;i++){
//            result.add(strArray[i]);
//        }
//        ServerLog.Print("---------------------");
//        ServerLog.Print(result.get(0));
//        ServerLog.Print("---------------------");
//        ServerLog.Print(result.get(1));
//        ServerLog.Print("---------------------");
//        for (int i = 0; i < result.size(); i++) {
//            ServerLog.Print(".................................................");
//            ServerLog.Print(result.get(i));
//            ServerLog.Print(".................................................");
//        }
//        .......................................................................
        List<String> numbers = new ArrayList<String>();
        numbers.add(("9133368036"));
//        ArrayOfLong l = new ArrayOfLong();
        String massageBody = "سلام سلام ااا";
//        List<Long> sendbatchMassages = Sms.send(massageBody, numbers);
//        for (int i = 0; i < sendbatchMassages.size(); i++) {
//            ServerLog.Print(">>>" + sendbatchMassages.get(i));
//        }
//        ServerLog.Print("credit is:" + Sms.getCredit());
        ServerLog.Print("--------------------------------");

//        ServerLog.Print(resultstr);
//        .......................................................................
        //    Long[] reciptionNo={Long.valueOf("9133368036")};
//    reciptionNo[]= Long.valueOf("9133368036");
//        ServerLog.Print("one Massage to:" + reciptionNo[0]);
//    long l = soap.getCredit(userName,password,senderNumber);
//        ServerLog.Print(l + "-------");
    }
}
