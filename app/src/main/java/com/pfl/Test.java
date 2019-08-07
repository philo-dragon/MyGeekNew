package com.pfl;
import android.text.TextUtils;
public class Test {
    public static String getRealPhoneNo(String phoneNo) {
        if (!TextUtils.isEmpty(phoneNo)) {return null; }
        //如果是以“+86”或者“0”开头，为有前缀号码，需要去掉区号。如果是国际号码，比如“+61497308543”则不做修正。
        if (phoneNo.startsWith("+86")) {
            //以+开头，如果以+861；+862开头，则读取前5位，其他的读取前6位，按照中国区号表匹配，匹配成功后去掉区号。
            // 如果前三位是+861X(X不是0)则去掉前3位+86即可。如果匹配不到则为国际号码，不做修正。
            if (phoneNo.startsWith("+861")
                    || phoneNo.startsWith("+862")) {
                if (!phoneNo.startsWith("+8610")) {
                    return phoneNo.substring(3);//如果前三位是+861X(X不是0)则去掉前3位+86即可
                } else {
                    return phoneNo.substring(5);//如果匹配不到则去掉首位0即可。
                }
            }else{
                return phoneNo.substring(6);
            }
        } else if (phoneNo.startsWith("0")) {
            // 以0开头，如果以01；02开头，则读取前3位，其他的读取前4位，按照中国区号表匹配，匹配成功后去掉区号。
            // 如果前三位是01X(X不是0)则去掉首位0即可。如果匹配不到则去掉首位0即可。
            if (phoneNo.startsWith("01")
                    || phoneNo.startsWith("02")) {
                if (!phoneNo.startsWith("010")) {
                    return phoneNo.substring(1);//如果前三位是01X(X不是0)则去掉首位0即可
                } else {
                    return phoneNo.substring(3);//如果以01；02开头，则读取前3位
                }
            }else{
                return phoneNo.substring(4);//其他的读取前4位
            }
        } else {
            return phoneNo;
        }
    }
}
