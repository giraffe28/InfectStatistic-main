	## 代码风格
	
- 缩进

  使用tab字符。
- 变量命名
1. 不能以下划线或美元符号开始，也不能以下划线或美元符号结束。

   反例： _name / __name / $Object / name_ / name$ / Object$
2. 严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式，纯拼音命名方式也要避免采用。
 
   反例： DaZhePromotion [打折] / getPingfenByName()  [评分] / int某变量 = 3
   
   正例： alibaba / taobao / youku / hangzhou等国际通用的名称，可视同英文。
3. 参数名、成员变量、局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式。
 
   正例： localValue / getHttpMessage() /  inputUserId
4. 杜绝完全不规范的缩写，避免望文不知义。
 
   反例： AbstractClass“缩写”命名成AbsClass；condition“缩写”命名成 condi，此类随意缩写严重降低了代码的可阅读性。
- 每行最多字符数

 单行字符数限制不超过120个，超出需要换行，换行时遵循如下原则：
1. 第二行相对第一行缩进4个空格，从第三行开始，不再继续缩进。
2. 运算符与下文一起换行。
3. 方法调用的点符号与下文一起换行。
4. 在多个参数超长，逗号后进行换行。
5. 在括号前不要换行。
- 函数最大行数

  不超过60行。
- 函数、类命名
1. 函数命名使用lowerCamelCase风格，必须遵从驼峰形式。
  
   正例： localValue / getHttpMessage() /  inputUserId
2. 类名使用UpperCamelCase风格，必须遵从驼峰形式，但以下情形例外：（领域模型的相关命名）DO / BO / DTO / VO等。
 
   正例：MarcoPolo / UserDO / XmlService / TcpUdpDeal /   TaPromotion
 
   反例：macroPolo / UserDo / XMLService / TCPUDPDeal /   TAPromotion
- 常量
1. 常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长。
   
   正例： MAX_STOCK_COUNT
 
   反例： MAX_COUNT
2. 不允许出现任何魔法值（即未经定义的常量）直接出现在代码中。
 
   反例： String key="Id#taobao_"+tradeId；
cache.put(key,  value);
3. long或者Long初始赋值时，必须使用大写的L，不能是小写的l，小写容易跟数字1混淆，造成误解。
 说明：Long a = 2l;写的是数字的21，还是Long型的2?
- 空行规则
1. 定义变量后要空行。尽可能在定义变量的同时初始化该变量，即遵循就近原则。如果变量的引用和定义相隔比较远，那么变量的初始化就很容易被忘记。若引用了未被初始化的变量，就会导致程序出错。
2. 每个函数定义结束之后都要加空行。
- 注释规则
1. 类、类属性、类方法的注释必须使用Javadoc规范，使用/*内容/格式，不得使用//xxx方式。
 说明：在IDE编辑窗口中，Javadoc方式会提示相关注释，生成Javadoc可以正确输出相应注释；在IDE中，工程调用方法时，不进入方法即可悬浮提示方法、参数、返回值的意义，提高阅读效率。

2. 所有的抽象方法（包括接口中的方法）必须要用Javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。
 说明：对子类的实现要求，或者调用注意事项，请一并说明。
3. 方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/ */注释，注意与代码对齐。

4. 所有的枚举类型字段必须要有注释，说明每个数据项的用途。

5. 与其“半吊子”英文来注释，不如用中文注释把问题说清楚。专有名词与关键字保持英文原文即可。
 反例：“TCP连接超时”解释成“传输控制协议连接超时”，理解反而费脑筋。
6. 代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改。
 说明：代码与注释更新不同步，就像路网与导航软件更新不同步一样，如果导航软件严重滞后，就失去了导航的意义。
- 操作符前后空格

  任何运算符左右必须加一个空格。

 