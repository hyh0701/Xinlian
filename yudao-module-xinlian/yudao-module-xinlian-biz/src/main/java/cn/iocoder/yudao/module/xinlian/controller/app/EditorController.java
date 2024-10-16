package cn.iocoder.yudao.module.xinlian.controller.app;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.module.infra.controller.app.file.vo.AppFileUploadReqVO;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import com.alibaba.druid.util.Utils;
import com.baidu.ueditor.ActionEnter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @author Administrator
 */
@Tag(name = "用户APP - ueditor")
@RestController
@RequestMapping("/ueditor")
@Validated
@Slf4j
public class EditorController {

    @javax.annotation.Resource
    private FileService fileService;
    /**
     * 读取配置文件
     * @param action
     * @param noCache
     * @return
     * @throws IOException
     */
    @GetMapping("/config")
    public String getConfig(String action,String noCache) throws IOException {

        Resource resource = new ClassPathResource("config.json");
        InputStream in=resource.getInputStream();
        HttpServletRequest request = ServletUtils.getRequest();
       // String path =
        String rootPath = request.getSession().getServletContext().getRealPath("/");
       // String decoder = URLDecoder.decode()
        String exec = new ActionEnter(request,"config.json").exec();
        System.out.println(exec);
        if (in != null) {
            String  text = Utils.read(in);
            return text;
        }
        return null;
    }

    /**
     * editor通用上传请求（单个）
     */
    @PostMapping("/imageUpload")
    public Map<String, Object> editorUploadFile(HttpServletRequest request) throws Exception
    {
        Map<String, Object> result = new HashMap<>();
        try {
            // 从request中获取上传的图片数据
            AppFileUploadReqVO uploadReqVO = new AppFileUploadReqVO();
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("upfile");
            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            uploadReqVO.setFile(file);
            uploadReqVO.setPath(fileName);
            String path = uploadReqVO.getPath();

            String url = fileService.createFile(fileName, path, IoUtil.readBytes(file.getInputStream()));
            // TODO: 处理图片上传逻辑，例如存储到本地文件系统或数据库

            // 返回图片的URL地址
            result.put("state", "SUCCESS");
            result.put("url", url);
            result.put("title", fileName);
            result.put("original", fileName);
        } catch (Exception e) {
            result.put("state", "FAIL");
            result.put("error", e.getMessage());
        }

        return result;
    }


    /**
     * 抓取远程图片
     */
    @RequestMapping("/catchRemoteFile")
    public Map<String, Object> catchRemoteFile(@RequestParam(name = "source[]") List<String> sources) {
        Map<String, Object> map = new HashMap<>();
//        if (StringUtils.isBlank(source)) {
//            map.put("state", "FAIL");
//            map.put("message", "上传文件不能为空");
//            return map;
//        }
//        String[] sources = source.split("，");
        if (CollectionUtils.isEmpty(sources)) {
            map.put("state", "FAIL");
            map.put("message", "上传文件不能为空");
            return map;
        }
        String[] array = new String[sources.size()];
        sources.toArray(array);
        List<Map<String, Object>> list = uploadRemoteFileToAliyun(array,ServletUtils.getRequest());
        map.put("list", list);
        map.put("state", "SUCCESS");
        return map;
    }

    /**
     * uploadRemoteFileToAliyun:(上传远程图片到阿里云). <br>
     *
     * @param sources 远程图片地址
     * @return
     *
     * @since 1.0
     */
    private List<Map<String, Object>> uploadRemoteFileToAliyun(String[] sources,HttpServletRequest request) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
       // String aliyunDomain = ossService.getConfig().getAliyunDomain();
        for (int i = 0; i < sources.length; i++) {
            try {
                String source = sources[i];
                if (StringUtils.isNotBlank(source)) {
                    URL url = new URL(source);
                    URLConnection con = url.openConnection();
                    try (InputStream is = con.getInputStream();) {
                        // 取出后缀名
                        String suffix = source.substring(source.lastIndexOf("."), source.length());
                        //获取文件名
                        String fileOrg = getFileNameFromUrl(source);
                        // 生成唯一的文件名
                        String fileName = UUID.randomUUID().toString() + "_" + fileOrg;
                        //上传文件
                        String url2 = fileService.createFile(fileName, fileName, IoUtil.readBytes(is));

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("source", source);
                        map.put("url", url2);
                        map.put("state", "SUCCESS");
                        result.add(map);
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return result;
    }

    private String getFileNameFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
