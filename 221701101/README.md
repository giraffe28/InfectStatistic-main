# InfectStatistic-221701101
|这个作业属于哪个课程|<a href="https://edu.cnblogs.com/campus/fzu/2020SpringW">2020春\|W班 (福州大学)</a>|
|--|--| 
|这个作业的要求在哪里|<a href="https://edu.cnblogs.com/campus/fzu/2020SpringW/homework/10281">寒假作业（2/2）——疫情统计</a>|   
|这个作业的目标|Git命令、Github Desktop使用,开发一个疫情统计程序|   
|作业正文|<a href="https://www.cnblogs.com/lu28/p/12249063.html">寒假作业（2/2）</a>| 
|其他参考文献|无|
#Github仓库地址
<a href="https://github.com/giraffe28/InfectStatistic-main">giraffe28</a>
#PSP表格
PSP2.1 | Personal Software Process Stages | 预估耗时（分钟）|   实际耗时（分钟）
--|:--:|--:|--:
Planning|计划|60|60
Estimate|估计这个任务需要多少时间|30|20         
Development|开发|1120|1126
Analysis|需求分析 (包括学习新技术)|300|330       
Design Spec|生成设计文档|40|60       
Design Review|设计复审|30|25        
Coding Standard|代码规范 (为目前的开发制定合适的规范)|60|64      
Design|具体设计|120|125       
Coding|具体编码|480|400       
Code Review|代码复审|30|32      
Test|测试（自我测试，修改代码，提交修改）|60|90       
Reporting|报告|420|642      
Test Report|测试报告|180|420      
Size Measurement|计算工作量|60|72        
Postmortem & Process Improvement Plan|事后总结, 并提出过程改进计划|180|150         
|合计||1630|1848
#我的解题思路
- 首先，看了好几遍作业的要求。
- 自己理解一下，就是：在命令行输入 $ java InfectStatistic list -date 2020-01-22 -log D:/log/ -out D:/output.txt
其中，java InfectStatistic表示执行主类InfectStatistic，list为命令，
程序从D:/log/目录下读取所有2020-01-22之前（包括2020-01-22）的日志文件，经过处理后将结果输出到D:/output.txt文件。
- 程序流程图
![](https://images.cnblogs.com/cnblogs_com/lu28/1639989/o_200218053503%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(3).png)
#设计实现
###类
`Province`//保存排好序的各个省份
`Parameters`//保存参数信息
`Regular`//对应语句的正则表达式
`Result`//一个省份对应一个Result
`ResultList`//由Result组成
###函数
`parseOption`//解析命令行传入的参数
`getLogFiles`//获得相关所有日志的文件路径
`parseLog`//解析一篇日志
`getIpResult`//使某省感染患者+n
`getSpResult`//使某省疑似患者+n
`getCureResult`//使某省治愈患者+n
`getDeadResult`//使某省死亡患者+n
`getIpMoveResult`//使省1感染患者+n，省2感染患者-n
`getSpMoveResult`//使省1疑似患者+n，省2疑似患者-n
`getSpToIpResult`//使某省疑似患者-n，感染患者+n
`getSpClearResult`//使某省疑似患者-n
`mergeList`//合并相同省份数据
`outPut`//将结果输出到-out指定文件
![](https://images.cnblogs.com/cnblogs_com/lu28/1639989/o_200218055232%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(4).png)