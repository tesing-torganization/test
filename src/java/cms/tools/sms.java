/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.tools;

import java.util.ArrayList;
import java.util.List;
import jj.jjNumber;
import sms.kavenegar.KvNgrSMS;

/**
 *
 * @author Mohammad
 */
public class sms {

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

    public static String sendOneSms(String content, String MobileNumber) {
        try {
            if (MobileNumber == null) {
                MobileNumber = "0";
            }
            ServerLog.Print("...sending...\n  sms.sendOneSms(" + content + "," + MobileNumber + ");");
            String str = MobileNumber;
            str = str.replaceAll("\\s+", ",");
            str = str.replaceAll(",+", ",");
            str = str.replaceAll("[^0-9+]+", ",");
            String numbers[] = str.split(",");
            List<String> numbersList = new ArrayList<String>();
            for (int i = 0; i < numbers.length; i++) {
                ServerLog.Print(numbers[i] + " maches:" + numbers[i].matches("[(+989)(09)(9)]+[0-9]{9},{0,1}"));//TODO test and replace whit ^(\+989|(09)|(9))([0-9]{9})$
                if (numbers[i].matches("[(+989)(09)(9)]+[0-9]{9},{0,1}")) {
                    numbersList.add(numbers[i]);
                }
            }
            if (numbersList.isEmpty()) {
                return "شماره موبایل وارد شده صحیح نیست  ";
            }
            List<Long> result = new ArrayList<Long>();
            if (numbersList.size() > 50) {// اگر لیست بزرگتر از مقدار مشخصی بود در جند نوبت اس ام اس ارسال شود
                ServerLog.Print("send to :" + numbersList.size() + " Numbers ...");
                for (int i = 0; i < numbersList.size() / 50; i++) {
                    ServerLog.Print("send to :" + (i * 50) + "to" + ((i + 1) * 50 - 1));
                    result.addAll(KvNgrSMS.send(content, numbersList.subList(i * 50, (i + 1) * 50 - 1)));
//                    result.add(Long.MAX_VALUE);// برای تست رمانی که می خواهیم پیامک ارسال نشود
                }
                if (numbersList.size() % 50 > 0) {// برای قسمت آخر اگر تقسیم لیست به بخش های پنجاه تایی کاملا مساوی ممکن نبود
                    ServerLog.Print("send to :" + ((numbersList.size() / 50) * 50) + "to" + (numbersList.size()));
                    result.addAll(KvNgrSMS.send(content, numbersList.subList((numbersList.size() / 50) * 50, numbersList.size())));
//                    result.add(Long.MAX_VALUE);// برای تست رمانی که می خواهیم پیامک ارسال نشود
                }
            } else {// اگر اندازه لیست کمتر از مقدار مشخص بود
                result.addAll(KvNgrSMS.send(content, numbersList));
//                result.add(Long.MAX_VALUE);// برای تست رمانی که می خواهیم پیامک ارسال نشود
            }
            ServerLog.Print(result.toString());
            String resultstr = "";
            resultstr += result.size() > 2 ? result.size() * 50 + "تعداد" : result.toString();
            return resultstr;
        } catch (Exception ex) {
            return "...  sms.sendOneSms(" + content + "," + MobileNumber + ");" + ex;
        }
    }
    
    public static void main(String[] args) {
        String str = "09133368036";
        ServerLog.Print(str);
        String numbers[] = str.split(",");
        List<String> nubmersList = new ArrayList<String>();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].matches("[(+989)(09)(9)]+[0-9]{9},{0,1}")) {
                ServerLog.Print(numbers[i]);
                nubmersList.add(numbers[i]);
            }
        }
        String status = sms.sendOneSms("تست از طریق برنامه", str);

        ServerLog.Print(status);

    }
}
