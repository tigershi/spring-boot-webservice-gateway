package org.springframework.cloud.ws.ui.model;

import java.util.List;

/**
 *  * Author:Tiger Shi
 *  * Date:2024/12/4
 */
public class WebServiceInterfaceHtmlTemple {

    private static String htmlHead="<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "  <title>{WebserviceTitle}webservice地址描述</title>\n" +
            "  <style>\n" +
            "table{border-collapse: collapse; border-spacing: 0;}\n" +
            "  \n" +
            ".layui-table{width: 100%; margin: 10px 0; background-color: #fff; color: #5F5F5F;}\n" +
            ".layui-table tr{transition: all .3s; -webkit-transition: all .3s;}\n" +
            ".layui-table th{text-align: left; font-weight: 600;}\n" +
            "\n" +
            ".layui-table thead tr,\n" +
            ".layui-table-header,\n" +
            ".layui-table-tool,\n" +
            ".layui-table-total,\n" +
            ".layui-table-total tr,\n" +
            ".layui-table-patch{}\n" +
            ".layui-table-mend{background-color: #fff;}\n" +
            ".layui-table-hover,\n" +
            ".layui-table-click,\n" +
            ".layui-table[lay-even] tbody tr:nth-child(even){background-color: #f8f8f8;}\n" +
            ".layui-table-checked{background-color: #dbfbf0;}\n" +
            ".layui-table-checked.layui-table-hover,\n" +
            ".layui-table-checked.layui-table-click{background-color: #abf8dd;}\n" +
            ".layui-table-disabled-transition *,\n" +
            ".layui-table-disabled-transition *:before,\n" +
            ".layui-table-disabled-transition *:after{-webkit-transition:none!important;-moz-transition:none!important;-o-transition:none!important;-ms-transition:none!important;transition:none!important}\n" +
            "\n" +
            "\n" +
            ".layui-table th,\n" +
            ".layui-table td,\n" +
            ".layui-table[lay-skin=\"line\"],\n" +
            ".layui-table[lay-skin=\"row\"],\n" +
            ".layui-table-view,\n" +
            ".layui-table-tool,\n" +
            ".layui-table-header,\n" +
            ".layui-table-col-set,\n" +
            ".layui-table-total,\n" +
            ".layui-table-page,\n" +
            ".layui-table-fixed-r,\n" +
            ".layui-table-mend,\n" +
            ".layui-table-tips-main,\n" +
            ".layui-table-grid-down{border-width: 1px; border-style: solid; border-color: #eee;}\n" +
            "\n" +
            ".layui-table th, .layui-table td{position: relative; padding: 9px 15px; min-height: 20px; line-height: 20px;  font-size: 14px;}\n" +
            "\n" +
            ".layui-table[lay-skin=\"line\"] th, .layui-table[lay-skin=\"line\"] td{border-width: 0; border-bottom-width: 1px;}\n" +
            ".layui-table[lay-skin=\"row\"] th, .layui-table[lay-skin=\"row\"] td{border-width: 0;border-right-width: 1px;}\n" +
            ".layui-table[lay-skin=\"nob\"] th, .layui-table[lay-skin=\"nob\"] td{border: none;}\n" +
            "\n" +
            ".layui-table img{max-width:100px;}\n" +
            "  </style>\n" +
            "  <style>\n" +
            "    body {padding: 32px;}\n" +
            "  </style>\n" +
            "</head>\n" +
            "<body>";

     private static String tableHead="<table class=\"layui-table\">\n" +
             "  <caption><h3>{ModuleName}</h3></caption>\n" +
             "  <colgroup>\n" +
             "    <col width=\"400\">\n" +
             "    <col width=\"150\">\n" +
             "    <col>\n" +
             "  </colgroup>\n" +
             "  <thead>\n" +
             "    <tr>\n" +
             "      <th>接口名称</th>\n" +
             "      <th>描述项</th>\n" +
             "      <th>描述详情</th>\n" +
             "    </tr> \n" +
             "  </thead>\n" +
             "  <tbody>";
     private static String tableBody_url = "<tr>\n" +
             "      <td rowspan=\"4\">{interfaceName}</td>\n" +
             "      <td>接口地址</td>\n" +
             "      <td> <a href=\"{interfaceUri}\" target=\"_blank\">{interfaceUri}</a></td>\n" +
             "    </tr>\n" ;
    private static String tableBody_other = "<tr>\n" +
            "      <td>{ItemName}</td>\n" +
            "      <td>{ItemVal}</td>\n" +
            "    </tr>\n" ;
    private static String tableEnd = " </tbody>\n" +
            "</table>";
    private static String htmlEnd="</body>\n" +
            "</html>";

    public  String  getModules(String appName, List<WebServiceModuleDesc> modules){
        StringBuilder sb = new StringBuilder();
        sb.append(htmlHead.replace("{WebserviceTitle}", appName));


         for (WebServiceModuleDesc moduleDesc : modules){
            sb.append(tableHead.replace("{ModuleName}", moduleDesc.getName()+"-webservice描述"));
            for(WebServiceDesc desc : moduleDesc.getUris()){
                sb.append(tableBody_url.replace("{interfaceName}", desc.getName()).replace("{interfaceUri}", desc.getWebServiceUri()));
                sb.append(tableBody_other.replace("{ItemName}", "接口描述").replace("{ItemVal}", desc.getDesc()));
                sb.append(tableBody_other.replace("{ItemName}", "最后修改时间").replace("{ItemVal}", desc.getLastUpdateTime()));
                sb.append(tableBody_other.replace("{ItemName}", "接口版本").replace("{ItemVal}", desc.getVersion()));
            }
            sb.append(tableEnd);
         }
         sb.append(htmlEnd);
         return sb.toString();
    }


}
