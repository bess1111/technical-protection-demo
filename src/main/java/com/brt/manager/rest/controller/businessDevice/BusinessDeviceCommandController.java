package com.brt.manager.rest.controller.businessDevice;


import com.brt.manager.core.service.businessDevice.BusinessDeviceService;
import com.brt.manager.rest.vo.ResEntity;
import com.brt.manager.rest.vo.businessDevice.ViewBusinessDevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(tags="BusinessDeviceQueryController-业务设备管理接口")
@RestController
public class BusinessDeviceCommandController {

    @Autowired
    private BusinessDeviceService businessDeviceService;

    @ApiOperation(value="新增业务设备")
    @ApiImplicitParam(paramType="body",name="viewBusinessDevice",value="业务设备",required=true,dataType="ViewBusinessDevice")
    @RequestMapping(value="/businessDevice",method=RequestMethod.POST)
    public ResEntity addBusinessDevice(@RequestBody ViewBusinessDevice viewBusinessDevice) {
        if(null==viewBusinessDevice) {
            return new ResEntity(null,false,"参数为空");
        }
        ViewBusinessDevice resVo = businessDeviceService.addBusinessDevice(viewBusinessDevice);
        if(null==resVo) {
            return new ResEntity(null,false,"添加失败");
        }
        return new ResEntity(resVo,true,"添加成功");
    }

    //删除某个业务设备
    @RequestMapping(value="/businessDevice/{businessDeviceId}",method=RequestMethod.DELETE)
    @ApiOperation(value="根据业务设备id业务删除设备接口",notes="根据业务设备id删除业务设备接口")
    @ApiImplicitParam(paramType="path",name="businessDeviceId",value="业务设备id",required=true,dataType="Long")
    public ResEntity deleteBusinessDevice(@PathVariable Long businessDeviceId) {
        if(null==businessDeviceId) {
            return new ResEntity(null,false,"传入参数为空");
        }
        Boolean resVo = businessDeviceService.deleteBusinessDevice(businessDeviceId);
        if(null!=resVo && resVo) {
            return new ResEntity(resVo,true,"成功");
        }
        return new ResEntity(null,false,"失败");
    }


    @ApiOperation(value="修改业务设备")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="body",name="viewBusinessDevice",value="业务设备",required=true,dataType="ViewBusinessDevice"),
            @ApiImplicitParam(paramType="path",name="businessDeviceId",value="业务设备id",required=true,dataType="Long")
    })
    @RequestMapping(value="/businessDevice/{businessDeviceId}",method=RequestMethod.PUT)
    public ResEntity updateCourse(@RequestBody ViewBusinessDevice viewBusinessDevice,@PathVariable Long businessDeviceId) {
        if(null==viewBusinessDevice||null==businessDeviceId) {
            return new ResEntity(null,false,"参数为空");
        }
        ViewBusinessDevice resVo = businessDeviceService.updateBusinessDevice(viewBusinessDevice,businessDeviceId);



        if(null==resVo) {
            return new ResEntity(null,false,"更新失败");
        }
        return new ResEntity(resVo,true,"添加成功");
    }

}
