import com.xy.utils.SmsUtil;

public class MainTest {
    public static void main(String[] args) {
        // 加密
//        System.out.println(Md5Util.md5UpperCase("000000"));
        // 随机数
//        System.out.println(RandomUtil.getRandom(32, RandomUtil.TYPE.LETTER_CAPITAL_NUMBER));

//        for (int i = 0; i < 10; i++) {
//            System.out.println(Utils.rangeRandom(0, 7));
//        }


        new SmsUtil().sendCode("17629213419");


    }

}
