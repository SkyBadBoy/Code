package com.code.until;

/**
 * Created by MaJian on 18/2/3.
 */
public class CommonStatus {


    public static String WXPay_TansfersUrl="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public static boolean IsGetMoney=false;
    public enum Status {
        /** 删除*/
        Delete {
            public String getid() {
                return "88";
            }
        },
        /** 有效*/
        Ectivity {
            public String getid() {
                return "1";
            }
        },
        /** 正在游戏中 */
        Run {
            public String getid() {
                return "11";
            }
        },
        /** 游戏结束*/
        Over {
            public String getid() {
                return "12";
            }
        },
        /** 中奖*/
        Win {
            public String getid() {
                return "13";
            }
        }/** 未中奖*/
        ,NotWin {
            public String getid() {
                return "14";
            }
        };

        public abstract String getid();
    }

    public enum StatusEnum {
        /** 删除*/
        Delete(88),
        /** 有效*/
        Ectivity(1);
        public int seq;
        StatusEnum(int seq){
            this.seq = seq;
        }
    }
    public enum AuthorizeType {
        /** 所有平台*/
        All(0),
        /** 后台管理*/
        Admin(1),
        /** 接口*/
        Interface(2) ;
        public int seq;
        AuthorizeType(int seq){
            this.seq = seq;
        }
    }

}
