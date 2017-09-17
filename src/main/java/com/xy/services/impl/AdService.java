package com.xy.services.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xy.models.Ad;
import com.xy.services.IAdService;
import com.xy.utils.Config;
import com.xy.utils.DateUtils;
import com.xy.utils.FileUtils;
import com.xy.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdService extends BaseService<Ad> implements IAdService {

    @Override
    public int saveSelective(Ad entity) {
        entity = this.handleInfo(entity);

        entity.setUuid(StringUtils.getUuid());
        entity.setAddTime(DateUtils.getCurrentDate());

        return super.saveSelective(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(Ad entity) {
        entity = this.handleInfo(entity);
        return super.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List selectList(Ad entity) {
        return this.handleResult(super.selectList(entity));
    }

    @Override
    public PageInfo<Ad> selectPageInfoByCondition(Condition condition, int offset, int limit) {
        PageInfo<Ad> ads = super.selectPageInfoByCondition(condition, offset, limit);
        ads.setList(this.handleResult(ads.getList()));
        return ads;
    }

    private Ad handleInfo(Ad entity) {
        try {
            Document etDoc = null, otDoc = null;
            Elements etImgEle = null, otImgEle = null;
            Gson gson = new Gson();
            Map<String, Object> etVideoMap = null;
            Map<String, Object> otVideoMap = null;


            if (StringUtils.isNotNull(entity.getImgUrl())) {
                FileUtils.moveFile(Config.FILETEMP + entity.getImgUrl(), Config.ADVIMGPATH);
            }

            if (StringUtil.isNotEmpty(entity.getVideoInfo()) && "inner".equals(entity.getVideoType())) {
                etVideoMap = gson.fromJson(entity.getVideoInfo(), Map.class);
                if (StringUtils.isNotNull(etVideoMap.get("img"))) {
                    FileUtils.moveFile(Config.FILETEMP + etVideoMap.get("img"), Config.ADVIDEOPATH);
                }
                if (StringUtils.isNotNull(etVideoMap.get("video"))) {
                    FileUtils.moveFile(Config.FILETEMP + etVideoMap.get("video"), Config.ADVIDEOPATH);
                }
            }

            /**
             * 保存内部广告链接详情到文件
             * 1.解析html，移动上传的临时图片到正式文件夹
             * 2.修改图片指向的地址
             * 3.保存至文件夹
             */
            List<String> etDetailImg = new ArrayList<>();
            if ("innerUrl".equals(entity.getType()) && StringUtils.isNotNull(entity.getGotoInfo())) {
                etDoc = Jsoup.parse(entity.getGotoInfo());
                etImgEle = etDoc.select("img");
                // 上传的图片名
                etImgEle.forEach(element -> {
                    etDetailImg.add(element.attr("title"));
                    com.xy.utils.FileUtils.moveFile(Config.FILETEMP + element.attr("title"), Config.ADVIMGPATH);
                    element.attr("src", Config.ADVIMGURL + element.attr("title"));
                });

                byte[] detail = etDoc.getElementsByTag("body").get(0).children().toString().getBytes();
                String detailName = StringUtils.getUuid() + ".spd";

                org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(Config.DESADPATH + detailName), detail, false);

                entity.setGotoInfo(detailName);
            }


            // 修改操作，删除修改的文件
            if (StringUtils.isNotNull(entity.getUuid())) {
                Ad other = super.selectOnlyByKey(entity.getUuid());

                List<String> delMoreImg = new ArrayList<>();

                //原广告缩略图是否需要删除
                if (StringUtils.isNotNull(entity.getImgUrl()) && !other.getImgUrl().equals(entity.getImgUrl())) {
                    delMoreImg.add(other.getImgUrl());
                }

                // 原视频文件是否一致
                if (StringUtils.isNotNull(other.getVideoInfo()) && "inner".equals(entity.getVideoType())) {
                    otVideoMap = gson.fromJson(other.getVideoInfo(), Map.class);
                    if (StringUtils.isNotNull(otVideoMap.get("img")) && StringUtils.isNotNull(etVideoMap.get("img")) && !otVideoMap.get("img").equals(etVideoMap.get("img"))) {
                        FileUtils.deleteFile(Config.ADVIDEOPATH + otVideoMap.get("img"));
                        FileUtils.deleteFile(Config.ADVIDEOPATH + otVideoMap.get("video"));
                    }
                }

                // 内部链接广告详情
                // 原详情和现详情是否一致，删除修改过的图片
                File advDetilHtml = new File(Config.DESADPATH + other.getGotoInfo());
                if (advDetilHtml.exists()) {
                    otDoc = Jsoup.parse(advDetilHtml, "UTF-8");
                    if (otDoc != null && otDoc.childNodeSize() > 0) {
                        List<String> otDetailsImg = new ArrayList<>();
                        etImgEle = otDoc.select("img");
                        etImgEle.forEach(element -> {
                            otDetailsImg.add(element.attr("title"));
                        });
                        otDetailsImg.removeAll(etDetailImg);

                        delMoreImg.addAll(otDetailsImg);
                    }

                    delMoreImg.forEach(s -> {
                        FileUtils.deleteFile(Config.ADVIMGPATH + s);
                    });

                    FileUtils.deleteFile(Config.DESADPATH + other.getGotoInfo());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }


    public List<Ad> handleResult(List<Ad> ads) {
        ads.forEach(ad -> {
            ad.setImgUrlShow(Config.ADVIMGURL + ad.getImgUrl());
            if ("innerUrl".equals(ad.getType())) {
                ad.setGotoInfo(Config.DESADURL + ad.getGotoInfo());
            }
            if (StringUtils.isNotNull(ad.getVideoInfo()) && "inner".equals(ad.getVideoType())) {

                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(ad.getVideoInfo(), Map.class);
                map.put("imgShow", Config.ADVIDEOURL + map.get("img"));
                map.put("videoShow", Config.ADVIDEOURL + map.get("video"));
                ad.setVideoInfo(gson.toJson(map));
            }
        });
        return ads;
    }
}
