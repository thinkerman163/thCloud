
package com.glodon.water.model.service;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.glodon.water.common.common.vo.PageInfoVo;
import com.glodon.water.common.common.vo.ReturnPageVo;

/**
 * @Description service基类
 * @author (作者) liuy-8
 * @date (开发日期) 2016年3月14日 上午9:58:43
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class BaseService {

    /**
     * @Description 放入分页信息，减少代码冗余
     * @author liuy-8
     * @date 2016年3月14日 上午9:27:35
     */
    protected ReturnPageVo returnPage(List<? extends Object> list) {
        if (list==null||list.size()==0) {
            return new ReturnPageVo();
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        PageInfo pageInfo = new PageInfo(list);
        ReturnPageVo pageVo = new ReturnPageVo();
        pageVo.setPage(pageInfo.getPageNum());
        pageVo.setNum(pageInfo.getTotal());
        pageVo.setPageSum(pageInfo.getPages());
        pageVo.setDataSize(list.size());

        List<Object> objList = new ArrayList<Object>();
        objList.addAll(list);
        pageVo.setData(objList);
        return pageVo;
    }

    /**
     * @Description 调用分页插件，减少代码冗余
     * @author liuy-8
     * @date 2016年3月14日 上午9:57:05
     */
    @SuppressWarnings("rawtypes")
    protected Page startPage(PageInfoVo vo) {
        // 设置分页参数
        return PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
    }
    
    /**
     * @Description 调用分页插件，减少代码冗余
     * @author liuy-8
     * @date 2016年3月14日 上午9:57:05
     */
    @SuppressWarnings("rawtypes")
    protected Page startPageNoCount(PageInfoVo vo) {
        // 设置分页参数
        return PageHelper.startPage(vo.getPageIndex(), vo.getPageSize(),false);
    }

}
