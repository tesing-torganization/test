package cms.cms;


import cms.access.*;
import cms.tools.Js;
import cms.tools.Server;
import cms.tools.ServerLog;
import cms.tools.jjTools;
import cms.tools.jjValidation;
import cms.tools.sms;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import jj.*;

public class SMS {

    public static String tableName = "sms";
    public static String _id = "id";
    public static String _text = "sms_text";
    public static String _mobileNubmers = "sms_nubmers";
    public static String _status = "sms_status";
    public static String _date = "sms_date";
    public static String _email = "sms_email";
    public static String _first_name = "sms_first_name";
    public static String _last_name = "sms_last_name";
    public static String _comment = "sms_comment";
    public static String _subject = "sms_subject";
    public static String _userId = "sms_userId";
    public static String lbl_send = "ارسال";
    public static String lbl_delete = "حذف";
    public static String lbl_reSend = "ارسال مجدد";
    
    
    public static String lbl_subject_std = "ارسال به دانشجو";
    public static String lbl_subject_manager = "ارسال توسط مدیریت ";
    public static String lbl_subject_birthDay = "تبریک تولید";
    public static String lbl_subject_payment = "شهریه";
    
    
    public static int rul_rfs = 51;
    public static int rul_send = 52;
    public static int rul_conf = 53;
    public static int rul_dlt = 54;
//    public static int rul_lng = 10;

    public static String refresh(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_rfs);
            if (!hasAccess.equals("")) {
                return hasAccess;
            }
            StringBuilder html = new StringBuilder();
            List<Map<String, Object>> row = jjDatabase.separateRow(db.SelectDesc(tableName, _id, _id));
            html.append("<table id='refreshSMS' class='tahoma10' style='direction: rtl;width:982px'><thead>");
            html.append("<th width='5%'>کد</th>");
            html.append("<th width='10%'>نام مخاطب</th>");
            html.append("<th width='10%'>شماره تلفن</th>");
            html.append("<th width='35%'>متن پیامک</th>");
            html.append("<th width='10%'>تاریخ ارسال</th>");
            html.append("<th width='10%'>توضیحات</th>");
            html.append("<th width='10%'>وضغیت</th>");
            html.append("<th width='5%'>حذف</th>");
            html.append("<th width='5%'>عملیات</th>");
            html.append("</thead><tbody>");
            for (int i = 0; i < row.size(); i++) {
                html.append("<tr>");
                html.append("<td class='c'>" + (row.get(i).get(_id).toString()) + "</td>");
                html.append("<td class='r'>" + (row.get(i).get(_first_name).toString()) + " " + (row.get(i).get(_last_name).toString()) + "</td>");
                html.append("<td class='r'>" + (row.get(i).get(_mobileNubmers).toString()) + "</td>");
                html.append("<td class='r'>" + (row.get(i).get(_text).toString()) + "</td>");
                html.append("<td class='r'>" + (jjCalendar_IR.getViewFormat(row.get(i).get(_date))) + "</td>");
                html.append("<td class='r'>" + (row.get(i).get(_comment).toString()) + "</td>");
                html.append("<td class='r'>" + (row.get(i).get(_status).toString()) + "</td>");
                html.append("<td class='mousePointer' onclick='cmsSMS.m_delete(" + row.get(i).get(_id) + ");' ><img src='img/del.png' style='height:30px' /></td>");
                html.append("<td class='mousePointer' onclick='cmsSMS.m_select(" + row.get(i).get(_id) + ");'><img src='img/l.png' style='height:30px' /></td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
            String height = jjTools.getParameter(request, "height");
            String panel = jjTools.getParameter(request, "panel");
            if (!jjNumber.isDigit(height)) {
                height = "400";
            }
            if (panel.equals("")) {
                panel = "swSMSTbl";
            }
            String html2 = "cmsSMS.m_show_tbl();";
            html2 += Js.setHtml("#" + panel, html.toString());
            html2 += Js.table("#refreshSMS", height, 0, Access_User.getAccessDialog(
                    request, db, rul_send).equals("") ? "18" : "", "لیست پیامک ها");
            //"18" is tblCode in cmsManager.js
            return html2;
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    public static String insert(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_send);
            if (!hasAccess.equals("")) {
                return hasAccess;
            }
            ServerLog.Print("Send sms----------------------------------");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(_comment, jjTools.getParameter(request, _comment));
            map.put(_comment, jjTools.getParameter(request, _comment));
            map.put(_date, jjCalendar_IR.getDatabaseFormat_8length(jjTools.getParameter(request, _date), true));
            map.put(_email, jjTools.getParameter(request, _email));
            map.put(_first_name, jjTools.getParameter(request, _first_name));
            map.put(_last_name, jjTools.getParameter(request, _last_name));
            map.put(_subject, jjTools.getParameter(request, _subject));
            map.put(_mobileNubmers, jjTools.getParameter(request, _mobileNubmers));
            map.put(_text, jjTools.getParameter(request, _text));
//            map.put(_userId, jjTools.getParameter(request, _userId));
            String status = sms.sendOneSms(jjTools.getParameter(request, _text), jjTools.getParameter(request, _mobileNubmers));
//            String status = "20001";
            map.put(_status, status);
            if (db.insert(tableName, map).getRowCount() == 0) {
                String errorMessage = "عملیات درج به درستی صورت نگرفت.";
                if (jjTools.getParameter(request, "myLang").equals("en")) {
                    errorMessage = "Edit Fail;";
                }
                return Js.dialog(errorMessage);
            }
            ServerLog.Print("----------------------------------End");
            return Js.dialog("وضعیت ارسال= "+status)
                    + Js.jjSMS.refresh();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }
    public static String insert(HttpServletRequest request, jjDatabaseWeb db,String text, String mobileNo, String subject, String coment, String email, String firstName, String LastName, Integer date) {
        try {
            text = text ==null ? "" : text;
            mobileNo = mobileNo ==null ? "" : mobileNo;
            subject = subject ==null ? "سیستم" : subject;
            coment = coment ==null ? "" : coment;
            email = email ==null ? "" : email;
            firstName = firstName ==null ? "" : firstName;
            LastName = LastName ==null ? "" : LastName;
            jjCalendar_IR ir = new jjCalendar_IR();
            date = date ==null ? ir.getDBFormat_8length() : date;
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(_mobileNubmers, mobileNo);
            map.put(_text, text);
            map.put(_subject, subject);
            map.put(_comment, coment);
            map.put(_date, date);
            map.put(_email, email);
            map.put(_first_name, firstName);
            map.put(_last_name, LastName);
//          map.put(_userId, jjTools.getParameter(request, _userId));
            String status = sms.sendOneSms(text, mobileNo);
            if(status==null){
                status = "Server Error: 704";
            }
            map.put(_status, status);
            if (db.insert(tableName, map).getRowCount() == 0) {
//            if (db.otherStatement("INSERT INTO sms (sms_subject,sms_text,sms_nubmers,sms_first_name,sms_status,sms_email,sms_comment,sms_date,sms_last_name) VALUES ('پیامک به کلاس','تست 7','[9374931859]','','[200]','','',0,'');")== false) {
                String errorMessage = "عملیات درج به درستی صورت نگرفت.";
                return errorMessage;
            }
            return ("وضعیت ارسال= "+status);
        }catch(Exception ex){
            return Server.ErrorHandler(ex);
        }
    }

    public static String add_new(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            StringBuilder html = new StringBuilder();
            boolean accIns = Access_User.hasAccess2(request, db, rul_send);
            if (accIns) {
                html.append(Js.setHtml("#SMS_button", "<input type=\"button\" id=\"insert_SMS_new\" value=\"" + lbl_send + "\" class=\"tahoma10\">"));
                html.append(Js.buttonMouseClick("#insert_SMS_new", Js.jjSMS.insert()));//send sms
                html.append(Js.setVal("#" + _date, jjCalendar_IR.getViewFormat(new jjCalendar_IR().getDBFormat_8length())));//send sms
                html.append(Js.setVal("#" + _status, "ارسال نشده، در حال ایجاد توسط کاربر..."));//send sms
            }
            return html.toString();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    public static String delete(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_dlt);
            if (!hasAccess.equals("")) {
                return hasAccess;
            }
            String id = jjTools.getParameter(request, _id);
            String errorMessageId = jjValidation.isDigitMessageFa(id, "کد");
            if (!errorMessageId.equals("")) {
                if (jjTools.isLangEn(request)) {
                    errorMessageId = jjValidation.isDigitMessageEn(id, "ID");
                }
                return Js.dialog(errorMessageId);
            }
            if (id.equals("1")) {
                String errorMessageId2 = "شما اجازه حذف این رکورد را ندارید.";
                if (jjTools.isLangEn(request)) {
                    errorMessageId2 = "You can't delete this record.";
                }
                return Js.dialog(errorMessageId2);
            }
            if (!db.delete(tableName, _id + "=" + id)) {
                String errorMessage = "عملیات حذف به درستی صورت نگرفت";
                if (jjTools.isLangEn(request)) {
                    errorMessage = "Delete Fail;";
                }
                return Js.dialog(errorMessage);
            }
            return Js.jjSMS.refresh();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    public static String select(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String id = jjTools.getParameter(request, _id);
            String errorMessageId = jjValidation.isDigitMessageFa(id, "کد");
            if (!errorMessageId.equals("")) {
                if (jjTools.isLangEn(request)) {
                    errorMessageId = jjValidation.isDigitMessageEn(id, "ID");
                }
                return Js.dialog(errorMessageId);
            }
            List<Map<String, Object>> row = jjDatabase.separateRow(db.Select(tableName, _id + "=" + id));
            if (row.isEmpty()) {
                String errorMessage = "رکوردی با این کد وجود ندارد.";
                if (jjTools.isLangEn(request)) {
                    errorMessage = "Select Fail;";
                }
                return Js.dialog(errorMessage);
            }
            StringBuilder html = new StringBuilder();
            StringBuilder html2 = new StringBuilder();

            html.append(Js.setVal("#" + tableName + "_" + _id, row.get(0).get(_id)));
            html.append(Js.setVal("#" + _comment, row.get(0).get(_comment)));
            html.append(Js.setVal("#" + _subject, row.get(0).get(_subject)));
            html.append(Js.setVal("#" + _date, jjCalendar_IR.getViewFormat(row.get(0).get(_date))));
//            html.append(Js.setVal("#" + _email, row.get(0).get(_email)));
            html.append(Js.setVal("#" + _first_name, row.get(0).get(_first_name)));
            html.append(Js.setVal("#" + _last_name, row.get(0).get(_last_name)));
            html.append(Js.setVal("#" + _mobileNubmers, row.get(0).get(_mobileNubmers)));
            html.append(Js.setVal("#" + _status, row.get(0).get(_status)));
            html.append(Js.setVal("#" + _text, row.get(0).get(_text)));
//            html.append(Js.setVal("#" + _userId, row.get(0).get(_userId)));

            boolean accDel = Access_User.hasAccess2(request, db, rul_dlt);
            boolean accEdt = Access_User.hasAccess2(request, db, rul_send);
//            boolean acclng = Access_User.hasAccess2(request, db, rul_lng);

            if (accEdt) {
                html2.append("<input type='button' id='edit_SMS' value='" + lbl_reSend + "' class='tahoma10'>");
                html.append(Js.buttonMouseClick("#edit_SMS", Js.jjSMS.edit()));
            }
            if (accDel) {
                html2.append("<input type='button' id='delete_SMS' value='" + lbl_delete + "' class='tahoma10'  />");
                html.append(Js.buttonMouseClick("#delete_SMS", Js.jjSMS.delete(id)));

            }
            return (Js.setHtml("#SMS_button", html2.toString())) + html.toString();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }
}
