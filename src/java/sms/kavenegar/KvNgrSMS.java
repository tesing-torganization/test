/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sms.kavenegar;
import cms.tools.Server;
import cms.tools.ServerLog;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Holder;
/**
 *
 * @author Mohammad
 */
public class KvNgrSMS {
    public static List<Long> send(String messageBody, List<String> reciptionNumber) {
        List<Long> finalresult = new ArrayList<Long>();
        try {
            sms.kavenegar.V1Soap soap;
            sms.kavenegar.V1 v1 = new V1();
            soap = v1.getV1Soap();
            ArrayOfString reciptors = new ArrayOfString();
            reciptors.string = reciptionNumber;
            Holder<String> h1 = new Holder<String>();
            Holder<Integer> h2 = new Holder<Integer>();
            Holder<ArrayOfLong> h3 = new Holder<ArrayOfLong>();
//            KavenegarApi api = new KavenegarApi("4353303871516374503538384832426C676B716E4F513D3D");
//            soap.sendSimpleByApikey("4D5539466B65356D742F763763646D396D6E5A4936413D3D", "30003003000100", messageBody, reciptors, 0, 1, h2, h1, h3);
            soap.sendSimpleByApikey(Server.smsKey,Server.smsPanelNumber , messageBody, reciptors, 0, 1, h2, h1, h3);
//            SendResult result = new SendResult();
//            String reciptor= new String();
//            reciptor= "9133368036";
//            result=api.Send("20003535", reciptor.trim().toString(), "سلام تست سیستم کاوه نگار");
            ServerLog.Print("--------------------");
            ServerLog.Print(h1.value.toString());
            ServerLog.Print("--------------------");
            int size = reciptionNumber.size();
            for (int j = 0; j < size; j++) {
                finalresult.add(new Long(h2.value.longValue()));
                ServerLog.Print(j + " - Massage to: " + reciptionNumber.get(j) + " - result:" + finalresult.get(j) + ";   (" + messageBody + ") ");
            }
            ServerLog.Print(h2.value.toString());
            ServerLog.Print("--------------------");
            ServerLog.Print(h3.value.toString());
            ServerLog.Print("--------------------");
        } catch (ApiException ex) {
            // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            ServerLog.Print("!!!!!!!!!!!!!!!!!!!!!!");
            System.out.print("Message : " + ex.getMessage() + "خطای وب سرویس");
        } catch (HttpException ex) {
            // در زمانی که مشکلی در برقرای ارتباط با وب سرویس وجود داشته باشد این خطا رخ می دهد
            System.out.print("Message : " + ex.getMessage() + "خطا در برقرای ارتباط");
        }
        return finalresult;
    }

    public static void main(String[] args) {
        sms.kavenegar.V1Soap soap;
        try {
            sms.kavenegar.V1 v1 = new V1();
            soap = v1.getV1Soap();
            ArrayOfString reciptors = new ArrayOfString();
            List<String> list = new ArrayList<String>();
            list.add(0, "09374931859");
            reciptors.string = list;
            Holder<String> h1 = new Holder<String>();
            Holder<Integer> h2 = new Holder<Integer>();
            Holder<ArrayOfLong> h3 = new Holder<ArrayOfLong>();
//            KavenegarApi api = new KavenegarApi("4353303871516374503538384832426C676B716E4F513D3D");
//            soap.sendSimpleByApikey("4D5539466B65356D742F763763646D396D6E5A4936413D3D", "30003003000100", " شماره آقای هاشمی کاوه نگار تست", reciptors, 0, 1, h2, h1, h3);
            soap.sendSimpleByApikey("4D5539466B65356D742F763763646D396D6E5A4936413D3D", "30003003000100", " شماره آقای هاشمی کاوه نگار تست", reciptors, 0, 1, h2, h1, h3);
//            SendResult result = new SendResult();
//            String reciptor= new String();
//            reciptor= "9133368036";
//            result=api.Send("20003535", reciptor.trim().toString(), "سلام تست سیستم کاوه نگار");
            ServerLog.Print("--------------------");
            ServerLog.Print(h1.value.toString());
            ServerLog.Print("--------------------");
            ServerLog.Print(h2.value.toString());
            ServerLog.Print("--------------------");
            ServerLog.Print(h3.value.toString());
            ServerLog.Print("--------------------");
        } catch (ApiException ex) {
            // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            ServerLog.Print("!!!!!!!!!!!!!!!!!!!!!!");
            System.out.print("Message : " + ex.getMessage());
        } catch (HttpException ex) {
            // در زمانی که مشکلی در برقرای ارتباط با وب سرویس وجود داشته باشد این خطا رخ می دهد
            System.out.print("Message : " + ex.getMessage());
        }
    }
}
