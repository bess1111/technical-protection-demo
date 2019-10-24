package com.brt.manager.rest.controller.businessDevice;


import com.brt.manager.core.service.businessDevice.BusinessDeviceService;
import com.brt.manager.rest.vo.ResEntity;
import com.brt.manager.rest.vo.businessDevice.ViewBusinessDevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(tags="BusinessDeviceQueryController-业务设备管理接口")
@RestController
public class BusinessDeviceQueryController {

    @Autowired
    private BusinessDeviceService businessDeviceService;

    @ApiOperation(value="根据businessDeviceId查询业务设备")
    @ApiImplicitParam(paramType="path",name="businessDeviceId",value="业务设备",required=true,dataType="Long")
    @RequestMapping(value="/businessDevice/{businessDeviceId}",method=RequestMethod.GET)
    public ResEntity getBusinessDeviceById(@PathVariable Long businessDeviceId) {
        if(null==businessDeviceId) {
            return new ResEntity(null,false,"参数为空");
        }
        //查询
        ViewBusinessDevice resVo = businessDeviceService.getBusinessDeviceById(businessDeviceId);
        if(null==resVo) {
            return new ResEntity(null,false,"结果为空");
        }
        return new ResEntity(resVo,true,"查询成功");
    }

    @ApiOperation(value="查询业务设备数量")

    @RequestMapping(value="/businessDevice/count",method=RequestMethod.GET)
    public ResEntity getBusinessDeviceCount() {

        //查询
        Integer resVo = businessDeviceService.getBusinessDeviceNumber();
        if(null==resVo) {
            return new ResEntity(null,false,"结果为空");
        }
        return new ResEntity(resVo,true,"查询成功");
    }




    @ApiOperation(value="查询所有业务设备")
    @RequestMapping(value="/businessDevices",method=RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="pageNumber",value="第几页",required=false,dataType="Integer"),
            @ApiImplicitParam(paramType="query",name="pageSize",value="每页大小",required=false,dataType="Integer")
    })
    public ResEntity addBusinessDevice(	  @RequestParam(value="pageNumber",required=false) Integer pageNumber,
                                             @RequestParam(value="pageSize",required=false) Integer pageSize) {

        if ( pageSize == null || pageNumber == null) {
            return new ResEntity(null, false, "错误，传入参数为空");
        }
        Page<ViewBusinessDevice> resVo = businessDeviceService.getAllBusinessDeviceByPage(pageNumber,pageSize);
        if(null==resVo||resVo.isEmpty()) {
            return new ResEntity(null,false,"查询结果为空");
        }
        return new ResEntity(resVo,true,"查询成功");
    }




}
