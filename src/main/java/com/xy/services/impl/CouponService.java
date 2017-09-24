package com.xy.services.impl;

import com.github.pagehelper.PageInfo;
import com.xy.models.Coupon;
import com.xy.services.ICouponService;
import com.xy.services.IShopCategroyService;
import com.xy.services.IShopService;
import com.xy.utils.Config;
import com.xy.utils.DateUtils;
import com.xy.utils.RandomUtil;
import com.xy.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

@Service
public class CouponService extends BaseService<Coupon> implements ICouponService {

    @Autowired
    private IShopCategroyService categroyService;
    @Autowired
    private IShopService shopService;


    @Override
    public PageInfo<Coupon> selectPageInfoByCondition(Condition condition, int offset, int limit) {
        PageInfo<Coupon> pages = super.selectPageInfoByCondition(condition, offset, limit);
        pages.setList(this.handleResult(pages.getList()));
        return pages;
    }


    @Override
    public int saveSelective(Coupon entity) {
        entity = this.handleInfo(entity);
        entity.setUuid(StringUtils.getUuid());
        entity.setNumber(Config.XY_COUPON_PREFIX + RandomUtil.getRandom(12, RandomUtil.TYPE.NUMBER));
        entity.setAuthor("lord");
        entity.setAddTime(DateUtils.getCurrentDate());
        return super.saveSelective(entity);
    }


    @Override
    public int updateByPrimaryKeySelective(Coupon entity) {
        entity = this.handleInfo(entity);
        return super.updateByPrimaryKeySelective(entity);
    }



    @Override
    public void startCoupon() {
        Condition cond = new Condition(Coupon.class);
        cond.createCriteria().andLessThanOrEqualTo("startTime", DateUtils.getDate());

        List<Coupon> list = super.selectListByCondition(cond);
        list.forEach(coupon -> {
            coupon.setStatus("online");
            super.updateByPrimaryKeySelective(coupon);
        });
    }


    private Coupon handleInfo(Coupon entity) {

        // 未设置有效期开始日
        if (StringUtils.isNull(entity.getStartTime())) {
            entity.setStartTime(DateUtils.getDate());
        }
        //未设置有效期开始日,表示永久有效
        if (StringUtils.isNull(entity.getEndTime())) {
            entity.setEndTime("forever");
        }


        if (entity.getStartTime().equals(DateUtils.getDate())) {
            entity.setStatus("online");
        } else {
            // 每天晚上 凌晨 扫描需要上线的优惠活动
            entity.setStatus("waitOnline");
        }

        return entity;
    }


    public List<Coupon> handleResult(List<Coupon> coupons) {
        coupons.forEach(coupon -> {
            String value = coupon.getToGoods();
            if (value.equals("cate")) {
                coupon.setToGoodsValueText(categroyService.selectOnlyByKey(coupon.getToGoodsValue()).getName());
            } else if(value.equals("shop")) {
                coupon.setToGoodsValueText(shopService.selectOnlyByKey(coupon.getToGoodsValue()).getName());
            }

        });
        return coupons;
    }

}